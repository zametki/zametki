import * as React from "react";
import * as ReactDOM from "react-dom";
import * as ReactRedux from "react-redux";
import {appStore, GroupTree, GroupTreeNode} from "./Store";


type OwnProps = {
    nodeId: string
}

type ConnectedState = {
    node: GroupTreeNode
}

type ConnectedDispatch = {
    // updateTree: (node: GroupTreeNode) => void
}

/** Maps Store state to component props */
const mapStateToProps = (groupTree: GroupTree, ownProps: OwnProps): ConnectedState => {
    return {
        node: groupTree && groupTree.nodeById ? groupTree.nodeById[ownProps.nodeId] : null
    }
}

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
        if (!props.nodeId) {
            throw new Error("no node id");
        }
    }

    render() {
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
                <GTV nodeId={appStore.getState().groupTree.rootNodeId}/>
            </ReactRedux.Provider>,
            document.getElementById(id)
        );
    }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GTV: React.ComponentClass<OwnProps> = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeView) as React.ComponentClass<OwnProps>;
