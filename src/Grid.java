import javafx.geometry.Pos;

import java.awt.*;

public class Grid {

    enum Position {
        UP_LEFT(0),
        UP_RIGHT(1),
        DOWN_LEFT(2),
        DOWN_RIGHT(3),
        ERROR();

        private int gridIndex;

        Position(int gridIndex) {
            this.gridIndex = gridIndex;
        }

        public String toString(Position position) {

            String posString = "";
            switch (position) {

                case UP_LEFT:
                    posString = " up left";
                    break;
                case UP_RIGHT:
                    posString = " up right";
                    break;
                case DOWN_LEFT:
                    posString = " down left";
                    break;
                case DOWN_RIGHT:
                    posString = " down right";
                    break;
            }
            return posString;
        }

        Position() {
        }

        public int getGridIndex() {
            return gridIndex;
        }

    }

    private static Point firstTilePosition;
    private final static int FILLED = 1;
    private final static int BLANK = 0;
    private static int[][] filledTiles;

    private Grid[] subGrids;
    // La position absolue du de cette grille dans la grille complete donnée par le premier point de cette grille.
    private Point absolutePosition;
    private int gridDepth;
    private Position filledTilePosition;

    public Grid(Point absolutePosition, int gridDepth) {
        this.absolutePosition = absolutePosition;
        this.gridDepth = gridDepth;
    }

    public static void initProblem(Point firstTileFilledPosition, Grid completeGrid) {
        Grid.firstTilePosition = firstTileFilledPosition;
        int x = Grid.firstTilePosition.x;
        int y = Grid.firstTilePosition.y;

        int arrayLength = (int) Math.pow(2, completeGrid.getGridDepth());
        Grid.filledTiles = new int[arrayLength][arrayLength];

        /*for (int i = 0; i < Grid.filledTiles.length; i++) {
            for (int j = 0; j < Grid.filledTiles.length; j++) {
                Grid.filledTiles[i][j] = BLANK;
            }
        }*/
        Grid.filledTiles[x][y] = FILLED;

    }

    public Position foundRogueTile(Grid grid) {
        int x = Grid.firstTilePosition.x;
        int y = Grid.firstTilePosition.y;
        Position position =Position.ERROR;
        int gridLength = (int) Math.pow(2, grid.getGridDepth());
        int offSet = gridLength / 2 - 1;

        if (Grid.inRange(grid.absolutePosition.x, grid.absolutePosition.x + offSet, x)
                && Grid.inRange(grid.absolutePosition.y, grid.absolutePosition.y + offSet, y)) {
            position = Position.UP_LEFT;

        } else if (Grid.inRange(grid.absolutePosition.x + offSet, grid.absolutePosition.x + gridLength - 1, x)
                && Grid.inRange(grid.absolutePosition.y, grid.absolutePosition.y + offSet, y)) {
            position= Position.DOWN_LEFT;

        } else if (Grid.inRange(grid.absolutePosition.x, grid.absolutePosition.x + offSet, x)
                && Grid.inRange(grid.absolutePosition.y + offSet, grid.absolutePosition.y + gridLength - 1, y)) {
            position = Position.UP_RIGHT;

        } else if (Grid.inRange(grid.absolutePosition.x + offSet, grid.absolutePosition.x + gridLength - 1, x)
                && Grid.inRange(grid.absolutePosition.y + offSet, grid.absolutePosition.y + gridLength - 1, y)) {
            position = Position.DOWN_RIGHT;
        }
        if(position == Position.ERROR){
            System.out.println("Rogue non trouvée pour la grille à : " + absolutePosition.toString());

        }        return position;
    }

    public Triplet solve() {
        return divide();
    }

    private Triplet divide() {

        // Divide the grid in four subGrids.
        int subGridDepth = this.gridDepth - 1;
        int gridLenght = (int) Math.pow(2, this.gridDepth);
        int offset = gridLenght / 2;

        Point upRightPosition = new Point();
        upRightPosition.setLocation(absolutePosition.getX(), absolutePosition.getY() + offset);

        Point downRightPosition = new Point();
        downRightPosition.setLocation(absolutePosition.getX() + offset, absolutePosition.getY() + offset);

        Point downLeftPosition = new Point();
        downLeftPosition.setLocation(absolutePosition.getX() + offset, absolutePosition.getY());

        if(gridDepth>1){
            this.subGrids = new Grid[4];
            Grid upLeftSubGrid = new Grid(absolutePosition, subGridDepth);
            Grid upRightSubGrid = new Grid(upRightPosition, subGridDepth);
            Grid downLeftSubGrid = new Grid(downLeftPosition, subGridDepth);
            Grid downRightSubGrid = new Grid(downRightPosition, subGridDepth);

            this.getSubGrids()[Position.UP_LEFT.getGridIndex()] = upLeftSubGrid;
            this.getSubGrids()[Position.UP_RIGHT.getGridIndex()] = upRightSubGrid;
            this.getSubGrids()[Position.DOWN_LEFT.getGridIndex()] = downLeftSubGrid;
            this.getSubGrids()[Position.DOWN_RIGHT.getGridIndex()] = downRightSubGrid;
        }else{
            subGrids = null;
        }
        /*------------------------------------------------------------------------------------------------------------*/

        // Place the piece in the grid
        Point first = new Point();
        Point second = new Point();
        Point third = new Point();

        // Dans le cas d une grille de 2 sur 2.
        if (gridDepth<=1) {
            // L element plein est en (0,0)
            if(Grid.filledTiles[absolutePosition.x][absolutePosition.y]==FILLED){
                first.setLocation(absolutePosition.x+offset,absolutePosition.y);
                second.setLocation(absolutePosition.x+offset,absolutePosition.y+offset);
                third.setLocation(absolutePosition.x, absolutePosition.y+offset);
                // L element plein est en (0,1)
            }else if(Grid.filledTiles[absolutePosition.x][absolutePosition.y+offset]==FILLED){
                first.setLocation(absolutePosition.x+offset,absolutePosition.y);
                second.setLocation(absolutePosition.x+offset,absolutePosition.y+offset);
                third.setLocation(absolutePosition.x, absolutePosition.y);
                // L element plein est en (1,0)
            }else if(Grid.filledTiles[absolutePosition.x+offset][absolutePosition.y]==FILLED){
                first.setLocation(absolutePosition.x,absolutePosition.y);
                second.setLocation(absolutePosition.x+offset,absolutePosition.y+offset);
                third.setLocation(absolutePosition.x, absolutePosition.y+offset);
                // L element plein est en (1,1)
            }else if(Grid.filledTiles[absolutePosition.x+offset][absolutePosition.y+offset]==FILLED){
                first.setLocation(absolutePosition.x+offset,absolutePosition.y);
                second.setLocation(absolutePosition.x,absolutePosition.y);
                third.setLocation(absolutePosition.x, absolutePosition.y+offset);
            }
        } else {
            // Le tileFilled est dans le coin haut a gauche.
            if(Grid.filledTiles[absolutePosition.x][absolutePosition.y] == FILLED){
                filledTilePosition = Position.UP_LEFT;
            // Dans le coin haut a droite
            }else if (Grid.filledTiles[absolutePosition.x][absolutePosition.y+gridLenght-1] == FILLED){
                filledTilePosition = Position.UP_RIGHT;

            // Dans le coin bas a gauche
            }else if(Grid.filledTiles[absolutePosition.x+gridLenght-1][absolutePosition.y] == FILLED){
                filledTilePosition = Position.DOWN_LEFT;

            // Dans le coin bas a droite
            }else if (Grid.filledTiles[absolutePosition.x+gridLenght-1][absolutePosition.y+gridLenght-1] == FILLED){
                filledTilePosition = Position.DOWN_RIGHT;

            // Dans aucun coin. ---> Tile de départ.
            }else{
                filledTilePosition = foundRogueTile(this);
            }

            switch (filledTilePosition) {
                case UP_LEFT:
                    first.setLocation(upRightPosition.x + offset - 1, upRightPosition.y);
                    second.setLocation(downRightPosition.x, downRightPosition.y);
                    third.setLocation(downLeftPosition.x, downLeftPosition.y + offset - 1);
                    break;
                case UP_RIGHT:
                    first.setLocation(absolutePosition.x + offset - 1, absolutePosition.y + offset - 1);
                    second.setLocation(downLeftPosition.x, downLeftPosition.y + offset - 1);
                    third.setLocation(downRightPosition.x, downRightPosition.y);
                    break;
                case DOWN_LEFT:
                    first.setLocation(absolutePosition.x + offset - 1, absolutePosition.y + offset - 1);
                    second.setLocation(upRightPosition.x + offset - 1, upRightPosition.y);
                    third.setLocation(downRightPosition.x, downRightPosition.y);
                    break;
                case DOWN_RIGHT:
                    first.setLocation(upRightPosition.x + offset - 1, upRightPosition.y);
                    second.setLocation(absolutePosition.x + offset - 1, absolutePosition.y + offset - 1);
                    third.setLocation(downLeftPosition.x, downLeftPosition.y + offset - 1);
                    break;
                    default:
                        System.out.println(" Aucune coordonnées trouvées pour la grille en : " + absolutePosition);
                        break;
            }
        }
        //System.out.println("Piece insere a : " + first.toString() +", " + second.toString() +", " +third.toString());
        Grid.filledTiles[first.x][first.y] = FILLED;
        Grid.filledTiles[second.x][second.y] = FILLED;
        Grid.filledTiles[third.x][third.y] = FILLED;

        return new Triplet(first, second, third);
    }

    //region Accessors
    public Grid[] getSubGrids() {
        return subGrids;
    }

    public int getGridDepth() {
        return gridDepth;
    }

    //endregion

    //region Utility
    public static boolean inRange(int low, int high, int n) {
        return n >= low && n <= high;
    }

    public static void printArrayTiles(){
        for (int[] x : Grid.filledTiles)
        {
            for (int y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
    //endregion
}
