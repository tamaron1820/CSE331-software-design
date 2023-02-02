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
 * This class contains a set of test cases that can be used to test the implementation of the Edges
 * class.
 *
 */
public class EdgeTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private final Node<String> n1 = new Node<>("n1");
    private final Node<String> n2 = new Node<>("n2");
    private final Node<String> n3 = new Node<>("n3");
    private final Edge<String,String> e1= new Edge<>("e1",n2);
    private final Edge<String,String> e2= new Edge<>("e2",n3);
    private final Edge<String,String> e3 = new Edge<>("e3",n1);
    private final Edge<String,String> e4= new Edge<>("e1",n2);
    private final Edge<String,String> e5= new Edge<>("e1",n3);
    @Test
    public void testGetLabelName() {
        assertEquals(e1.getLabelName(),"e1");
        assertEquals(e2.getLabelName(),"e2");
        assertEquals(e3.getLabelName(),"e3");
    }

    @Test
    public void testGetChildName() {
        assertEquals(e1.getChildName(),n2);
        assertEquals(e2.getChildName(),n3);
        assertEquals(e3.getChildName(),n1);
    }

    @Test
    public void testEquals() {
        assertTrue(e1.equals(e4));
        assertFalse(e1.equals(e5));
    }

    @Test
    public void testHashCode() {
        assertEquals(e1.hashCode(),"e1".hashCode()^n2.hashCode());
        assertEquals(e3.hashCode(),"e3".hashCode()^n1.hashCode());
    }
}
