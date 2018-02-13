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

        // start the recursion
        tripletList = new ArrayList<Triplet>();

        StopWatch stopWatch = new StopWatch();
        divideAndConquer(0, gridSize-1,0, gridSize-1, emptyTile);
        System.out.println("Profondeur de la grille : " + n);
        System.out.println("Execution time : " + stopWatch.elapsedTime() + "sec.");


        return this.tripletList;
    }

    public int findRogueTile(int xStart, int xEnd, int yStart, int yEnd, Point emptyTile) {
        int midx = (xEnd - xStart + 1) / 2;
        int midy = (yEnd - yStart + 1) / 2;
        if( emptyTile.x < midx && emptyTile.y < midy) return 0;
        else if( emptyTile.x >= midx && emptyTile.y < midy) return 1;
        else if( emptyTile.x < midx && emptyTile.y >= midy) return 2;
        else if( emptyTile.x >= midx && emptyTile.y >= midy) return 3;
        return -1;
    }

    public int findEmptyTileQuartile(int xStart, int xEnd, int yStart, int yEnd, Point emptyTile) {
        if(emptyTile.x == xStart && emptyTile.y == yStart) return 0;
        else if (emptyTile.x == xEnd && emptyTile.y == yEnd) return 3;
        else if(emptyTile.x == xStart && emptyTile.y == yEnd) return 2;
        else if(emptyTile.x == xEnd && emptyTile.y == yStart) return 1;
        else return findRogueTile(xStart, xEnd, yStart, yEnd, emptyTile);
    }

    public void divideAndConquer(int xStart, int xEnd, int yStart, int yEnd, Point emptyTile) {
        int size = xEnd + 1 - xStart ;

        // split in 4 smaller grids
        int halves = size / 2;
        int emptyTileQuartile = -1;
        emptyTileQuartile = findEmptyTileQuartile(xStart, xEnd, yStart, yEnd, emptyTile);

        // place triplet to have one occupied tile on every quarter
        //System.out.println("empty tile " + emptyTile.toString() + "is in "+ emptyTileQuartile+"th quarter");
        int borderLimit = halves - 1;

        Point quarter0empty = new Point(xStart+borderLimit, yStart+borderLimit);
        Point quarter1empty = new Point(xStart+borderLimit+1, yStart+borderLimit);
        Point quarter2empty = new Point(xStart+borderLimit, yStart+borderLimit+1);
        Point quarter3empty = new Point(xStart+borderLimit+1, yStart+borderLimit+1);

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
