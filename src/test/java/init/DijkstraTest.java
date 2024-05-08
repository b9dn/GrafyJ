package init;

import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {

    @Test
    public void dijkstraShortestPathTest() throws MyException, IOException {
        Graph g = ReadGraph.readGraph("src/test/resources/dijkstraShortestPathTest");
        DijkstraGraph gr = new DijkstraGraph(g);
        gr.dijkstra(0);
        Queue_node[] t = gr.shortestPath(10);

        assertEquals(t[0].node, 0);
        assertEquals(t[1].node, 1);
        assertEquals(t[2].node, 5);
        assertEquals(t[3].node, 6);
        assertEquals(t[4].node, 7);
        assertEquals(t[5].node, 11);
        assertEquals(t[6].node, 10);
    }
}
