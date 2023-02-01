package graph;

/**
 * <b><Node/b> is an immutable representation of parent node. Each Node has
 * a parent node
 *
 * <p> Node can be notated by N, where N is a name of node
 */
public class Node <N>{
    /**
     * Holds parent name
     */
    private final Node<N> parent;

    // Abstract Function:
    // Node n, represents a  parent node
    //
    // Representation Invariant for every node:
    // parent is not null
    // parent != null

    /**
     * Consstructs a new Node
     * @param parent
     * @spec.requires {@code parent !=null}
     * @spec.effects Constructs a new node which node name is parent
     */
    public Node(Node<N> parent) {
        this.parent = parent;
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep(){
        assert (this.parent != null);
    }

    /**
     * Returns parent name of this node
     *
     * @return the name of this node
     * @spec.requires this != null
     */
    public Node<N> getParentName() {
        checkRep();
        return this.parent;
    }
    /**
     * Returns a boolean of this and obj
     *
     * @param obj an object to be compared with this
     * @return true if and only if instance of obj is node and parent name of obj are same as this.
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
           Node<?> node = (Node<?>) obj;
           return node.parent.equals(this.parent);
        }
    }

    /**
     * Returns hash code
     *
     * @return integer hash code
     */
    @Override
    public int hashCode() {
        return parent.hashCode();
    }
}
