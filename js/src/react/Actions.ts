import {GroupTreeNode} from './Store'
import {Action} from 'redux'

export interface ZAction<T> extends Action {
    payload: T
}

export function isAction<T>(action: Action, actionName: string): action is ZAction<T> {
    return action && action.type && action.type === actionName
}

export class ActionType {
    static UpdateGroupTree = 'UpdateTreeAction'
    static ToggleGroupTreeNode = 'ToggleGroupTreeNode'
    static ActivateGroupTreeNode = 'ActivateGroupTreeNode'
    static GroupTreeFilterUpdate = 'GroupTreeFilterUpdate'
}

export type UpdateGroupTreeActionPayload = { nodes: GroupTreeNode[]; }
export type ToggleGroupTreeNodeActionPayload = { nodeId: number, expanded: boolean; }
export type ActivateGroupTreeNodeActionPayload = { nodeId: number }
export type GroupTreeFilterUpdatePayload = { filterText: string }

export const createUpdateGroupTreeAction = (nodes: GroupTreeNode[]): ZAction<UpdateGroupTreeActionPayload> => ({
    type: ActionType.UpdateGroupTree,
    payload: {nodes}
})
export const createToggleGroupTreeNodeAction = (nodeId: number, expanded: boolean): ZAction<ToggleGroupTreeNodeActionPayload> => ({
    type: ActionType.ToggleGroupTreeNode,
    payload: {nodeId, expanded}
})
export const createActivateGroupTreeNodeAction = (nodeId: number): ZAction<ActivateGroupTreeNodeActionPayload> => ({
    type: ActionType.ActivateGroupTreeNode,
    payload: {nodeId}
})
export const createGroupTreeFilterUpdateAction = (filterText: string): ZAction<GroupTreeFilterUpdatePayload> => ({
    type: ActionType.GroupTreeFilterUpdate,
    payload: {filterText}
})
