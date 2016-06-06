

public class Quad {
    private double x, y;       //  left endpoint
    private double length;       //  the length of one of its sides

    public Quad (double x, double y, double length) {
        this.x = x;
        this.y = y;
        this.length = length;
    }

    public boolean contains(double x, double y) {
        return x >= this.x && y >= this.y && x <= this.x + length && y <= this.y + length;
    }

    public double length() {
        return length;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public Quad NW() {
        return new Quad(x, y + length/2, length/2);
    }

    public Quad NE() {
        return new Quad(x + length/2, y + length/2, length/2);
    }

    public Quad SW() {
        return new Quad(x, y, length/2);
    }

    public Quad SE() {
        return new Quad(x + length/2, y, length/2);
    }
}
