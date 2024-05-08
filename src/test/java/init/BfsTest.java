package init;

import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BfsTest {

    @Test
    public void bfsTest_spojny() throws MyException, IOException {
        Graph g = ReadGraph.readGraph("src/test/resources/mygraph");
        Bfs b = new Bfs(g);
        assertTrue(b.bfsRunTest());
    }

    @Test
    public void bfsTest_niespojny() throws MyException, IOException {
        Graph g = ReadGraph.readGraph("src/test/resources/niespojny");
        Bfs b = new Bfs(g);
        assertFalse(b.bfsRunTest());
    }
}
