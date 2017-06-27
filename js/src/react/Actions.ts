import * as Redux from 'redux'
import {GroupTreeNode} from './Store'

export interface Action<T> extends Redux.Action {
  payload: T
}

export function isAction<T>(action: Action<any>, actionName: string): action is Action<T> {
  return action && action.type && action.type === actionName
}

export class ActionType {
  static UpdateGroupTree = 'UpdateTreeAction'
  static ToggleGroupTreeNode = 'ToggleGroupTreeNode'
  static ActivateGroupTreeNode = 'ActivateGroupTreeNode'
  static GroupTreeFilterUpdate = 'GroupTreeFilterUpdate'
}

export type UpdateGroupTreeActionPayload = { nodes: Array<GroupTreeNode>; }
export type ToggleGroupTreeNodeActionPayload = { nodeId: number, expanded: boolean; }
export type ActivateGroupTreeNodeActionPayload = { nodeId: number }
export type GroupTreeFilterUpdatePayload = { filterText: string }

export const createUpdateGroupTreeAction = (nodes: Array<GroupTreeNode>): Action<UpdateGroupTreeActionPayload> => ({type: ActionType.UpdateGroupTree, payload: {nodes}})
export const createToggleGroupTreeNodeAction = (nodeId: number, expanded: boolean): Action<ToggleGroupTreeNodeActionPayload> => ({type: ActionType.ToggleGroupTreeNode, payload: {nodeId, expanded}})
export const createActivateGroupTreeNodeAction = (nodeId: number): Action<ActivateGroupTreeNodeActionPayload> => ({type: ActionType.ActivateGroupTreeNode, payload: {nodeId}})
export const createGroupTreeFilterUpdateAction = (filterText: string): Action<GroupTreeFilterUpdatePayload> => ({type: ActionType.GroupTreeFilterUpdate, payload: {filterText}})
