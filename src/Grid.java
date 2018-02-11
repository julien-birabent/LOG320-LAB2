import javafx.geometry.Pos;

import java.awt.*;

public class Grid {

    enum Position {
        UP_LEFT(0),
        UP_RIGHT(1),
        DOWN_LEFT(2),
        DOWN_RIGHT(3);

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

    private Grid[] subGrids;
    private Triplet piecePosition;
    // La position absolue du de cette grille dans la grille complete donnée par le premier point de cette grille.
    private Point absolutePosition;
    private int gridDepth;
    private Position filledTilePosition;

    public Grid(Point absolutePosition, int gridDepth) {
        this.subGrids = new Grid[4];
        this.absolutePosition = absolutePosition;
        this.gridDepth = gridDepth;
    }

    public static void initProblem(Point firstTileFilledPosition, Grid completeGrid) {
        Grid.firstTilePosition = firstTileFilledPosition;
        int x = Grid.firstTilePosition.x;
        int y = Grid.firstTilePosition.y;
        int gridLength = (int) Math.pow(2, completeGrid.gridDepth);
        int offSet = gridLength / 2 - 1;

        if (Grid.inRange(0, offSet, x) && Grid.inRange(0, offSet, y)) {
            completeGrid.setFilledTilePosition(Position.UP_LEFT);
        } else if (Grid.inRange(offSet, gridLength, x) && Grid.inRange(0, offSet, y)) {
            completeGrid.setFilledTilePosition(Position.DOWN_LEFT);
        } else if (Grid.inRange(0, offSet, x) && Grid.inRange(offSet, gridLength, y)) {
            completeGrid.setFilledTilePosition(Position.UP_RIGHT);
        } else completeGrid.setFilledTilePosition(Position.DOWN_RIGHT);
    }

    public void foundRogueTile(Grid subGrid) {
        int x = Grid.firstTilePosition.x;
        int y = Grid.firstTilePosition.y;
        int gridLength = (int) Math.pow(2, subGrid.getGridDepth());
        int offSet = gridLength / 2 - 1;

        if (offSet == -1) {
            offSet = 0;
        }


        if (Grid.inRange(subGrid.absolutePosition.x, subGrid.absolutePosition.x + offSet, x)
                && Grid.inRange(subGrid.absolutePosition.y, subGrid.absolutePosition.y + offSet, y)) {
            subGrid.setFilledTilePosition(Position.UP_LEFT);

        } else if (Grid.inRange(subGrid.absolutePosition.x+offSet, subGrid.absolutePosition.x +gridLength-1, x)
                && Grid.inRange(subGrid.absolutePosition.y, subGrid.absolutePosition.y + offSet, y)) {
            subGrid.setFilledTilePosition(Position.DOWN_LEFT);

        } else if (Grid.inRange(subGrid.absolutePosition.x, subGrid.absolutePosition.x + offSet, x)
                && Grid.inRange(subGrid.absolutePosition.y+offSet, subGrid.absolutePosition.y +gridLength-1, y)) {
            subGrid.setFilledTilePosition(Position.UP_RIGHT);

        } else if (Grid.inRange(subGrid.absolutePosition.x+offSet, subGrid.absolutePosition.x +gridLength-1, x)
                && Grid.inRange(subGrid.absolutePosition.y+offSet, subGrid.absolutePosition.y +gridLength-1, y)) {
            subGrid.setFilledTilePosition(Position.DOWN_RIGHT);
        }

        // System.out.println("gray tile position : (" + x + ";" + y + ") ---> " + subGrid.getFilledTilePosition().toString(filledTilePosition));
    }

    public Triplet solve() {
        return divide();
    }

    private Triplet divide() {

        // Divide the grid in four subGrids.
        int subGridDepth = this.gridDepth - 1;
        int gridLenght = (int) Math.pow(2, this.gridDepth);
        int offset = gridLenght / 2;
        //System.out.println("UpLeft position : " + absolutePosition.toString());

        Point upRightPosition = new Point();
        upRightPosition.setLocation(absolutePosition.getX(), absolutePosition.getY() + offset);
        //System.out.println("UpRight position : " + upRightPosition.toString());

        Point downRightPosition = new Point();
        downRightPosition.setLocation(absolutePosition.getX() + offset, absolutePosition.getY() + offset);
        // System.out.println("downRight position : " + downRightPosition.toString());

        Point downLeftPosition = new Point();
        downLeftPosition.setLocation(absolutePosition.getX() + offset, absolutePosition.getY());
        // System.out.println("DownLeft position : " + downLeftPosition.toString() + "\n");

        Grid upLeftSubGrid = new Grid(absolutePosition, subGridDepth);
        Grid upRightSubGrid = new Grid(upRightPosition, subGridDepth);
        Grid downLeftSubGrid = new Grid(downLeftPosition, subGridDepth);
        Grid downRightSubGrid = new Grid(downRightPosition, subGridDepth);

        this.getSubGrids()[Position.UP_LEFT.getGridIndex()] = upLeftSubGrid;
        this.getSubGrids()[Position.UP_RIGHT.getGridIndex()] = upRightSubGrid;
        this.getSubGrids()[Position.DOWN_LEFT.getGridIndex()] = downLeftSubGrid;
        this.getSubGrids()[Position.DOWN_RIGHT.getGridIndex()] = downRightSubGrid;

        // Place the piece in the grid
        Point first = new Point();
        Point second = new Point();
        Point third = new Point();

        if(filledTilePosition != null){
            switch (filledTilePosition) {
                case UP_LEFT:
                    first.setLocation(upRightPosition.x + offset - 1,
                            upRightPosition.y);
                    second.setLocation(downRightPosition.x, downRightPosition.y);
                    third.setLocation(downLeftPosition.x, downLeftPosition.y + offset - 1);

                    this.foundRogueTile(upLeftSubGrid);
                    upRightSubGrid.setFilledTilePosition(Position.DOWN_LEFT);
                    downRightSubGrid.setFilledTilePosition(Position.UP_LEFT);
                    downLeftSubGrid.setFilledTilePosition(Position.UP_RIGHT);

                    break;
                case UP_RIGHT:
                    first.setLocation(absolutePosition.x + offset - 1,
                            absolutePosition.y + offset - 1);
                    second.setLocation(downLeftPosition.x, downLeftPosition.y + offset - 1);
                    third.setLocation(downRightPosition.x, downRightPosition.y);


                    this.foundRogueTile(upRightSubGrid);
                    downRightSubGrid.setFilledTilePosition(Position.UP_LEFT);
                    downLeftSubGrid.setFilledTilePosition(Position.UP_RIGHT);
                    upLeftSubGrid.setFilledTilePosition(Position.DOWN_RIGHT);
                    break;
                case DOWN_LEFT:
                    first.setLocation(absolutePosition.x + offset - 1,
                            absolutePosition.y + offset - 1);
                    second.setLocation(upRightPosition.x + offset - 1, upRightPosition.y);
                    third.setLocation(downRightPosition.x, downRightPosition.y);

                    this.foundRogueTile(downLeftSubGrid);
                    upLeftSubGrid.setFilledTilePosition(Position.DOWN_RIGHT);
                    upRightSubGrid.setFilledTilePosition(Position.DOWN_LEFT);
                    downRightSubGrid.setFilledTilePosition(Position.UP_LEFT);
                    break;
                case DOWN_RIGHT:
                    first.setLocation(upRightPosition.x + offset - 1,
                            upRightPosition.y);
                    second.setLocation(absolutePosition.x + offset - 1, absolutePosition.y + offset - 1);
                    third.setLocation(downLeftPosition.x, downLeftPosition.y + offset - 1);

                    this.foundRogueTile(downRightSubGrid);
                    upLeftSubGrid.setFilledTilePosition(Position.DOWN_RIGHT);
                    upRightSubGrid.setFilledTilePosition(Position.DOWN_LEFT);
                    downLeftSubGrid.setFilledTilePosition(Position.UP_RIGHT);
                    break;
            }
            this.piecePosition = new Triplet(first, second, third);
        }

        return this.piecePosition;
    }

    //region Accessors
    public Grid[] getSubGrids() {
        return subGrids;
    }

    public Point getAbsolutePosition() {
        return absolutePosition;
    }

    public void setAbsolutePosition(Point absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public int getGridDepth() {
        return gridDepth;
    }

    public void setGridDepth(int gridDepth) {
        this.gridDepth = gridDepth;
    }

    public void setSubGrids(Grid[] subGrids) {
        this.subGrids = subGrids;
    }

    public Triplet getPiecePosition() {
        return piecePosition;
    }

    public void setPiecePosition(Triplet piecePosition) {
        this.piecePosition = piecePosition;
    }

    public Position getFilledTilePosition() {
        return filledTilePosition;
    }

    public void setFilledTilePosition(Position filledTilePosition) {
        this.filledTilePosition = filledTilePosition;
    }

    //endregion
    public static boolean inRange(int low, int high, int n) {
        return n >= low && n <= high;
    }
}
