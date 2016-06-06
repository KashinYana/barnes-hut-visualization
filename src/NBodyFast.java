import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class NBodyFast {
    public static void main(String[] args) {
        final double dt = 3;                     // time quantum
        double radius = StdIn.readDouble();        // radius of universe
        Scanner scanner;
        try {
            scanner = new Scanner(new File("graph_c.txt"));
        } catch (Exception e) {
            System.out.print("File not found");
            return;
        }

        int N = scanner.nextInt();                   // number of particle
        int M = scanner.nextInt();

        // turn on animation mode and rescale coordinate system
        StdDraw.show(0);
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);

        // read in and initialize bodies
        Body[] bodies = new Body[N];
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            double px   = -radius/10 + (2 * radius/10) * rand.nextDouble();
            double py   = -radius/10 + (2 * radius/10) * rand.nextDouble();
            double vx   = 0;
            double vy   = 0;
            double mass = 10;
            Color color = new Color(0, 0, 255);
            bodies[i]   = new Body(px, py, vx, vy, mass, color);
        }

        Integer[][] edges = new Integer[M][2];
        for (int i = 0; i < M; ++i) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            edges[i][0] = from;
            edges[i][1] = to;
        }

        StdOut.print("stop");


        // simulate the universe
        double t = 0.0;

        for (double it = 0; true; it++) {

            System.out.println(it);

            // create
            BHTree bhTree = new BHTree(new Quad(-radius, -radius, 2*radius));
            for (int i = 0; i < N; ++i) {
                bhTree.insert(bodies[i]);
            }

            // update the forces
            for (int i = 0; i < N; i++) {
                bodies[i].resetForce();
            }

            // update the forces
            for (int i = 0; i < N; i++) {
                bhTree.updateForce(bodies[i]);
            }

            for (int i = 0; i < M; i++) {
                int from = edges[i][0];
                int to = edges[i][1];

                bodies[from].addAttractiveForce(bodies[to]);
                bodies[to].addAttractiveForce(bodies[from]);
            }

            // update the bodies
            for (int i = 0; i < N; i++) {
                bodies[i].update(dt);
            }

            //// draw the bodies
            StdDraw.clear(StdDraw.BLACK);

            for (int i = 0; i < M; i++) {
                Body.drawEdge(bodies[edges[i][0]], bodies[edges[i][1]]);
            }

            for (int i = 0; i < N; i++) {
                bodies[i].draw();
            }

            StdDraw.show(1);
            t = t + dt;
        }

         /*save(bodies, N);
        System.out.println("ok");

        StdDraw.show(0);
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);
        StdDraw.clear(StdDraw.BLACK);
        for (int i = 0; i < N; i++) {
            bodies[i].draw();
        }

        for (int i = 0; i < M; i++) {
            Body.drawEdge(bodies[edges[i][0]], bodies[edges[i][1]]);
        }

        StdDraw.show(1);*/
    }

    public static void save(Body[] bodies, int N) {
        BufferedWriter writer = null;
        try {

            writer = new BufferedWriter(new FileWriter("result_graph.txt"));
            for (int i = 0; i < N; i++)
            {
                writer.write(bodies[i].toString());
                writer.newLine();
            }
            writer.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
