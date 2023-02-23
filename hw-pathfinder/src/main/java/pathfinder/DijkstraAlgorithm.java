package pathfinder;

import graph.*;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * This class implements Dijkstra algorithm. This algorithm
 * is used for finding shortest path between two Nodes.
 */

public class DijkstraAlgorithm {

    /**
     *  Return shortest path which is found by using Dijkstra's algorithm.
     *
     * @param graph graph which is for finding shortest path
     * @param startNode start of node of shortest path
     * @param destNode end of node of shortest path
     * @return shortest path which is found by Dijkstra's algorithm, otherwise return null
     * @param <N> node type in the graph
     * @throws IllegalArgumentException if graph is null or either start node
     * or dest node is null
     */
    public static <N> Path<N> findShortestPath (Graph<N,Double> graph, N startNode, N destNode) {
        if (graph == null) throw new IllegalArgumentException("Graph should not be null");
        if ( startNode == null || destNode == null) throw new IllegalArgumentException("startNode and destNode should not be null");
        if (!(graph.containedNode(new Node<>(startNode)) && graph.containedNode(new Node<>(destNode)))) throw new IllegalArgumentException("The nodes are not contained in the graph");
        Queue<Path<N>> active = new PriorityQueue<>(new shortPathComparetor());
        Set<Node<N>> finished = new HashSet<>();
        active.add(new Path<>(startNode));

        while (!active.isEmpty()) {
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();
            Node<N> minNode = new Node<>(minPath.getEnd());
            if (minDest.equals(destNode)) {
                return minPath;
            } else if(finished.contains(minNode)) {
                continue;
            } else {
                List<Edge<Double,N>> listedge = new ArrayList<>(graph.getAllEdge(minNode));
                for (Edge<Double,N> e: listedge) {
                    if(!finished.contains(e.getChildName())) {
                        Path<N> newPath = minPath.extend(e.getChildName().getParentName(), e.getLabelName());
                        active.add(newPath);
                    }
                }
            }
            finished.add(minNode);
        }
        return null;
    }

    private static class shortPathComparetor implements Comparator<Path<?>> {

        /**
         *
         * @param p1 the first object to be compared.
         * @param p2 the second object to be compared.
         * @return 1 if cost of first path is larger than cost of second path
         * -1 if cost of the second path is larger than cost of second path.
         * @throws IllegalArgumentException if either first path or second path is null
         */
        public int compare ( Path<?> p1, Path<?> p2) {
            if (p1 == null || p2 == null) throw new IllegalArgumentException("both path should not be null");
            double p1Cost = p1.getCost();
            double p2Cost = p2.getCost();
            double costDifference = p1Cost - p2Cost;
            if( costDifference > 0 ){
                return 1;
            } else {
                return -1;
            }
        }
    }
}
