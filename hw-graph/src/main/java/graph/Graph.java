package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <b>Graph </b> represents a mutable directed graph.
 *
 * <p> Graph can be notated by pair (N . E), where N is a parent Node, E is a
 * pair of (E , T) where E is an edge name, T is a child name.
 *
 */
public class Graph<N,E> implements BaseGraph<N,E> {
    /**
     * Holds graph which includes Node which represents parent node
     * and Set which is a pair of Edge name and child name
     */
    private Map<Node<N>, Set<Edge<E,N>>> graph;

    public static final boolean DEBUG = false;

    // Abstract Function:
    // Graph g represents a map of Node which has parent name of node
    // and child whihc is connected with parent
    // and Edge which is for connecting child with parent
    //
    // Representation Invariant for every Graph:
    // graph!=null &&
    // for all nodes are not null and no duplicate
    // for all Edges are not null and no duplicate

    /**
     * Constructs a new Graph
     *
     * @spec.effects Constructs a new graph
     */
    public Graph() {
        graph = new HashMap<>();
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    public void checkRep() {
        assert (graph!=null) : "graph should not be null";
        if(DEBUG) {
            for (Node<N> n: graph.keySet()) {
                assert (n!=null): "Parent node should not be null";
                for(Edge<E,N> e: graph.get(n)){
                    assert (e!=null) :"edge should not be null";
                    Set<Edge<E,N>> contained = new HashSet<>();
                    assert(!contained.contains(e)) : "Edge should not be duplicate";
                    contained.add(e);                }
            }
        }
    }

    /**
     * Add a new Node which is not contained in graph.
     *
     * @param n is a name of the new parent node
     * @spec.requires n != null
     * @spec.modifies this
     * @spec.effects add a given node in the graph
     */
    @Override
    public void addNode(Node<N> n) {
        checkRep();
        if(!graph.containsKey(n)) {
            graph.put(n,new HashSet<>());
            checkRep();
        }
    }

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
    @Override
    public void addEdge(Node<N> parent, E edgename, Node<N> child) {
        checkRep();
        if(parent == null || edgename ==null || child==null) throw new IllegalArgumentException("all parent and edgename and child should not be null");
        if(!graph.containsKey(parent)){
            throw new IllegalArgumentException("Parent is not contained in graph");
        } else if(!graph.containsKey(child)){
            throw new IllegalArgumentException("child is not contained in graph");
        } else{
            graph.get(parent).add(new Edge<>(edgename,child));
            checkRep();
        }
    }

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
    @Override
    public boolean isConnected(Node<N> parent, E edgename, Node<N> child) {
        checkRep();
        if(parent == null || edgename ==null || child==null) throw new IllegalArgumentException("all parent and edgename and child should not be null");
        if(!graph.containsKey(parent)){
            throw new IllegalArgumentException("Parent is not contained in graph");
        } else if(!graph.containsKey(child)){
            throw new IllegalArgumentException("child is not contained in graph");
        } else {
            for (Edge<E, N> e : graph.get(parent)) {
                if ((e.getChildName().equals(child)) && (edgename == null || e.getLabelName().equals(edgename))) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns Nodes which are connected with given parent node
     *
     * @param parent
     * @spec.requires parent!=null
     * @return nodes which are connected with given node
     */
    @Override
    public Set<Node<N>> getChildNode(Node<N> parent) {
        checkRep();
        Set<Node<N>> childNode = new HashSet<>();
        for (Edge<E,N> e:graph.get(parent)) {
            childNode.add((Node<N>) e.getChildName());
        }
        checkRep();
        return childNode;
    }

    /**
     * Returns all nodes which are contained in graph
     *
     * @return all nodes which are contained in graph
     */
    @Override
    public Set<Node<N>> getAllNode() {
        checkRep();
        return new HashSet<>(graph.keySet());
    }

    /**
     * Returns all edges which are contained in graph
     *
     * @param parent is a parent name
     * @return all edges which are contained in graph
     */
    @Override
    public Set<Edge<E,N>> getAllEdge(Node<N> parent) {
        checkRep();
        return new HashSet<>(graph.get(parent));
    }

    /**
     * Clear graph
     *
     * @spec.effect clear all nodes and edges
     */
    public void clearGraph() {
        checkRep();
        graph.clear();
    }

    /**
     * Returns an edge between parent and child
     *
     * @param parent is a parent name of graph
     * @param child  is a child name of graph
     * @return a edge between parent node and child node
     * @throws IllegalArgumentException if parent or child are null
     */
    @Override
    public E getEdge(Node<N> parent, Node<N> child) {
        checkRep();
        if(parent == null || child == null) {
            throw new IllegalArgumentException("parent or child should not be null");
        } else {
            for (Edge<E,N> e: graph.get(parent)) {
                if (e.getChildName().equals(child)) {
                    return e.getLabelName();
                }
            }
            return null;
        }
    }

    /**
     * Returns whether node is included in graph
     *
     * @param nodes is a name of node for checking whether the node is
     * included in graph
     * @throws IllegalArgumentException if the graph is null
     * @return true if the node is included in graph
     */
    @Override
    public boolean containedNode(Node<N> nodes) {
        if(this.graph == null) throw new IllegalArgumentException();
        return graph.containsKey(nodes);
    }

    /**
     * Returns a size of graph
     *
     * @return a size of graph
     */
    public int graphSize() {
        checkRep();
        return graph.size();
    }
}