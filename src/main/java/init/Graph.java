package init;

import javafx.scene.shape.Line;

import java.io.FileWriter;
import java.io.IOException;

public class Graph {

    protected int height;
    protected int width;
    protected Node[] nod = null;
    protected double minValEdg;
    protected double maxValEdg;
    protected Line[] lines = null;
    private int actNmbOfLines = 0;

    public Node getNode(int id) {
        return this.nod[id];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void connectNode(int first, int direction, int second) {
        nod[first].connect(direction, nod[second]);
    }

    public void setVal(int id, int direction, double value) {
        nod[id].setVal(direction, value);
    }

    public double getVal(int id, int direction) {
        return nod[id].showValEdg(direction);
    }

    public Node getEdg(int id, int direction) {
        return nod[id].edgPointer(direction);
    }

    public int EdgNum(int id, int direction) {
        return nod[id].edgNum(direction);
    }

    public double getMinValEdg() {
        return this.minValEdg;
    }

    public double getMaxValEdg() {
        return this.maxValEdg;
    }

    public void setMinValEdg(double x) {
        this.minValEdg = x;
    }

    public void setMaxValEdg(double x) {
        this.maxValEdg = x;
    }

    public Line[] getLines() {
        return this.lines;
    }

    public void setLine(Line l) {
        this.lines[actNmbOfLines++] = l;
    }


    public Graph() {
    }

    public Graph(int height, int width) {
        this.height = height;
        this.width = width;
        int nmbOfLines = (2 * width - 1) * (2 * height - 1) - 1;
        this.lines = new Line[nmbOfLines];
        this.nod = new Node[height * width];
        for (int i = 0; i < width * height; i++) {
            this.nod[i] = new Node(i);
        }
    }

    public Graph(int height, int width, double minVal, double maxVal) {
        this.height = height;
        this.width = width;
        this.minValEdg = minVal;
        this.maxValEdg = maxVal;
        int nmbOfLines = (2 * width - 1) * (2 * height - 1) - 1;
        this.lines = new Line[nmbOfLines];
        this.nod = new Node[height * width];
        for (int i = 0; i < width * height; i++) {
            this.nod[i] = new Node(i);
        }

        int column = 1, row = 1;
        /////////////////////////////////
        for (int i = 0; i < height * width; i++) {
            if (column > this.width) {
                column = 1;
                row++;
            }
            if (i - this.width >= 0) { //sprawdzenie czy polaczenie w gore
                nod[i].connect(0, nod[i - this.width]);
                nod[i].randEdg(0, minVal, maxVal);
            }
            if (i + 1 < row * this.width) { //prawo
                nod[i].connect(1, nod[i + 1]);
                nod[i].randEdg(1, minVal, maxVal);
            }
            if (i + this.width < width * height) { //dol
                nod[i].connect(2, nod[i + this.width]);
                nod[i].randEdg(2, minVal, maxVal);
            }
            if (i - 1 >= (row - 1) * this.width) { //gora
                nod[i].connect(3, nod[i - 1]);
                nod[i].randEdg(3, minVal, maxVal);
            }
            column++;
        }
        /////////////////////////////////
    }

    public void writeToFile(String filename) throws IOException {
        int x = width * height;
        FileWriter fw = new FileWriter(filename);

        fw.write(height + " " + width);
        fw.write("\n");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < 4; j++) {
                if (nod[i].isEdg(j))
                    fw.write(EdgNum(i, j) + " :" + getVal(i, j) + " ");
            }
            fw.write("\n");
        }
        fw.close();
    }

}
