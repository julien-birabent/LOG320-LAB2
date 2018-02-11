import java.awt.*;
import java.util.ArrayList;

public class Controller {

    private ArrayList<Triplet> tripletList = new ArrayList<>();
    private Point[][] grid;

    public Controller() {
    }

    public void makeNewGrid(int n, Point emptyTile){
        ProblemParameters.getInstance().setParamN(n);
        ProblemParameters.getInstance().setEmptyTilePosition(emptyTile);

    }

    public ArrayList<Triplet> resolveProblem(){

        int initialGridDepth = ProblemParameters.getInstance().getParamN();
        int initialGridLength = (int)Math.pow(2,initialGridDepth);

        Grid completeGrid = new Grid(new Point(0,0),initialGridDepth);
        Grid.initProblem(ProblemParameters.getInstance().getEmptyTilePosition(),completeGrid);
        divideAndConquer(completeGrid);

        return tripletList;
    }

    public void divideAndConquer(Grid grid){

        if (grid.getGridDepth() == 0) {
            return;
        }else{
            tripletList.add(grid.solve());
            for(Grid subGrid : grid.getSubGrids()){
                divideAndConquer(subGrid);
            }
        }
    }
}
