import java.awt.*;
import java.util.ArrayList;

public class Controller {

    private ArrayList<Triplet> tripletList;
    private Point[][] grid;

    public Controller() {
    }

    public void makeNewGrid(int n, Point emptyTile){
        ProblemParameters.getInstance().setParamN(n);
        ProblemParameters.getInstance().setEmptyTilePosition(emptyTile);

    }

    public ArrayList<Triplet> resolveProblem(){
        //TODO : use algorithm here and return a result (void for now)
        int n = ProblemParameters.getInstance().getParamN();
        Point emptyTile = ProblemParameters.getInstance().getEmptyTilePosition();
        int gridSize = (int)Math.pow(2,n);
        this.grid = new Point[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                this.grid[j][i] = new Point(j,i);
            }
        }
        // start the recursion
        tripletList = new ArrayList<Triplet>();
        divideAndConquer(0, gridSize-1,0, gridSize-1, emptyTile);


        return this.tripletList;
    }

    public void divideAndConquer(int xStart, int xEnd, int yStart, int yEnd, Point emptyTile) {
        int size = xEnd + 1 - xStart ;

        // split in 4 smaller grids
        int halves = size / 2;
        int emptyTileQuartile = -1;

        Point[][] topLeft = new Point[halves][halves];
        Point[][] topRight = new Point[halves][halves];
        Point[][] bottomLeft = new Point[halves][halves];
        Point[][] bottomRight = new Point[halves][halves];

        for (int i = 0; i < halves; i++) {
            for (int j = 0; j < halves; j++) {
                topLeft[j][i] = grid[xStart + j][yStart + i];
                topRight[j][i] = grid[xStart + j + halves][yStart + i];
                bottomLeft[j][i] = grid[xStart + j][yStart + i + halves];
                bottomRight[j][i] = grid[xStart + j + halves][yStart + i + halves];

                // find which quarter contains the empty tile
                if(emptyTile.equals(topLeft[j][i])) emptyTileQuartile = 0;
                else if(emptyTile.equals(topRight[j][i])) emptyTileQuartile = 1;
                else if(emptyTile.equals(bottomLeft[j][i])) emptyTileQuartile = 2;
                else if(emptyTile.equals(bottomRight[j][i])) emptyTileQuartile = 3;

            }
        }

        // place triplet to have one occupied tile on every quarter
        System.out.println("empty tile " + emptyTile.toString() + "is in "+ emptyTileQuartile+"th quarter");
        int borderLimit = halves - 1;

        Point quarter0empty = topLeft[borderLimit][borderLimit];
        Point quarter1empty = topRight[0][borderLimit];
        Point quarter2empty = bottomLeft[borderLimit][0];
        Point quarter3empty = bottomRight[0][0];

        if (emptyTileQuartile == -1) {
            System.out.println("wtf is happening");
        }

        // if the empty tile is top left quarter
        if (emptyTileQuartile == 0) {
            Point first = quarter1empty;
            Point second = quarter2empty;
            Point third = quarter3empty;
            this.tripletList.add(new Triplet(first, second, third));

            if(size > 2) {
                divideAndConquer(xStart, xStart + borderLimit, yStart, yStart + borderLimit, emptyTile);
                divideAndConquer(xStart + borderLimit + 1, xEnd, yStart, yStart + borderLimit, first);
                divideAndConquer(xStart, xStart + borderLimit, yEnd - borderLimit, yEnd, second);
                divideAndConquer(xEnd - borderLimit, xEnd, yEnd - borderLimit, yEnd, third);
            }
        }

        // if the empty tile is top right quarter
        else if (emptyTileQuartile == 1) {
            Point first = quarter0empty;
            Point second = quarter2empty;
            Point third = quarter3empty;
            this.tripletList.add(new Triplet(first, second, third));

            if(size > 2) {
                divideAndConquer(xStart, xStart + borderLimit, yStart, yStart + borderLimit, first);
                divideAndConquer(xStart + borderLimit + 1, xEnd, yStart, yStart + borderLimit, emptyTile);
                divideAndConquer(xStart, xStart + borderLimit, yEnd - borderLimit, yEnd, second);
                divideAndConquer(xEnd - borderLimit, xEnd, yEnd - borderLimit, yEnd, third);
            }
        }

        // if the empty tile is bottom left quarter
        else if (emptyTileQuartile == 2) {
            Point first = quarter0empty;
            Point second = quarter1empty;
            Point third = quarter3empty;
            this.tripletList.add(new Triplet(first, second, third));

            if(size > 2) {

                divideAndConquer(xStart, xStart + borderLimit, yStart, yStart + borderLimit, first);
                divideAndConquer(xStart + borderLimit + 1, xEnd, yStart, yStart + borderLimit, second);
                divideAndConquer(xStart , xStart + borderLimit , yEnd - borderLimit, yEnd, emptyTile);
                divideAndConquer(xEnd - borderLimit, xEnd, yEnd - borderLimit, yEnd, third);
            }
        }

        // if the empty tile is bottom right quarter
        else if (emptyTileQuartile == 3) {
            Point first = quarter0empty;
            Point second = quarter1empty;
            Point third = quarter2empty;
            this.tripletList.add(new Triplet(first, second, third));

            if(size > 2) {
                divideAndConquer(xStart, xStart + borderLimit, yStart, yStart + borderLimit, first);
                divideAndConquer(xStart + borderLimit + 1, xEnd, yStart, yStart + borderLimit, second);
                divideAndConquer(xStart , xStart + borderLimit , yEnd - borderLimit, yEnd, third);
                divideAndConquer(xEnd - borderLimit, xEnd, yEnd - borderLimit, yEnd, emptyTile);
            }
        }
    }

}
