

public class BHTree {

    private Body body;     // body or aggregate body stored in this node
    private Quad quad;     // square region that the tree represents
    private BHTree NW;     // tree representing northwest quadrant
    private BHTree NE;     // tree representing northeast quadrant
    private BHTree SW;     // tree representing southwest quadrant
    private BHTree SE;     // tree representing southeast quadrant

    public static double theta = 0.5;

    public BHTree(Quad q) {
        this.quad = q;
        this.body = null;
        this.NW = null;
        this.NE = null;
        this.SW = null;
        this.SE = null;
    }

    public void insert(Body b) {
        if (!b.in(quad)) {
            return;
        }

        if (body == null) {
            body = b;
        } else {

            if (b.distanceTo(this.body) < 0.001) {
                b.shift(quad);
            }

            if (this.NW == null) {
                this.NW = new BHTree(quad.NW());
                this.NE = new BHTree(quad.NE());
                this.SW = new BHTree(quad.SW());
                this.SE = new BHTree(quad.SE());
                this.NW.insert(this.body);
                this.NE.insert(this.body);
                this.SW.insert(this.body);
                this.SE.insert(this.body);
            }

            this.NW.insert(b);
            this.NE.insert(b);
            this.SW.insert(b);
            this.SE.insert(b);

            this.body = Body.add(this.body, b);
        }
    }

    public void updateForce(Body b) {
        if (this.NW == null) {
            if (this.body == null || this.body == b) {
                return;
            } else {
                b.addForce(this.body);
            }
        } else {
            if (this.quad.length() / this.body.distanceTo(b) < theta) {
                b.addForce(this.body);
            } else {
                NW.updateForce(b);
                NE.updateForce(b);
                SW.updateForce(b);
                SE.updateForce(b);
            }
        }
    }
}
