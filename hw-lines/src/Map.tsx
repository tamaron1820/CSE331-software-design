/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

export interface Edge  {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
    color: string;
}
interface MapProps {
  // TODO: Define the props of this component. You will want to pass down edges
    value: Edge[];
}

interface MapState {}

class Map extends Component<MapProps, MapState> {
    constructor(props: MapProps) {
        super(props);
    }

  render() {
        let list : JSX.Element[] = [];
        for ( let i = 0; i< this.props.value.length; i++) {
            let element = this.props.value[i];
            list.push(<MapLine color={element.color} x1={element.x1} y1={element.y1} x2={element.x2} y2={element.y2}/>);
        }
    return (
      <div id="map">
        <MapContainer
          center={position}
          zoom={15}
          scrollWheelZoom={false}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {
            // TODO: Render map lines here using the MapLine component. E.g.
            // <MapLine key={key1} color="red" x1={1000} y1={1000} x2={2000} y2={2000}/>
            // will draw a red line from the point 1000,1000 to 2000,2000 on the
            // map
              <div>{list}</div>
          }
        </MapContainer>
      </div>
    );
  }
}

export default Map;
