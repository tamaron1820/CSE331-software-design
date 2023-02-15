/*
 * Copyright (C) 2023 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2023 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel.scriptTestRunner;

import graph.Edge;
import graph.Graph;
import graph.Node;
import marvel.MarvelPaths;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    private final Map<String, Graph<String, String>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;
    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException{
        // TODO: Implement this.
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }
    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "LoadGraph" :
                    loadGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }
    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }
    private void createGraph(String graphName) {
        graphs.put(graphName,new Graph<>());
        output.println("created graph " + graphName);
    }
    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        // TODO Insert your code here.
        Graph<String, String> graph1 = graphs.get(graphName);
        graph1.addNode(new Node<>(nodeName));
        output.println("added node " + nodeName +  " to " + graphName);
    }
    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        // TODO Insert your code here.
        Graph<String, String> graph1 = graphs.get(graphName);
        Node<String> n1 = new Node<>(parentName);
        Node<String> n2 = new Node<>(childName);
        graph1.addEdge(n1,edgeLabel,n2);
        output.println("added edge " + edgeLabel + " from " + parentName + " to "
                + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // TODO Insert your code here.
        Graph<String,String> graph1 = graphs.get(graphName);
        String nodeList = graphName + " contains:";
        List<Node<String>> nodes = new ArrayList<>(graph1.getAllNode());
        nodes.sort(new NodeCompare());
        for (Node<String> n: nodes) nodeList += " " + n.getParentName();
        output.println(nodeList);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        // TODO Insert your code here.
        Graph<String,String> graph1 = graphs.get(graphName);
        List<Edge<String,String>> edges1 = new ArrayList<>(graph1.getAllEdge(new Node<>(parentName)));
        edges1.sort(new EdgeCompare());
        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (Edge e: edges1) output.print(" " + e.getChildName().getParentName() + "(" + e.getLabelName() + ")");
        output.println();
    }

    private void loadGraph(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String fileName = arguments.get(1);
        loadGraph(graphName,fileName);
    }
    private void loadGraph(String graphName, String fileName) {
        Graph<String,String> marvelGraph = MarvelPaths.makeGraph(fileName);
        graphs.put(graphName,marvelGraph);
        output.println("loaded graph " + graphName);
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String destination = arguments.get(2);
        findPath(graphName, start, destination);
    }

    private void findPath(String graphName, String start, String destination) {
        Graph<String, String> graph = graphs.get(graphName);
        Node<String> startNode = new Node<>(start);
        Node<String> destinationNode = new Node<>(destination);

        if (!graph.containedNode(startNode) || !graph.containedNode(destinationNode)) {
            if(!graph.containedNode(startNode)) {
                output.println("unknown: "+start);
            }
            if(!graph.containedNode(destinationNode)) {
                output.println("unknown: "+destination);
            }
        } else {
            output.println("path from " +start +" to " + destination+ ":");
            List<Edge<String ,String >> shortestPath = MarvelPaths.shortestPathBFS(start,destination,graph);
            if ( shortestPath == null) {
                output.println("no path found");
            } else{
                String parent= start;
                for ( Edge<String,String > e: shortestPath) {
                    output.println(parent + " to " + e.getChildName().getParentName() + " via " + e.getLabelName());
                    parent = e.getChildName().getParentName();
                }
            }
        }
    }

    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
    /**
     * Implements a Comparator to compare two nodes
     */
    private static class NodeCompare implements Comparator<Node<String>> {
        /**
         * Compare two nodes by alphabetically
         * @param node1 First node looked at
         * @param node2 Second node looked at
         * @return a negative integer if first node is less than second node,
         * a positive integer if first node is greater than second node,
         * 0 if first node is equal to second node.
         * @throws IllegalArgumentException if either of given nodes are null
         */
        public int compare(Node<String> node1, Node<String> node2) {
            if (node1 == null || node2 == null) throw new IllegalArgumentException();
            return node1.getParentName().compareTo(node2.getParentName());
        }
    }
    /**
     * Implements a Comparator to compare two edges
     */
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
}
