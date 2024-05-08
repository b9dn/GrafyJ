package init;

import gui.CircleG;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class DijkstraGraph extends Graph {

    private Queue_node[] dij = null;
    private ArrayList<Line> dijkstraLines = new ArrayList<>();
    private int nodeDijkstra; // = -1;
    private AnchorPane pane = null; // for shortest path drawing

    public DijkstraGraph(int h, int w, double a, double b) {
        super(h, w, a, b);
        dij = new Queue_node[w * h];
        nodeDijkstra = -1;
        for (int i = 0; i < w * h; i++) {
            dij[i] = new Queue_node(i, Integer.MAX_VALUE, -1);
        }
    }

    public DijkstraGraph(Graph gr) {
        this.height = gr.height;
        this.width = gr.width;
        this.nod = gr.nod;
        this.nodeDijkstra = -1;
        this.minValEdg = gr.minValEdg;
        this.maxValEdg = gr.maxValEdg;
        this.lines = gr.lines;
        dij = new Queue_node[width * height];
        for (int i = 0; i < width * height; i++) {
            if (nod[i].getCircle() != null)
                nod[i].getCircle().setOnMousePressed(circleOnMousePressedEventHandler);
            dij[i] = new Queue_node(i, Integer.MAX_VALUE, -1);
        }
    }

    public void dijkstra(int destinationId) { //destinationId - wezel od ktorego szukamy najkr odl
        this.nodeDijkstra = destinationId;
        Queue q = new Queue();
        dijFill(destinationId);
        if (nod[destinationId].getCircle() != null)
            nod[destinationId].getCircle().setOnMousePressed(circleOnMousePressedEventHandler);
        q.addToQueue(dij[destinationId]);
        while (!q.isEmpty()) {
            Queue_node act = q.popFromQueue();
            for (int i = 0; i < 4; i++) {
                if (nod[act.node].isEdg(i)) {
                    if (dij[act.node].odl + getVal(act.node, i) < dij[getEdg(act.node, i).showNode()].odl) {
                        dij[getEdg(act.node, i).showNode()].odl = dij[act.node].odl + getVal(act.node, i);
                        dij[getEdg(act.node, i).showNode()].parent = dij[act.node].node;
                        if (nod[destinationId].getCircle() != null)
                            nod[getEdg(act.node, i).showNode()].getCircle().setOnMousePressed(circleOnMousePressedEventHandler);
                        q.addToQueue(dij[getEdg(act.node, i).showNode()]);
                    }
                }
            }
        }
        dijColor();
    }

    private void drawDijkstra(AnchorPane Pane, int toNode) {
        Queue_node[] shortestPath = shortestPath(toNode);
        double x0, x1, y0, y1;
        double lineWidth = lines[0].getStrokeWidth();

        for (int i = 0; i < shortestPath.length - 1; i++) {
            x0 = getNode(shortestPath[i].node).getCircle().getCenterX();
            y0 = getNode(shortestPath[i].node).getCircle().getCenterY();
            x1 = getNode(shortestPath[i + 1].node).getCircle().getCenterX();
            y1 = getNode(shortestPath[i + 1].node).getCircle().getCenterY();

            Line l = new Line(x0, y0, x1, y1);
            l.setStroke(Color.WHITE);
            l.setStrokeWidth(1.75 * lineWidth);
            addDijkstraLine(l);
            Pane.getChildren().add(l);
        }
    }

    public Queue_node[] shortestPath(int toNode) {
        int nOfConnections = dijIlePolaczen(toNode);
        Queue_node[] ret = new Queue_node[nOfConnections];

        ret[--nOfConnections] = dij[toNode];
        while (ret[nOfConnections].parent != -1) {
            ret[--nOfConnections] = dij[ret[nOfConnections + 1].parent];
        }

        return ret;
    }

    private int dijIlePolaczen(int toNode) {
        Queue_node tmp = this.dij[toNode];
        int count = 1;
        while (tmp.parent != -1) {
            tmp = dij[tmp.parent];
            count++;
        }

        return count;
    }

    private void dijColor() {
        double saturationRotationWidth = 300 / longestPathVal();
        for (int i = 0; i < dij.length; i++) {
            double z = dij[i].odl * saturationRotationWidth;
            if (nod[i].getCircle() != null)
                nod[i].getCircle().setFill(Color.hsb(z, 1, 0.75));
        }
    }

    private double longestPathVal() {
        double ret = 0;
        for (Queue_node queueNode : dij) {
            if (queueNode.odl > ret)
                ret = queueNode.odl;
        }
        return ret;
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        cleanDijkstraLines();
                        CircleG c = (CircleG) event.getSource();
                        dijkstra(c.getNodeNmb());
                    } else if (event.getButton() == MouseButton.SECONDARY && pane != null && nodeDijkstra != -1) {
                        CircleG c = (CircleG) event.getSource();
                        drawDijkstra(pane, c.getNodeNmb());
                    }
                }
            };

    private void dijFill(int node) {
        for (int i = 0; i < width * height; i++) {
            if (i == node)
                dij[i] = new Queue_node(i, 0, -1);
            else
                dij[i] = new Queue_node(i, Integer.MAX_VALUE, -1);
        }
    }

    public void addDijkstraLine(Line l) {
        dijkstraLines.add(l);
    }

    public void cleanDijkstraLines() {
        pane.getChildren().removeAll(dijkstraLines);
        dijkstraLines.clear();
    }

    public void setPane(AnchorPane p) {
        this.pane = p;
    }
}
