package graph;

import java.util.*;
/**
 * <b>Edge</b> is an immutable representation of Edge in a directed graph.Each Edge has
 * edge name which is labeled and child Node name.
 *
 * <p> Edge can be notated by pair (E . N), where E is an edge name, N is a
 * child name.
 *
 */
public class Edge <E,N>{
    /**
    * Holds labels in all Edges
    */
    private final E label;
    /**
     * Holds children name in all Edges
     */
    private final Node<N> child;
    // Abstract Function:
    // Edge e, represents an edge and a child whose edge is pointing
    //
    // Representation Invariant for every Edge:
    // Edges and children are not null
    // label != null && child != null

    /**
     * Constructs a new Edge
     * @param label the label name in the edge
     * @param child the child name in the edge
     * @spec.requires {@code label != null && child != null}
     * @spec.effects Constructs a new edge which edge name is label and
     * child name is child
     */
    public Edge(E label, Node<N> child) {
        this.label = label;
        this.child = child;
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.label != null && this.child!=null);
    }

    /**
     * Returns label name of this Edge
     *
     * @return the name of this edge
     * @spec.requires this != null
     */
    public E getLabelName(){
        checkRep();
        return this.label;
    }

    /**
     * Returns child name of this Edge
     *
     * @return the name of the Edge
     * @spec.requires this != null
     */
    public Node<N> getChildName() {
        checkRep();
        return this.child;
    }
    /**
     * Returns a boolean of this and obj
     *
     * @param obj an object to be compared with this
     * @return true if and only if instance of obj is Edge and label name and
     * child name of obj are same as this.
     * @spec.requires object != null
     */
    @Override
    public boolean equals(Object obj) {
        checkRep();
        if(this == obj) {
            return true;
        } else if(!(obj instanceof Edge<?,?>)){
            return false;
        } else {
            Edge<?,?> edge = (Edge<?, ?>) obj;
            return edge.label.equals(this.label) && edge.child.equals(this.child);
        }
    }
    /**
     * Returns hash code
     *
     * @return integer hash code
     */
    @Override
    public int hashCode() {
        return label.hashCode()* child.hashCode();
    }
}
