import * as Redux from "redux";
import {AppStore, GroupTree, GroupTreeNode} from "./Store";
import {Action, ActionType_UpdateTree, isAction, UpdateTreeActionPayload} from "./Actions";

function flattenTree(node: GroupTreeNode, nodeById: { [nodeId: string]: GroupTreeNode }) {
    nodeById[node.id] = node;
    if (node.children) {
        for (let i = 0, n = node.children.length; i < node.children.length; i++) {
            flattenTree(node.children[i], nodeById);
        }
    }
    return nodeById;
}

//todo: initial state
function handleGroupActions(state: GroupTree = null, action: Action<any>): GroupTree {
    if (isAction<UpdateTreeActionPayload>(action, ActionType_UpdateTree)) {
        return {
            rootNodeId: action.payload.rootNode.id,
            nodeById: flattenTree(action.payload.rootNode, {})
        }
    }
    return state;
}

export const AppReducers = Redux.combineReducers<AppStore>({
    groupTree: handleGroupActions
})

