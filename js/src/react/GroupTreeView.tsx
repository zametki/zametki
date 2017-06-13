import * as React from "react";
import * as ReactDOM from "react-dom";
import * as ReactRedux from "react-redux";
import {appStore, AppStore, GROUP_TREE_ROOT_NODE_ID, GroupTreeNode} from "./Store";
import {toggleGroupExpandedState} from "../api/ajax";


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
        this.onToggleExpandedState = this.onToggleExpandedState.bind(this);
    }

    render() {
        if (!this.props.node) {
            return null;
        }
        const children = this.props.node.children;
        const isRoot = this.props.nodeId === GROUP_TREE_ROOT_NODE_ID;
        const renderSubtree = children && children.length > 0 && (isRoot || this.props.node.expanded);
        const subTree = renderSubtree ? children.map(child => <GTV nodeId={child.id}/>) : undefined;
        let nodeComponent;
        const treeNodeClass = "tree-node" + (this.props.node.active ? " tree-node-active" : "");
        const treeJunctionClass = "tree-junction" + (children && children.length > 0 ? (this.props.node.expanded ? " tree-junction-expanded" : " tree-junction-collapsed") : "");
        if (this.props.node.id != 0) {
            nodeComponent = (
                <div className={treeNodeClass}>
                    <div style={{paddingLeft: this.props.node.level * 16}}>
                        <table className="w100">
                            <tr>
                                <td className="tree-junction-td">
                                    <a className={treeJunctionClass} onClick={this.onToggleExpandedState}></a>
                                </td>
                                <td>
                                    <div className="tree-content">
                                        <a className="tree-node-group-link">
                                            <span className="badge zametka-count-badge float-right ml-1">?</span>
                                            <span>{this.props.node.name}</span>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>);
        }
        return (
            <div>
                {nodeComponent}
                {subTree}
            </div>
        );
    }

    static wrap(id: string) {
        ReactDOM.render(
            <ReactRedux.Provider store={appStore}>
                <GTV nodeId={GROUP_TREE_ROOT_NODE_ID}/>
            </ReactRedux.Provider>,
            document.getElementById(id)
        );
    }

    onToggleExpandedState() {
        toggleGroupExpandedState(this.props.nodeId, !this.props.node.expanded);
    }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GTV: React.ComponentClass<OwnProps> = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeView) as React.ComponentClass<OwnProps>;
