import com.sun.prism.image.Coords;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ProblemParameters extends Observable{
    private static ProblemParameters ourInstance = new ProblemParameters();

    private final static int DEFAULT_N = 2;
    private int paramN;
    private Point emptyTilePosition;


    public static ProblemParameters getInstance() {
        return ourInstance;
    }

    private ProblemParameters() {
        this.emptyTilePosition = new Point(0,0);
        this.paramN = DEFAULT_N;
    }

    public int getParamN() {
        return paramN;
    }

    public void setParamN(int paramN) {
        this.paramN = paramN;
        setChanged();
        notifyObservers(paramN);
    }

    public Point getEmptyTilePosition() {
        return emptyTilePosition;
    }

    public void setEmptyTilePosition(Point emptyTilePosition) {
        this.emptyTilePosition = emptyTilePosition;
        setChanged();
        notifyObservers(emptyTilePosition);
    }
}
