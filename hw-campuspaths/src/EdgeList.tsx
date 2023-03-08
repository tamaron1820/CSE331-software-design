import React, {Component} from 'react';

interface EdgeListProps {
    onChange(edges: Edge[] | null): void;  // called when a new edge list is ready
}

interface EdgeListState {
    isBuilding: boolean;
    startbuilding: string;
    endbuilding: string;
    buildingNames: string[];
}

export type Edge = {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
    color: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            isBuilding: false,
            startbuilding: "",
            endbuilding: "",
            buildingNames: [],
        }
        this.getBuildingNames();
    }

    SetStart = (val: string) => {
        this.setState({
            startbuilding: val,
        })
    };

    SetEnd = (val: string) => {
        this.setState({
            endbuilding: val,
        })
    };

    parsePath(data: string) : Edge[] {
        let ret: Edge[] = [];
        let obj = JSON.parse(data);
        let color: string;
        let distance = parseInt(obj["cost"]);
        switch (true) {
            case distance < 500:
                color = "green";
                break;
            case distance < 800:
                color = "red";
                break;
            case distance < 1200:
                color = "yellow";
                break;
            case distance < 2000:
                color = "blue";
                break;
            case distance < 3000:
                color = "orange";
                break;
            default:
                color = "black";
                break;
        }


        obj["path"].forEach((e: any) => {
            let edge: Edge = {
                x1: e['start']['x'],
                y1: e['start']['y'],
                x2: e['end']['x'],
                y2: e['end']['y'],
                color: color,
            }
            ret.push(edge);
        })
        return ret;
    }

    checkEdge(): boolean {
        return !(this.state.startbuilding === "" || this.state.endbuilding === "");
    }

    getBuildingNames(): void {
        if (!this.state.isBuilding){
            fetch('http://localhost:4567/buildingnames').then(response => response.json().then(data => {
                        this.setState({
                                isBuilding: true,
                                buildingNames: data
                        });
            })).catch(e => {
                alert("Server Errpr");
            })
        }
    }

    DropDownMenu = (buildinglabel: string, val: string, setfunction: Function) : JSX.Element => {
        return(
            <div>
                <label>
                    {buildinglabel}
                    <select value={val} onChange={(e) => setfunction(e.target.value)}>
                        <option value="" key="">SELECT BUILDING</option>
                        {this.state.buildingNames.map((buildingName) => (
                            <option value={buildingName} key={buildingName}>{buildingName}</option>
                        ))}
                    </select>
                </label>
            </div>
        )
    }
    onFindPath() : void{
        let boolean = this.checkEdge();
        if (!boolean){
            alert("Building not selected");
            return;
        }

        let url = new URL('http://localhost:4567/FindPath');
        url.searchParams.append("s", this.state.startbuilding);
        url.searchParams.append("e", this.state.endbuilding);
        fetch(url.toString())
            .then(response => response.json()
                .then(data => {
                    let edge = this.parsePath(JSON.stringify(data));
                    this.props.onChange(edge);
                }))
    }
    render() {
        return (
            <div id="edge-list">
                Select Buildings <br/>
                <div>
                    {this.DropDownMenu("Start", this.state.startbuilding, this.SetStart)}
                    {this.DropDownMenu("Destination", this.state.endbuilding, this.SetEnd)}
                </div>
                <button onClick={() => {this.onFindPath();}}>Draw</button>
                <button onClick={() => { this.ResetButtonClear();}}>Reset</button>
            </div>
        );
    }
    ResetButtonClear = () => {
        this.setState({startbuilding: "", endbuilding: "",})
        this.props.onChange(null);
    }
}

export default EdgeList;