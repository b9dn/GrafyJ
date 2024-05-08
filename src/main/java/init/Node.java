package init;

import gui.CircleG;

import java.util.Random;

public class Node {
    private int id;
    private Node[] edg = new Node[4];
    private double[] valEdg = new double[4];
    private Random liczba = new Random();
    private CircleG cir;

    public void setVal(int direction, double value) {
        this.valEdg[direction] = value;
    }

    public Node(int id) {
        this.id = id;
    }

    public void connect(int directionToConnect, Node node) {
        edg[directionToConnect] = node;
    }

    public void randEdg(int direction, double min, double max) {
        this.valEdg[direction] = liczba.nextDouble() * (max - min) + min;
    }

    public int showNode() {
        return this.id;
    }

    public double showValEdg(int direction) {
        return this.valEdg[direction];
    }

    public boolean isEdg(int direction) {
        return this.edg[direction] != null;
    }

    public Node edgPointer(int direction) {
        return this.edg[direction];
    }

    public int edgNum(int direction) {
        return this.edg[direction].id;
    }

    public CircleG getCircle() {
        return this.cir;
    }

    public void setCircle(CircleG circle) {
        this.cir = circle;
    }
}
