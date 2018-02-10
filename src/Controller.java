import java.awt.*;
import java.util.ArrayList;

public class Controller {

    private ArrayList<Triplet> tripletList = new ArrayList<>();
    private Point[][] grid;

    public Controller() {
    }

    public void makeNewGrid(int n, Point emptyTile){
        //ProblemParameters.getInstance().setParamN(n);
       // ProblemParameters.getInstance().setEmptyTilePosition(emptyTile);

    }

    public ArrayList<Triplet> resolveProblem(){

        //Grid completeGrid = new Grid(new Point(0,0),ProblemParameters.getInstance().getParamN());



        return null;
    }

    public void divideAndConquer(Grid grid){

       // if(grid.getGridDepht()==)
        grid.divide();
        tripletList.add(grid.placePiece());

    }

}
