import * as Redux from "redux";
import {GroupTreeNode} from "./Store";

export interface Action<T> extends Redux.Action {
    payload: T
}

export function isAction<T>(action: Action<any>, actionName: string): action is Action<T> {
    return action && action.type && action.type == actionName;
}

export const ActionType_UpdateTree = "UpdateTreeAction";
export type UpdateTreeActionPayload = { rootNode: GroupTreeNode; }

export const createUpdateTreeAction = (rootNode: GroupTreeNode): Action<UpdateTreeActionPayload> => ({type: ActionType_UpdateTree, payload: {rootNode}});
