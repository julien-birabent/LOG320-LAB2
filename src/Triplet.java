public class Triplet<A> {

    private A first;
    private A second;
    private A third;

    public Triplet(A first, A second, A third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public A getSecond() {
        return second;
    }

    public A getThird() {
        return third;
    }
}
