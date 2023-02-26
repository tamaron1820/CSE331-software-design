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

import React, {Component} from 'react';

interface EdgeListProps {
    onChange(edges: Edge[]): void;  // called when a new edge list is ready
                                 // TODO: once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}

export interface Edge {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
    color: string;
}
interface EdgeListState {
    val: string;
}
/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            val: "",
        }
    }

    TextClear() {
        this.setState({
            val: "",
        })
        this.props.onChange([]);
    }

    TextChange() {
        let edges = this.checkValidEdge(this.state.val);
        this.props.onChange(edges);
    }

    checkValidEdge(str: string) {
        let edgeList: Edge[] = [];
        if ( str === "") alert("Null string");
        let line = str.split("\n");

        for( let i =0; i<line.length; i++) {
            let value = line[i].trim().split(" ");
            if( value.length <5) alert("Invalid argument");
            let numCheck : boolean = true;
            for (let j = 0; j < value.length ; j++) {
                if (parseInt(value[j]) < 0 || parseInt(value[j]) > 4000){
                    alert("Invalid number");
                    numCheck = false;
                }
            }
            if(numCheck) {
                let edge: Edge = {
                    x1:parseInt(value[0]),
                    y1:parseInt(value[1]),
                    x2:parseInt(value[2]),
                    y2:parseInt(value[3]),
                    color:value[4]

                }
                edgeList.push(edge);
            }
        }
        return edgeList;
    }
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(event) => {this.setState({
                        val: event.target.value,
                    })}}
                    value={this.state.val}
                /> <br/>
                <button onClick={() => {this.TextChange()}}>Draw</button>
                <button onClick={() => {this.TextClear()}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
