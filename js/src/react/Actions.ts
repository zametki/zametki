import * as Redux from "redux";
import {GroupTreeNode} from "./Store";

export interface Action<T> extends Redux.Action {
    payload: T
}

export function isAction<T>(action: Action<any>, actionName: string): action is Action<T> {
    return action && action.type && action.type == actionName;
}

export const ActionType_UpdateTree = "UpdateTreeAction";
export type UpdateTreeActionPayload = { nodes: Array<GroupTreeNode>; }

export const ActionType_ToggleTreeNode = "ToggleTreeNode";
export type ToggleTreeNodeActionPayload = { nodeId: number, expanded: boolean; }

export const createUpdateTreeAction = (nodes: Array<GroupTreeNode>): Action<UpdateTreeActionPayload> => ({type: ActionType_UpdateTree, payload: {nodes}});
export const createToggleTreeNodeAction = (nodeId: number, expanded: boolean): Action<ToggleTreeNodeActionPayload> => ({type: ActionType_ToggleTreeNode, payload: {nodeId, expanded}});
