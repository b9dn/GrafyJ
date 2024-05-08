package gui;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class CircleG extends Circle {
    private int nodeNmb;

    public CircleG(double r, Paint paint, int nodeNmb) {
        super(r, paint);
        this.nodeNmb = nodeNmb;
    }

    public int getNodeNmb() {
        return nodeNmb;
    }
}
