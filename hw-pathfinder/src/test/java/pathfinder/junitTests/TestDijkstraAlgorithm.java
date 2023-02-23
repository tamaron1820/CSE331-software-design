package pathfinder.junitTests;

import graph.*;
import pathfinder.DijkstraAlgorithm;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * This class is the test class of DijkstraAlgorithm class.
 */
public class TestDijkstraAlgorithm {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private Graph<String,Double> graph;
    private DijkstraAlgorithm shortestPath;
    private final Node<String> n1 = new Node<>("n1");
    private final Node<String> n2 = new Node<>("n2");
    private final Node<String> n3 = new Node<>("n3");

    @Before
    public void setUp() throws Exception {
        graph = new Graph<>();
        shortestPath = new DijkstraAlgorithm();
        graph.addNode(n1);
        graph.addNode(n2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestShortestPathNullGraph () {
        shortestPath.findShortestPath(null,n1.getParentName(),n2.getParentName());
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestShortestPathNullStartNode () {
        shortestPath.findShortestPath(graph,null,n2.getParentName());
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestShortestPathNullEndNode () {
        shortestPath.findShortestPath(graph,n1.getParentName(),null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestShortestPathNotContainedStart () {
        shortestPath.findShortestPath(graph, n3.getParentName(),n2.getParentName());
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestShortestPathNotContainedEnd() {
        shortestPath.findShortestPath(graph, n1.getParentName(),n3.getParentName());
    }

    @Test (expected = IllegalArgumentException.class)
    public void TestShortestPathEmptyGraph () {
        shortestPath.findShortestPath(new Graph<>(),n1.getParentName(),n2.getParentName());
    }

}
