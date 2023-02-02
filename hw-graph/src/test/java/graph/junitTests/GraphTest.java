package graph.junitTests;

import graph.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * This class contains a set of test cases that can be used to test the implementation of the Graph
 * class.
 *
 */
public class GraphTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private Graph<String,String> graph1;
    private final Node<String> n1 = new Node<>("n1");
    private final Node<String> n2 = new Node<>("n2");
    private final Node<String> n3 = new Node<>("n3");
    private final Node<String> n4 = new Node<>("n4");
    private final Node<String> n5 = new Node<>("n5");
    @Before
    public void setUp() {
        graph1 = new Graph<>();
    }

    @Test
    public void testAddNodes () {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        assertFalse(graph1.graphSize()==5);
        assertTrue(graph1.graphSize()==3);
    }

    @Test
    public void testIsConnected() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(n3,"e3",n1);
        graph1.addEdge(n2,"e4",n1);
        assertTrue(graph1.isConnected(n3,"e3",n1));
        assertFalse(graph1.isConnected(n2,"e4",n3));
        assertTrue(graph1.isConnected(n2,"e4",n1));
    }

    @Test
    public void testGetChildNode() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(n1,"e1",n2);
        graph1.addEdge(n1,"e2",n3);
        Set<Node<String>> childNodes = new HashSet<>();
        childNodes.add(n2);
        childNodes.add(n3);
        assertEquals(graph1.getChildNode(n1),childNodes);
    }

    @Test
    public void testGetAllNodes() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        Set<Node<String>> node = new HashSet<>();
        node.add(n1);
        node.add(n2);
        node.add(n3);
        assertEquals(graph1.getAllNode(),node);
    }

    @Test
    public void testGetAllEdge() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(n1,"e1",n2);
        graph1.addEdge(n1,"e2",n3);
        Set<Edge> node = new HashSet<>();
        node.add(new Edge<>("e1",n2));
        node.add(new Edge<>("e2",n3));
        assertEquals(graph1.getAllEdge(n1),node);
    }

    @Test
    public void testClearGraph() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(n1,"e1",n2);
        graph1.addEdge(n1,"e2",n3);
        graph1.clearGraph();
        assertTrue(graph1.getAllNode().isEmpty());
    }
    @Test
    public void testGetEdge() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(n1,"e1",n2);
        graph1.addEdge(n1,"e2",n3);
        assertEquals(graph1.getEdge(n1,n2),"e1");
        assertEquals(graph1.getEdge(n1,n3),"e2");
    }
    @Test
    public void testContainedNodes() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        assertTrue(graph1.containedNode(n1));
        assertTrue(graph1.containedNode(n3));
        assertFalse(graph1.containedNode(n4));
        assertFalse(graph1.containedNode(n5));
    }

    @Test
    public void testAddEdgesWithSameNodes() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addEdge(n1,"e1",n2);
        graph1.addEdge(n1,"e2",n2);
        Set<Edge> node = new HashSet<>();
        node.add(new Edge<>("e1",n2));
        node.add(new Edge<>("e2",n2));
        assertEquals(graph1.getAllEdge(n1),node);
    }

    @Test
    public void testAddExistedEdges() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addEdge(n1,"e1",n2);
        graph1.addEdge(n1,"e1",n2);
        graph1.addEdge(n1,"e2",n3);
        Set<Edge> node = new HashSet<>();
        node.add(new Edge<>("e1",n2));
        node.add(new Edge<>("e2",n3));
        assertEquals(graph1.getAllEdge(n1),node);
    }

    @Test
    public void testAddSameNodes() {
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addNode(n1);
        graph1.addNode(n3);
        assertEquals(graph1.graphSize(),3);
    }

}
