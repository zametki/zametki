import * as Redux from "redux";
import {AppStore, GroupTree, GroupTreeNode} from "./Store";
import {Action, ActionType_ToggleTreeNode, ActionType_UpdateTree, isAction, ToggleTreeNodeActionPayload, UpdateTreeActionPayload} from "./Actions";

function flattenTree(node: GroupTreeNode, nodeById: { [nodeId: string]: GroupTreeNode }) {
    nodeById[node.id] = node;
    if (node.children) {
        for (const child of node.children) {
            flattenTree(child, nodeById);
        }
    }
    return nodeById;
}

/** Group Tree reducer */
function groupTree(state: GroupTree = {nodeById: {}}, action: Action<any>): GroupTree {
    if (isAction<UpdateTreeActionPayload>(action, ActionType_UpdateTree)) {
        return {
            nodeById: flattenTree(action.payload.rootNode, {})
        }
    } else if (isAction<ToggleTreeNodeActionPayload>(action, ActionType_ToggleTreeNode)) {
        console.log("expanded: " + action.payload.nodeId + "->" + action.payload.expanded);
        const nodeById = state.nodeById;
        const node = nodeById[action.payload.nodeId];
        if (node) {
            node.expanded = action.payload.expanded;
        }
        return {nodeById: nodeById}
    }
    return state;
}

export const AppReducers = Redux.combineReducers<AppStore>({groupTree})

