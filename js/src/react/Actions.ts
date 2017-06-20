import * as Redux from 'redux'
import {GroupTreeNode} from './Store'

export interface Action<T> extends Redux.Action {
  payload: T
}

export function isAction<T>(action: Action<any>, actionName: string): action is Action<T> {
  return action && action.type && action.type === actionName
}

export class ActionType {
  static UpdateTree = 'UpdateTreeAction'
  static ToggleTreeNode = 'ToggleTreeNode'
}

export type UpdateTreeActionPayload = { nodes: Array<GroupTreeNode>; }
export type ToggleTreeNodeActionPayload = { nodeId: number, expanded: boolean; }

export const createUpdateTreeAction = (nodes: Array<GroupTreeNode>): Action<UpdateTreeActionPayload> => ({type: ActionType.UpdateTree, payload: {nodes}})
export const createToggleTreeNodeAction = (nodeId: number, expanded: boolean): Action<ToggleTreeNodeActionPayload> => ({type: ActionType.ToggleTreeNode, payload: {nodeId, expanded}})
