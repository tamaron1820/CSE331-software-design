package marvel.junitTests;

import graph.Graph;
import marvel.MarvelParser;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Iterator;

/**
 * This class is the test class of marvel parser class.
 */
public class MarvelParserTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private Graph<String, String> graph;

    @Before
    public void setUp() throws Exception {
        graph = MarvelPaths.makeGraph("soccerPlayer.csv");
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestMakeInvalidFileName() {
        MarvelPaths.makeGraph("412512@231er//");
    }
    @Test (expected = IllegalArgumentException.class)
    public void TestMakeNullFile() {
        MarvelPaths.makeGraph(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestDestinationNull() {
        MarvelPaths.shortestPathBFS("n1",null,graph);
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestStartNull() {
        MarvelPaths.shortestPathBFS(null,"n1",graph);
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestMakeNoExistedFile() {
        MarvelPaths.makeGraph("soccer.csv");
    }
}
