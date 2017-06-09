import * as React from "react";
import * as ReactDOM from "react-dom";
import * as ReactRedux from "react-redux";
import {appStore, AppStore, GROUP_TREE_ROOT_NODE_ID, GroupTreeNode} from "./Store";


type OwnProps = {
    nodeId: number
}

type ConnectedState = {
    node: GroupTreeNode
}

type ConnectedDispatch = {
    // updateTree: (node: GroupTreeNode) => void
}

/** Maps Store state to component props */
const mapStateToProps = (store: AppStore, ownProps: OwnProps): ConnectedState => {
    return {
        node: store.groupTree.nodeById[ownProps.nodeId]
    }
}

// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch): ConnectedDispatch {
    return {
        // updateTree: groupTreeRoot => dispatch(createUpdateTreeAction(groupTreeRoot))
    }
}

type GroupTreeViewProps = ConnectedState & ConnectedDispatch & OwnProps;

export class GroupTreeView extends React.Component<GroupTreeViewProps, {}> {

    constructor(props: GroupTreeViewProps, context: any) {
        //noinspection TypeScriptValidateTypes
        super(props, context);
        if (typeof props.nodeId === "undefined") {
            throw new Error("no node id");
        }
    }

    render() {
        console.log("render node-id:" + this.props.nodeId + " -> " + this.props.node);
        if (!this.props.node) {
            return null;
        }
        const children = this.props.node.children;
        const subTree = children && children.length > 0 ? children.map(child => <GTV nodeId={child.id}/>) : undefined;
        return (
            <div className="pl10">
                <div>{this.props.node.name}</div>
                {subTree}
            </div>
        )
    }

    static wrap(id: string) {
        ReactDOM.render(
            <ReactRedux.Provider store={appStore}>
                <GTV nodeId={GROUP_TREE_ROOT_NODE_ID}/>
            </ReactRedux.Provider>,
            document.getElementById(id)
        );
    }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GTV: React.ComponentClass<OwnProps> = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeView) as React.ComponentClass<OwnProps>;
