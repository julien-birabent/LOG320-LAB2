import java.awt.*;

public class Grid {

    private static int gridLenght;

    private Grid[] subGrids;
    private Triplet piecePosition;
    // La position absolue du de cette grille dans la grille entière donnée par le premier point de cette grille.
    private Point absolutePosition;
    private int gridDepht;

    public Grid(Point absolutePosition, int gridDepht) {
        this.subGrids = new Grid[4];
        this.piecePosition = new Triplet();
        this.absolutePosition = absolutePosition;
        this.gridDepht = gridDepht;
    }

    public void divide(){



    }

    public Triplet placePiece(){

        return null;
    }


    //region Accessors
    public Grid[] getSubGrids() {
        return subGrids;
    }

    public static int getGridLenght() {
        return gridLenght;
    }

    public Point getAbsolutePosition() {
        return absolutePosition;
    }

    public void setAbsolutePosition(Point absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public int getGridDepht() {
        return gridDepht;
    }

    public void setGridDepht(int gridDepht) {
        this.gridDepht = gridDepht;
    }

    public void setSubGrids(Grid[] subGrids) {
        this.subGrids = subGrids;
    }

    public static void setGridLenght(int gridLenghtParam){
        gridLenght = gridLenghtParam;
    }

    public Triplet getPiecePosition() {
        return piecePosition;
    }

    public void setPiecePosition(Triplet piecePosition) {
        this.piecePosition = piecePosition;
    }
    //endregion
}
