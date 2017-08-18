import {GroupTreeNode, Note} from './Store'
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
    static UpdateNotesList = 'UpdateNotesList'
}

export type UpdateGroupTreePayload = { nodes: GroupTreeNode[]; }
export type ToggleGroupTreeNodePayload = { groupId: number, expanded: boolean; }
export type GroupTreeFilterUpdatePayload = { filterText: string }
export type ToggleGroupTreeNodeMenuPayload = { groupId: number, active: boolean }
export type CreateGroupPayload = { parentGroupId: number }
export type ActivateGroupPayload = { groupId: number }
export type RenameGroupPayload = { groupId: number }
export type MoveGroupPayload = { groupId: number }
export type HideModalPayload = {}
export type ShowGroupNavigatorPayload = {}
export type DeleteGroupPayload = { groupId: number }
export type UpdateNotesListPayload = { notes: Note[] }

export const newUpdateGroupTreeAction = (nodes: GroupTreeNode[]): ZAction<UpdateGroupTreePayload> => ({
    type: ActionType.UpdateGroupTree,
    payload: {nodes}
})

export const newToggleGroupTreeNodeAction = (groupId: number, expanded: boolean): ZAction<ToggleGroupTreeNodePayload> => ({
    type: ActionType.ToggleGroupTreeNode,
    payload: {groupId, expanded}
})

export const newActivateGroupAction = (groupId: number): ZAction<ActivateGroupPayload> => ({
    type: ActionType.ActivateGroup,
    payload: {groupId}
})

export const newGroupTreeFilterUpdateAction = (filterText: string): ZAction<GroupTreeFilterUpdatePayload> => ({
    type: ActionType.GroupTreeFilterUpdate,
    payload: {filterText}
})

export const newToggleGroupTreeNodeMenuAction = (groupId: number, active: boolean): ZAction<ToggleGroupTreeNodeMenuPayload> => ({
    type: ActionType.ToggleGroupTreeNodeMenu,
    payload: {groupId, active}
})

export const newShowCreateGroupAction = (parentGroupId: number): ZAction<CreateGroupPayload> => ({
    type: ActionType.CreateGroup,
    payload: {parentGroupId}
})

export const newShowRenameGroupAction = (groupId: number): ZAction<RenameGroupPayload> => ({
    type: ActionType.RenameGroup,
    payload: {groupId}
})

export const newShowMoveGroupAction = (groupId: number): ZAction<MoveGroupPayload> => ({
    type: ActionType.MoveGroup,
    payload: {groupId}
})

export const newHideModalAction = (): ZAction<HideModalPayload> => ({
    type: ActionType.HideModal,
    payload: {}
})

export const newShowGroupNavigatorAction = (): ZAction<ShowGroupNavigatorPayload> => ({
    type: ActionType.ShowGroupNavigator,
    payload: {}
})

export const newDeleteGroupAction = (groupId: number): ZAction<ShowGroupNavigatorPayload> => ({
    type: ActionType.DeleteGroup,
    payload: {groupId}
})

export const newUpdateNotesListAction = (notes: Note[]): ZAction<UpdateNotesListPayload> => ({
    type: ActionType.UpdateNotesList,
    payload: {notes}
})
