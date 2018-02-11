import javafx.scene.paint.Stop;

import java.awt.*;
import java.util.ArrayList;

public class Controller {

    private ArrayList<Triplet> tripletList = new ArrayList<>();

    public Controller() {
    }

    public void makeNewGrid(int n, Point emptyTile) {
        ProblemParameters.getInstance().setParamN(n);
        ProblemParameters.getInstance().setEmptyTilePosition(emptyTile);

    }

    public ArrayList<Triplet> resolveProblem() {

        int initialGridDepth = ProblemParameters.getInstance().getParamN();

        StopWatch stopWatch = new StopWatch();
        
        Grid completeGrid = new Grid(new Point(0, 0), initialGridDepth);
        Grid.initProblem(ProblemParameters.getInstance().getEmptyTilePosition(), completeGrid);

        divideAndConquer(completeGrid);
        //Grid.printArrayTiles();

        System.out.println("Profondeur de la grille : " + initialGridDepth);
        System.out.println("Execution time : " + stopWatch.elapsedTime() + "sec.");
        return tripletList;
    }

    public void divideAndConquer(Grid grid) {

        tripletList.add(grid.solve());
        if (grid.getSubGrids() != null) {
            for (Grid subGrid : grid.getSubGrids()) {
                divideAndConquer(subGrid);
            }
        }
    }
}
