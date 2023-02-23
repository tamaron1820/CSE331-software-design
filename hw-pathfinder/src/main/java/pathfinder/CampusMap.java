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

package pathfinder;

import graph.*;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.*;
import pathfinder.DijkstraAlgorithm;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * CampusMap class is the graph representation of the UW campus map.
 * Each building is expressed as a node and there is the edge between each node.
 */
public class CampusMap implements ModelAPI {

    // Abstract Function:
    // AF(this) = m which is campus map
    // Campus buildings are expressed as nodes which include in this
    //
    //
    // Representation Invariant for every Graph:
    // graph!=null
    // for all nodes are not null and
    // for all Edges are not null
    // buildingNames != null and buildingLocations != null

    /**
     * A graph which contains all the buildings
     */
    private Graph<Point, Double> graph;
    /**
     * Holds all name of the buildings
     */
    private Map<String, String> buildingNames;

    /**
     * Holds all location name of the buildings
     */

    private Map<String,Point> buildingLocations;

    private static final boolean Debug = false;

    /**
     * Constructs a graph of campus map which includes campus buildings and campus locations
     *
     * @spec.requires campus_buildings.csv is a valid name and not null
     * @spec.requires campus_paths.csv is a valid name and not null
     * @spec.effects creates a new Graph which includes buildings and locations
     */
    public CampusMap() {
        graph = new Graph<>();
        buildingNames = new HashMap<>();
        buildingLocations = new HashMap<>();
        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");

        for (CampusBuilding building: buildings) {
            buildingNames.put(building.getShortName(), building.getLongName());
            Point newPoint = new Point(building.getX(),building.getY());
            buildingLocations.put(building.getShortName(),newPoint);
            graph.addNode(new Node<>(newPoint));
        }

        for (CampusPath path: paths) {
            Point point1 = new Point(path.getX1(),path.getY1());
            graph.addNode(new Node<>(point1));
            Point point2 = new Point(path.getX2(), path.getY2());
            graph.addNode(new Node<>(point2));
            graph.addEdge(new Node<>(point1),path.getDistance(),new Node<>(point2));
        }
        checkRep();
    }

    private void checkRep() {
        assert (graph!=null );
        assert (buildingLocations != null && buildingNames != null);

        if(Debug) {
            for (Node<Point> key : graph.getAllNode()) {
                assert (key != null);
            }

            for (String key : buildingNames.keySet()) {
                assert (key != null);
            }
            for (String key : buildingNames.values()) {
                assert (key != null);
            }
        }
    }
    @Override
    public boolean shortNameExists(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        checkRep();
        if(shortName == null) {
            throw new IllegalArgumentException("Name should not be null");
        }
        return buildingNames.containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        checkRep();
        if(!shortNameExists(shortName)) {
            throw new IllegalArgumentException("Name is not included in campus");
        }
        if( shortName == null) {
            throw new IllegalArgumentException("Name should not be null");
        }
        return buildingNames().get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        return new HashMap<>(buildingNames);
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        if(!(shortNameExists(startShortName) && shortNameExists(endShortName))) {
            throw new IllegalArgumentException("The arguments name are not included in campus");
        }
        if( startShortName == null || endShortName == null ){
            throw new IllegalArgumentException("Names should not be null");
        }
        return DijkstraAlgorithm.findShortestPath(graph,buildingLocations.get(startShortName),buildingLocations.get(endShortName));
    }

}
