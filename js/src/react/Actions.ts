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
    static ToggleGroupTreeNodeRename = 'ToggleGroupTreeNodeRename'
    static ToggleGroupTreeNodeMenu = 'ToggleGroupTreeNodeMenu'
    static ShowCreateGroup = 'ShowCreateGroup'
    static HideModal = 'HideModal'
}

export type UpdateGroupTreeActionPayload = { nodes: GroupTreeNode[]; }
export type ToggleGroupTreeNodeActionPayload = { nodeId: number, expanded: boolean; }
export type ActivateGroupTreeNodeActionPayload = { nodeId: number }
export type GroupTreeFilterUpdatePayload = { filterText: string }
export type ToggleGroupTreeNodeRenamePayload = { nodeId: number, active: boolean }
export type ToggleGroupTreeNodeMenuPayload = { nodeId: number, active: boolean }
export type ShowCreateGroupPayload = { parentNodeId: number }
export type HideModalPayload = {}

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

export const createToggleGroupTreeNodeRenameAction = (nodeId: number, active: boolean): ZAction<ToggleGroupTreeNodeRenamePayload> => ({
    type: ActionType.ToggleGroupTreeNodeRename,
    payload: {nodeId, active}
})

export const createToggleGroupTreeNodeMenuAction = (nodeId: number, active: boolean): ZAction<ToggleGroupTreeNodeMenuPayload> => ({
    type: ActionType.ToggleGroupTreeNodeMenu,
    payload: {nodeId, active}
})

export const createShowCreateGroupAction = (parentNodeId: number): ZAction<ShowCreateGroupPayload> => ({
    type: ActionType.ShowCreateGroup,
    payload: {parentNodeId}
})

export const createHideModalAction = (): ZAction<HideModalPayload> => ({
    type: ActionType.HideModal,
    payload: {}
})
