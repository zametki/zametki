import * as Redux from "redux";
import {AppStore, GroupTree} from "./Store";
import {Action, ActionType_ToggleTreeNode, ActionType_UpdateTree, isAction, ToggleTreeNodeActionPayload, UpdateTreeActionPayload} from "./Actions";


/** Group Tree reducer */
function groupTree(state: GroupTree = {nodeById: {}, nodeIds: []}, action: Action<any>): GroupTree {
    if (isAction<UpdateTreeActionPayload>(action, ActionType_UpdateTree)) {
        const nodeById = {};
        const nodeIds = [];
        action.payload.nodes.map(n => {
            nodeById[n.id] = n;
            nodeIds.push(n.id)
        });
        return {nodeById, nodeIds}
    } else if (isAction<ToggleTreeNodeActionPayload>(action, ActionType_ToggleTreeNode)) {
        const nodeById = state.nodeById;
        const node = nodeById[action.payload.nodeId];
        if (node) {
            node.expanded = action.payload.expanded;
        }
        return {...state, nodeById: nodeById}
    }
    return state;
}

export const AppReducers = Redux.combineReducers<AppStore>({groupTree})

