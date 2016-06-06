import java.awt.Color;

public class Body {
    private double rx, ry;       // position
    private double vx, vy;       // velocity
    private double fx, fy;       // force
    private double mass;         // mass
    private Color color;         // color

    // create and initialize a new Body
    public Body(double rx, double ry, double vx, double vy, double mass, Color color) {
        this.rx    = rx;
        this.ry    = ry;
        this.vx    = vx;
        this.vy    = vy;
        this.mass  = mass;
        this.color = color;
    }

    public  boolean in(Quad q) {
        return q.contains(rx, ry);
    }

    public static Body add(Body a, Body b) {
        return new Body(
                (a.rx*a.mass + b.rx*b.mass)/(a.mass + b.mass),
                (a.ry*a.mass + b.ry*b.mass)/(a.mass + b.mass),
                a.vx,
                a.vy,
                a.mass + b.mass,
                a.color
        );
    }

    // update the velocity and position of the invoking Body
    // uses leapfrom method, as in Assignment 2
    public void update(double dt) {
        vx += dt * fx / mass;
        vy += dt * fy / mass;
        rx += dt * vx;
        ry += dt * vy;
        if (Double.isNaN(vx)) {
            System.out.println("!!!!!!!!!!!!");
            System.out.println(fx);

        }
    }

    // return the Euclidean distance bewteen the invoking Body and b
    public double distanceTo(Body b) {
        double dx = rx - b.rx;
        double dy = ry - b.ry;
        return Math.sqrt(dx*dx + dy*dy);
    }

    // reset the force of the invoking Body to 0
    public void resetForce() {
        fx = 0.0;
        fy = 0.0;
        vx = 0;
        vy = 0;
    }

    // compute the net force acting between the invoking body a and b, and
    // add to the net force acting on the invoking Body
    public void addForce(Body b) {
        double G = 1000;   // gravational constant
        Body a = this;
        double dx = b.rx - a.rx;
        double dy = b.ry - a.ry;
        double dist = Math.sqrt(dx*dx + dy*dy) + 1;
        double F = 0;
        if (dist > 2000) {
            F = 0;
        } else {
            F = G / (dist * dist);
        }
        a.fx -= F * dx / dist;
        a.fy -= F * dy / dist;
    }

    public void addAttractiveForce(Body b) {
        double k = 0.1;
        Body a = this;
        double dx = b.rx - a.rx;
        double dy = b.ry - a.ry;
        double dist = Math.sqrt(dx*dx + dy*dy) + 1;
        double F = 0.1;
        if (dist > 2) {
            F = k * (dist - 2);
        }
        a.fx += F * dx / dist;
        a.fy += F * dy / dist;
    }

    public void shift(Quad q) {
        if (q.contains(this.rx + 0.001, this.rx +0.001)) {
            this.rx += 0.001;
            this.ry += 0.001;
        } else {
            this.rx = q.x();
            this.ry = q.y();
        }
    }


    // draw the invoking Body to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(color);
        StdDraw.point(rx, ry);
    }

    public static void drawEdge(Body a, Body b) {
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(new Color(255, 0, 0));
        StdDraw.line(a.rx, a.ry, b.rx, b.ry);
    }

    // convert to string representation formatted nicely
    public String toString() {
        return String.format("%10.3E %10.3E", rx, ry);
    }

}