package graph;

import java.util.*;

/**
 * <b>BaseGraph</b> represents the interface of Graph.
 *
 * <p> BaseGraph can be notated by pair (N . E), where N is a parent Node, E is a
 * pair of (E , T) where E is an edge name, T is a child name.
 *
 */
public interface BaseGraph<N,E> {
    // Abstraction Function:
    // Two nodes are not always same
    /**
     * Add a new Node which is not contained in graph.
     *
     * @param n is a name of the new parent node
     * @spec.requires n != null
     * @spec.modifies this
     * @spec.effects add a given node in the graph
     */
    public void addNode(Node<N> n);

    /**
     * Add a new Edge which is not already contained in graph
     *
     * @param parent is a parent name of the edge
     * @param edgename is an edgename
     * @param child is a child name of the edge
     * @spec.requires parent and edgename, child is not null
     * @spec.modifies this
     * @spec.effects add a given edge in the graph
     * @throws IllegalArgumentException if parent and edgename, child is null or
     * parent and edgename, child does not contain in graph
     */
    public void addEdge(Node<N> parent,E edgename,Node<N> child);

    /**
     * Check if given parent and edgename are pointed to correct child
     * @param parent
     * @param edgename
     * @param child
     * @spec.requires parent and edgename, child is not null
     * @spec.modifies this
     * @throws IllegalArgumentException if parent and edgename, child is null
     * @return true if and only if given parent and edgename are pointed to
     * correct child name
     */
    public boolean isConnected(Node<N>parent,E edgename,Node<N> child);

    /**
     * Returns Nodes which are connected with given parent node
     *
     * @param parent
     * @spec.requires parent!=null
     * @return nodes which are connected with given node
     */
    public Set<Node<N>> getChildNode(Node<N> parent);

    /**
     * Returns all nodes which are contained in graph
     *
     * @return all nodes which are contained in graph
     */
    public Set<Node<N>> getAllNode();

    /**
     * Returns all edges which are contained in graph
     *
     * @param parent is a parent name
     * @return all edges which are contained in graph
     */
    public Set<Edge<E,N>> getAllEdge(Node<N> parent);

    /**
     * Returns an edge between parent and child
     *
     * @param parent is a parent name of graph
     * @param child is a child name of graph
     * @throws IllegalArgumentException if parent and child are null
     * @return a edge between parent node and child node
     */
    public E getEdge(Node<N> parent, Node<N> child);

    /**
     * Returns whether node is included in graph
     *
     * @param nodes is a name of node for checking whether the node is
     * included in graph
     * @throws IllegalArgumentException if the graph is null
     * @return true if the node is included in graph
     */
    public boolean containedNode(Node<N> nodes);
}
