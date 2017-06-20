import * as React from "react";
import {GroupTreeNode} from "./Store";

type OwnProps = {
    node: GroupTreeNode
}

export default class GroupTreeCountsBadge extends React.Component<OwnProps, {}> {

    shouldComponentUpdate(nextProps: Readonly<OwnProps>): boolean {
        return nextProps.node.entriesCount != this.props.node.entriesCount;
    }

    render() {
        if (this.props.node.entriesCount <= 0) {
            return null;
        }
        return <div className="badge zametka-count-badge float-right ml-1">{this.props.node.entriesCount}</div>
    }
}
