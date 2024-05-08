package init;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ReadGraphTest {

    @Test
    public void testReadGraph_correctGraph() throws Exception {
        String path = "src/test/resources/mygraph";

        Graph g = ReadGraph.readGraph(path);
        Node result = g.getEdg(0, 3);
        double a = g.getVal(0, 1);

        assertNull(result);
        assertEquals(0.2187532451857941, a, 0);
    }

    @Test(expected = MyException.class)
    public void testReadGraph_wrongGraphDimensions() throws MyException, IOException {
        ReadGraph.readGraph("src/test/resources/wrongGraphDimensions");

        assert false;
    }

    @Test(expected = MyException.class)
    public void testReadGraph_wrongGraphDimensions2() throws MyException, IOException {
        ReadGraph.readGraph("src/test/resources/wrongGraphDimensions2");

        assert false;
    }

    @Test(expected = MyException.class)
    public void testReadGraph_wrongCharacter() throws MyException, IOException {
        ReadGraph.readGraph("src/test/resources/wrongCharacter");

        assert false;
    }

    @Test(expected = MyException.class)
    public void testReadGraph_negativeValue() throws MyException, IOException {
        ReadGraph.readGraph("src/test/resources/negativeValue");

        assert false;
    }
}
