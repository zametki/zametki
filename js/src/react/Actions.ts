import {GroupTreeNode} from './Store'
import {Action} from 'redux'

export interface ZAction<T> extends Action {
    payload: T
}

export class ActionType {
    static UpdateGroupTree = 'UpdateTreeAction'
    static ToggleGroupTreeNode = 'ToggleGroupTreeNode'
    static GroupTreeFilterUpdate = 'GroupTreeFilterUpdate'
    static ToggleGroupTreeNodeMenu = 'ToggleGroupTreeNodeMenu'
    static ActivateGroup = 'ActivateGroup'
    static CreateGroup = 'CreateGroup'
    static RenameGroup = 'RenameGroup'
    static MoveGroup = 'MoveGroup'
    static HideModal = 'HideModal'
    static ShowGroupNavigator = 'ShowGroupNavigator'
    static DeleteGroup = 'DeleteGroup'
}

export type UpdateGroupTreeActionPayload = { nodes: GroupTreeNode[]; }
export type ToggleGroupTreeNodeActionPayload = { nodeId: number, expanded: boolean; }
export type GroupTreeFilterUpdatePayload = { filterText: string }
export type ToggleGroupTreeNodeMenuPayload = { nodeId: number, active: boolean }
export type CreateGroupPayload = { parentNodeId: number }
export type ActivateGroupActionPayload = { nodeId: number }
export type RenameGroupPayload = { nodeId: number }
export type MoveGroupPayload = { nodeId: number }
export type HideModalPayload = {}
export type ShowGroupNavigatorPayload = {}
export type DeleteGroupPayload = { nodeId: number }

export const newUpdateGroupTreeAction = (nodes: GroupTreeNode[]): ZAction<UpdateGroupTreeActionPayload> => ({
    type: ActionType.UpdateGroupTree,
    payload: {nodes}
})

export const newToggleGroupTreeNodeAction = (nodeId: number, expanded: boolean): ZAction<ToggleGroupTreeNodeActionPayload> => ({
    type: ActionType.ToggleGroupTreeNode,
    payload: {nodeId, expanded}
})

export const newActivateGroupAction = (nodeId: number): ZAction<ActivateGroupActionPayload> => ({
    type: ActionType.ActivateGroup,
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

export const newShowMoveGroupAction = (nodeId: number): ZAction<MoveGroupPayload> => ({
    type: ActionType.MoveGroup,
    payload: {nodeId}
})

export const newHideModalAction = (): ZAction<HideModalPayload> => ({
    type: ActionType.HideModal,
    payload: {}
})

export const newShowGroupNavigatorAction = (): ZAction<ShowGroupNavigatorPayload> => ({
    type: ActionType.ShowGroupNavigator,
    payload: {}
})


export const newDeleteGroupAction = (nodeId: number): ZAction<ShowGroupNavigatorPayload> => ({
    type: ActionType.DeleteGroup,
    payload: {nodeId}
})
