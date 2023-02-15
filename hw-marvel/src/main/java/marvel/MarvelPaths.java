package marvel;
import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.*;

/**
 * This MarvelPaths class implements the graph of comic name and character name
 * and find a path between characters by using BFS
 */

public class MarvelPaths {
    /**
     * Make a graph which based on data of file. Graph are made in
     * form of node which includes character name and edge which includes
     * comic name and character name
     *
     * @param filename the file name that which includes comic information
     * @throws IllegalArgumentException if filename == null
     * @return Graph which includes information which are included filename
     */
    public static Graph<String, String> makeGraph(String filename) {
        if (filename==null) throw new IllegalArgumentException("Filename is null");

        Map<String, List<String>> comic = new HashMap<>();
        comic = MarvelParser.parseData(filename);
        Graph<String, String> baseGraph = new Graph<>();
        for(String comicName : comic.keySet()) {
            List<String> charaName = comic.get(comicName);
            baseGraph.addNode(new Node<>(charaName.get(0)));
            for ( int i=0; i< charaName.size(); i++ ){
                Node<String> parentChara = new Node<>(charaName.get(i));
                for ( int j=i+1; j<charaName.size() ;j++) {
                    Node<String> childChara = new Node<>(charaName.get(j));
                    baseGraph.addNode(childChara);
                    baseGraph.addEdge(parentChara,comicName,childChara);
                    baseGraph.addEdge(childChara,comicName,parentChara);
                }
            }
        }
        return baseGraph;
    }

    /**
     * Return shortest path between two nodes what are given by users.
     * This path is found by using Breadth-First Search,
     *
     * @param start start node of the path
     * @param destination destination node of the path
     * @param graph the Graph what are explored
     * @throws IllegalArgumentException if graph is null or if start node or destination node is null.
     * @return A list which includes shortest path which is Edge information. This edge includes
     * label name of edge and childNode
     */
    public static List<Edge<String, String>> shortestPathBFS (String start, String destination, Graph<String, String> graph)  {
        if(graph == null) throw new IllegalArgumentException("Graph is null");
        if(start == null || destination == null) throw new IllegalArgumentException("Either start or destination is null");
        Node<String> nodeStart = new Node<>(start);
        Node<String> nodeDestination = new Node<>(destination);
        Queue<Node<String>> worklist = new LinkedList<>();
        Map<Node<String>,List<Edge<String,String>>> paths = new HashMap<>();

        worklist.add(nodeStart);
        paths.put(nodeStart,new ArrayList<>());

        while(!worklist.isEmpty()) {
            Node<String> nextNode = worklist.remove();
            if (nextNode.equals(nodeDestination)) {
                return new ArrayList<>(paths.get(nextNode));
            }else {
                List<Edge<String, String>> edgeList = new ArrayList<>(graph.getAllEdge(nextNode));
                edgeList.sort(new EdgeCompare());
                for (Edge<String, String> edge : edgeList) {
                    if (!paths.containsKey(edge.getChildName())) {
                        List<Edge<String, String>> p = new ArrayList<>(paths.get(nextNode));
                        List<Edge<String, String>> pDush = new ArrayList<>(p);
                        pDush.add(edge);
                        paths.put(edge.getChildName(), pDush);
                        worklist.add(edge.getChildName());
                    }
                }
            }
        }
        return null;
    }


    private static class EdgeCompare implements Comparator<Edge<String,String>> {
        /**
         * Compares two edges where child nodes are compared first, followed by edge label names.
         *
         * @param edge1 first edge
         * @param edge2 second edge
         * @return They are compared by alphabetically order.
         * A negative integer if first edge is  less than second edge,
         * a positive integer if first edge is greater than second edge,
         * 0 if first edge is equal to second edge.
         * @throws IllegalArgumentException if either of given edges are null
         */
        public int compare(Edge<String,String> edge1, Edge<String,String> edge2) {
            if (edge1 == null || edge2 == null) throw new IllegalArgumentException();
            if (edge1.getChildName().getParentName().compareTo(edge2.getChildName().getParentName()) != 0) {
                return edge1.getChildName().getParentName().compareTo(edge2.getChildName().getParentName());
            }
            return edge1.getLabelName().compareTo(edge2.getLabelName());
        }
    }

    /**
     * To find the shortest path between two characters what are
     * given by users.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        Graph<String,String> marvel = MarvelPaths.makeGraph("marvel.csv");
        System.out.println("Welcome to Marvel BFS search");
        System.out.println("Input two characters of marvel");
        Scanner chara = new Scanner(System.in);
        boolean keep = true;
        while(keep) {
            System.out.println("Please enter first character :");
            Node<String> charaFirst = new Node<>(chara.nextLine());
            System.out.println("Please enter second character :");
            Node<String> charaSecond = new Node<>(chara.nextLine());
            if (!(marvel.containedNode(charaFirst) || marvel.containedNode(charaSecond))) {
                System.out.println("The characters are not included in the graph");
            }
            String result = "path from " + charaFirst.getParentName() + " to " + charaSecond.getParentName() + ":";
            System.out.println(result);
            List<Edge<String, String>> shortestpath = MarvelPaths.shortestPathBFS(charaFirst.getParentName(),charaSecond.getParentName(),marvel);
            if ( shortestpath == null) {
                System.out.println("no path");
            } else {
                String parentName = charaFirst.getParentName();
                for ( Edge<String,String > e: shortestpath) {
                    System.out.println("From " + charaFirst.getParentName() + " to " + parentName + " via " + e.getLabelName());
                    parentName = e.getChildName().getParentName();
                }
            }
            System.out.println("Would you like to continue(yes/no)");
            String answer = chara.nextLine();
            if(!answer.trim().equalsIgnoreCase("yes")) {
                System.out.println("Stop finding path");
                keep=false;
                chara.close();
            }

        }
    }
}
