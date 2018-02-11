import java.awt.*;

public class Triplet{

    private Point first;
    private Point second;
    private Point third;

    public Triplet(Point first, Point second, Point third) {
        this.first = first;
        this.second = second;
        this.third = third;

    }

    public Triplet() {
        this.first = new Point();
        this.second = new Point();
        this.third = new Point();
    }

    public Point getFirst() {
        return first;
    }

    public Point getSecond() {
        return second;
    }

    public Point getThird() {
        return third;
    }
}
