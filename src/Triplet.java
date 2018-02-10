import java.awt.*;

public class Triplet{

    private Point first;
    private Point second;
    private Point third;

    public Triplet(Point first, Point second, Point third) {
        this.first = first;
        this.second = second;
        this.third = third;
        System.out.println("new Triplet at: " + first + " " + second + " " + third);

    }

    public Triplet() {
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
