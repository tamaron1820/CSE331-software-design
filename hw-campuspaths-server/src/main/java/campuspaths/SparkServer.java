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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import graph.Graph;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import pathfinder.CampusMap;
import pathfinder.datastructures.Point;
import pathfinder.datastructures.Path;


import java.util.Map;


public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.
        CampusMap map = new CampusMap();

        Spark.get("/FindPath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startBuilding = request.queryParams("s");
                String endBuilding = request.queryParams("e");
                if(startBuilding == null || endBuilding == null || !map.shortNameExists(startBuilding) || !map.shortNameExists(endBuilding)) {
                    Spark.halt(400);
                }
                Path<Point> path = null;
                try {
                    path = map.findShortestPath(startBuilding, endBuilding);
                } catch (IllegalArgumentException e) {
                    Spark.halt(400, "start and end do not present");
                }
                Gson gson = new Gson();
                return gson.toJson(map.findShortestPath(startBuilding, endBuilding));
            }
        });

        Spark.get("/buildingnames", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> buildings = map.buildingNames();
                Gson gson = new Gson();
                return gson.toJson(buildings.keySet());
            }
        });

    }

}
