package init;

import gui.CircleG;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Bfs extends Graph {

    private Queue_node[] visited = null;

    public Bfs(int height, int width, double maxVal, double minVal) {
        super(height, width, maxVal, minVal);
        visited = new Queue_node[height * width];
        visitedFill(0);
    }

    public Bfs(Graph gr) {
        this.height = gr.height;
        this.width = gr.width;
        this.nod = gr.nod;
        this.minValEdg = gr.minValEdg;
        this.maxValEdg = gr.maxValEdg;
        this.visited = new Queue_node[width * height];
        this.lines = gr.lines;
        visitedFill(0);
    }

    public boolean bfsRun() {
        Queue q = new Queue();
        nod[0].getCircle().setOnMousePressed(circleOnMousePressedEventHandler);
        q.addToQueue(visited[0]);
        Queue_node i;
        while (!q.isEmpty()) {
            i = q.popFromQueue();
            for (int j = 0; j < 4; j++) {
                if (nod[i.node].isEdg(j) && visited[nod[i.node].edgNum(j)].parent == -1) {
                    int x = nod[i.node].edgNum(j);
                    visited[x].node = x;
                    visited[x].parent = i.node;
                    visited[x].odl = nod[i.node].showValEdg(j);
                    nod[x].getCircle().setOnMousePressed(circleOnMousePressedEventHandler);
                    q.addToQueue(visited[x]);
                }
            }
        }
        for (int j = 0; j < width * height; j++) {
            if (visited[j].parent == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean bfsRunTest() {
        Queue q = new Queue();

        q.addToQueue(visited[0]);
        Queue_node i;
        while (!q.isEmpty()) {
            i = q.popFromQueue();
            for (int j = 0; j < 4; j++) {
                if (nod[i.node].isEdg(j) && visited[nod[i.node].edgNum(j)].parent == -1) {
                    int x = nod[i.node].edgNum(j);
                    visited[x].node = x;
                    visited[x].parent = i.node;
                    visited[x].odl = nod[i.node].showValEdg(j);

                    q.addToQueue(visited[x]);
                }
            }
        }
        for (int j = 0; j < width * height; j++) {
            if (visited[j].parent == -1) {
                return false;
            }
        }
        return true;
    }

    public void bfsColor(int node) {
        double saturationRotationWidth = (double) 1 / (width + height) * 360;

        visitedFill(node);
        Queue q = new Queue();
        nod[node].getCircle().setFill(Color.hsb(0, 1, 0.75));
        q.addToQueue(visited[node]);
        Queue_node i;
        while (!q.isEmpty()) {
            i = q.popFromQueue();
            for (int j = 0; j < 4; j++) {
                if (nod[i.node].isEdg(j) && visited[nod[i.node].edgNum(j)].parent == -1) {
                    int x = nod[i.node].edgNum(j);
                    visited[x].node = x;
                    visited[x].parent = i.node;
                    visited[x].odl = visited[visited[x].parent].odl + 1;
                    double z = visited[x].odl * saturationRotationWidth;
                    nod[x].getCircle().setFill(Color.hsb(z, 1, 0.75));
                    q.addToQueue(visited[x]);
                }
            }
        }
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    CircleG c = (CircleG) t.getSource();
                    bfsColor(c.getNodeNmb());
                }
            };

    private void visitedFill(int node) {
        for (int i = 0; i < width * height; i++) {
            if (i == node)
                visited[i] = new Queue_node(i, 0, i);
            else
                visited[i] = new Queue_node(i, Integer.MAX_VALUE, -1);
        }
    }
}
