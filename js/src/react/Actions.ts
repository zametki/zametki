import {GroupTreeNode} from './Store'
import {Action} from 'redux'

export interface ZAction<T> extends Action {
    payload: T
}

export class ActionType {
    static UpdateGroupTree = 'UpdateTreeAction'
    static ToggleGroupTreeNode = 'ToggleGroupTreeNode'
    static ActivateGroupTreeNode = 'ActivateGroupTreeNode'
    static GroupTreeFilterUpdate = 'GroupTreeFilterUpdate'
    static ToggleGroupTreeNodeMenu = 'ToggleGroupTreeNodeMenu'
    static CreateGroup = 'CreateGroup'
    static RenameGroup = 'RenameGroup'
    static HideModal = 'HideModal'
}

export type UpdateGroupTreeActionPayload = { nodes: GroupTreeNode[]; }
export type ToggleGroupTreeNodeActionPayload = { nodeId: number, expanded: boolean; }
export type ActivateGroupTreeNodeActionPayload = { nodeId: number }
export type GroupTreeFilterUpdatePayload = { filterText: string }
export type ToggleGroupTreeNodeMenuPayload = { nodeId: number, active: boolean }
export type CreateGroupPayload = { parentNodeId: number }
export type RenameGroupPayload = { nodeId: number}
export type HideModalPayload = {}

export const newUpdateGroupTreeAction = (nodes: GroupTreeNode[]): ZAction<UpdateGroupTreeActionPayload> => ({
    type: ActionType.UpdateGroupTree,
    payload: {nodes}
})

export const newToggleGroupTreeNodeAction = (nodeId: number, expanded: boolean): ZAction<ToggleGroupTreeNodeActionPayload> => ({
    type: ActionType.ToggleGroupTreeNode,
    payload: {nodeId, expanded}
})

export const newActivateGroupTreeNodeAction = (nodeId: number): ZAction<ActivateGroupTreeNodeActionPayload> => ({
    type: ActionType.ActivateGroupTreeNode,
    payload: {nodeId}
})

export const newGroupTreeFilterUpdateAction = (filterText: string): ZAction<GroupTreeFilterUpdatePayload> => ({
    type: ActionType.GroupTreeFilterUpdate,
    payload: {filterText}
})

export const newToggleGroupTreeNodeMenuAction = (nodeId: number, active: boolean): ZAction<ToggleGroupTreeNodeMenuPayload> => ({
    type: ActionType.ToggleGroupTreeNodeMenu,
    payload: {nodeId, active}
})

export const newShowCreateGroupAction = (parentNodeId: number): ZAction<CreateGroupPayload> => ({
    type: ActionType.CreateGroup,
    payload: {parentNodeId}
})

export const newShowRenameGroupAction = (nodeId: number): ZAction<RenameGroupPayload> => ({
    type: ActionType.RenameGroup,
    payload: {nodeId}
})

export const newHideModalAction = (): ZAction<HideModalPayload> => ({
    type: ActionType.HideModal,
    payload: {}
})
