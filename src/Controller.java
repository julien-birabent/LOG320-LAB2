import java.awt.*;

public class Controller {

    public Controller() {
    }

    public void makeNewGrid(int n, Point emptyTile){
        ProblemParameters.getInstance().setParamN(n);
        ProblemParameters.getInstance().setEmptyTilePosition(emptyTile);
    }

    public void resolveProblem(){
        //TODO : use algorithm here and return a result (void for now)
    }

}
