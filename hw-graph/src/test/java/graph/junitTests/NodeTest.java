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
 * This class contains a set of test cases that can be used to test the implementation of the Node
 * class.
 *
 */
public class NodeTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private final Node<String> n1 = new Node<>("n1");
    private final Node<String> n2 = new Node<>("n1");
    private final Node<String> n3 = new Node<>("n3");

    @Test
    public void testGetParentName() {
        assertEquals(n1.getParentName(),"n1");
        assertEquals(n3.getParentName(),"n3");
    }

    @Test
    public void testEquals() {
        assertTrue(n1.equals(n2));
        assertFalse(n2.equals(n3));
    }

    @Test
    public void testHashCode() {
        assertEquals(n1.hashCode(),n2.hashCode());
    }
}