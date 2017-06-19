import * as React from "react";
import * as ReactDOM from "react-dom";
import * as ReactRedux from "react-redux";
import {appStore, AppStore, GROUP_TREE_ROOT_NODE_ID, GroupTreeNode} from "./Store";
import {activateGroup} from "../api/ajax";
import {createToggleTreeNodeAction} from "./Actions";


type OwnProps = {
    node: GroupTreeNode
}

type ConnectedState = {
    node: GroupTreeNode
}

type ConnectedDispatch = {
    expandNode: (nodeId: number, expanded: boolean) => void
}

/** Maps Store state to component props */
const mapStateToProps = (store: AppStore, ownProps: OwnProps): ConnectedState => {
    return {
        node: {... store.groupTree.nodeById[ownProps.node.id]}
    }
}

// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch): ConnectedDispatch {
    return {
        expandNode: (nodeId, expanded) => dispatch(createToggleTreeNodeAction(nodeId, expanded))
    }
}

type GroupTreeViewProps = ConnectedState & ConnectedDispatch & OwnProps;

export class GroupTreeView extends React.Component<GroupTreeViewProps, {}> {

    constructor(props: GroupTreeViewProps, context: any) {
        //noinspection TypeScriptValidateTypes
        super(props, context);
        if (typeof props.node === "undefined") {
            throw new Error("no node id");
        }
        this.onToggleExpandedState = this.onToggleExpandedState.bind(this);
        this.activateGroup = this.activateGroup.bind(this);
    }

    render() {
        if (!this.props.node) {
            console.log("Node not found: " + this.props)
            return null;
        }
        let groupTree = appStore.getState().groupTree;
        const children = this.props.node.children;
        const renderSubtree = children && children.length > 0 && (this.props.node.expanded);
        const subTree = renderSubtree ? children.map(childId => <GTV node={groupTree.nodeById[childId]} key={"node-" + childId}/>) : undefined;
        let nodeComponent;
        const treeNodeClass = "tree-node" + (this.props.node.active ? " tree-node-active" : "");
        const treeJunctionClass = "tree-junction" + (children && children.length > 0 ? (this.props.node.expanded ? " tree-junction-expanded" : " tree-junction-collapsed") : "");
        let countLabel = null;
        if (this.props.node.entriesCount > 0) {
            countLabel = <span className="badge zametka-count-badge float-right ml-1">{this.props.node.entriesCount}</span>;
        }
        if (this.props.node.id != 0) {
            nodeComponent = (
                <div className={treeNodeClass}>
                    <div style={{paddingLeft: this.props.node.level * 16}}>
                        <table className="w100">
                            <tbody>
                            <tr>
                                <td className="tree-junction-td">
                                    <a className={treeJunctionClass} onClick={this.onToggleExpandedState}></a>
                                </td>
                                <td>
                                    <div className="tree-content">
                                        <a className="tree-node-group-link" onClick={this.activateGroup}>
                                            {countLabel}
                                            <span>{this.props.node.name}</span>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
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

    static wrap(elementId: string) {
        let groupTree = appStore.getState().groupTree;
        let nodeById = groupTree.nodeById;
        const treeNodes = groupTree.nodeIds
            .filter(id => nodeById[id].parentId === GROUP_TREE_ROOT_NODE_ID)
            .map(id => <GTV node={nodeById[id]} key={"node-" + id}/>)
        ReactDOM.render(
            <ReactRedux.Provider store={appStore}>
                <div>
                    {treeNodes}
                </div>
            </ReactRedux.Provider>,
            document.getElementById(elementId)
        );
    }

    onToggleExpandedState() {
        this.props.expandNode(this.props.node.id, !this.props.node.expanded);
    }

    activateGroup() {
        activateGroup(this.props.node.id);
    }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GTV: React.ComponentClass<OwnProps> = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeView) as React.ComponentClass<OwnProps>;
