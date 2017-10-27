import {GroupTreeNode, Note} from './Store'
import {Action} from 'redux'

export interface ZAction<T> extends Action {
    payload: T,
    asyncDispatch?: (newAction: ZAction<any>) => void
}

export class ActionType {
    static UpdateGroupTree = 'UpdateTreeAction'
    static ToggleGroupTreeNode = 'ToggleGroupTreeNode'
    static UpdateGroupTreeFilter = 'UpdateGroupTreeFilter'
    static ToggleGroupTreeNodeMenu = 'ToggleGroupTreeNodeMenu'
    static ChangeGroup = 'ChangeGroup'
    static ShowCreateGroupDialog = 'ShowCreateGroupDialog'
    static CreateGroup = 'CreateGroup'
    static ShowRenameGroupDialog = 'ShowRenameGroupDialog'
    static RenameGroup = 'RenameGroup'
    static ShowMoveGroupDialog = 'ShowMoveGroupDialog'
    static MoveGroup = 'MoveGroup'
    static HideModal = 'HideModal'
    static DeleteGroup = 'DeleteGroup'
    static StartUpdateNotesList = 'StartUpdateNotesList'
    static UpdateNotesList = 'UpdateNotesList'
    static DeleteNote = 'DeleteNote'
    static StartEditNote = 'StartEditNote'
    static CancelEditNote = 'CancelEditNote'
    static ShowMoveNoteDialog = 'ShowMoveNoteDialog'
    static MoveNote = 'MoveNote'
    static ToggleNoteMenu = 'ToggleNoteMenu'
    static ToggleAddNote = 'ToggleAddNote'
    static CreateNote = 'CreateNote'
    static UpdateNote = 'UpdateNote'
    static UpdateSidebarState = 'UpdateSidebarState'
}

// TODO: use common payload for groupId or noteId only payloads.
export type UpdateGroupTreePayload = { nodes: GroupTreeNode[]; }
export type ToggleGroupTreeNodePayload = { groupId: number, expanded: boolean; }
export type UpdateGroupTreeFilterPayload = { filterText: string }
export type ToggleGroupTreeNodeMenuPayload = { groupId: number, active: boolean }
export type ShowCreateGroupDialogPayload = { parentGroupId: number }
export type CreateGroupPayload = { parentGroupId: number, name: string }
export type ChangeGroupPayload = { groupId: number }
export type ShowRenameGroupDialogPayload = { groupId: number }
export type RenameGroupPayload = { groupId: number, name: string }
export type ShowMoveGroupDialogPayload = { groupId: number }
export type MoveGroupPayload = { groupId: number, parentId: number }
export type HideModalPayload = {}
export type ShowGroupNavigatorPayload = {}
export type DeleteGroupPayload = { groupId: number }
export type StartUpdateNotesListPayload = { groupId: number }
export type UpdateNotesListPayload = { notes: Note[] }
export type StartEditNotePayload = { noteId: number }
export type CancelEditNotePayload = { noteId: number }
export type DeleteNotePayload = { noteId: number }
export type ShowMoveNoteDialogPayload = { noteId: number }
export type MoveNotePayload = { noteId: number, groupId: number }
export type ToggleNoteMenuPayload = { noteId: number, active: boolean }
export type ToggleAddNotePayload = {}
export type CreateNotePayload = { groupId: number, text: string }
export type UpdateNotePayload = { noteId: number, text: string }
export type UpdateSidebarStatePayload = { open: boolean }

export const newUpdateGroupTreeAction = (nodes: GroupTreeNode[]): ZAction<UpdateGroupTreePayload> => ({
    type: ActionType.UpdateGroupTree,
    payload: {nodes}
})

export const newToggleGroupTreeNodeAction = (groupId: number, expanded: boolean): ZAction<ToggleGroupTreeNodePayload> => ({
    type: ActionType.ToggleGroupTreeNode,
    payload: {groupId, expanded}
})

export const newChangeGroupAction = (groupId: number): ZAction<ChangeGroupPayload> => ({
    type: ActionType.ChangeGroup,
    payload: {groupId}
})

export const newGroupTreeFilterUpdateAction = (filterText: string): ZAction<UpdateGroupTreeFilterPayload> => ({
    type: ActionType.UpdateGroupTreeFilter,
    payload: {filterText}
})

export const newToggleGroupTreeNodeMenuAction = (groupId: number, active: boolean): ZAction<ToggleGroupTreeNodeMenuPayload> => ({
    type: ActionType.ToggleGroupTreeNodeMenu,
    payload: {groupId, active}
})

export const newShowCreateGroupAction = (parentGroupId: number): ZAction<ShowCreateGroupDialogPayload> => ({
    type: ActionType.ShowCreateGroupDialog,
    payload: {parentGroupId}
})

export const newCreateGroupAction = (parentGroupId: number, name: string): ZAction<CreateGroupPayload> => ({
    type: ActionType.CreateGroup,
    payload: {parentGroupId, name}
})

export const newShowRenameGroupAction = (groupId: number): ZAction<ShowRenameGroupDialogPayload> => ({
    type: ActionType.ShowRenameGroupDialog,
    payload: {groupId}
})

export const newRenameGroupAction = (groupId: number, name: string): ZAction<RenameGroupPayload> => ({
    type: ActionType.RenameGroup,
    payload: {groupId, name}
})

export const newShowMoveGroupDialogAction = (groupId: number): ZAction<ShowMoveGroupDialogPayload> => ({
    type: ActionType.ShowMoveGroupDialog,
    payload: {groupId}
})

export const newMoveGroupAction = (groupId: number, parentId: number): ZAction<MoveGroupPayload> => ({
    type: ActionType.MoveGroup,
    payload: {groupId, parentId}
})

export const newHideModalAction = (): ZAction<HideModalPayload> => ({
    type: ActionType.HideModal,
    payload: {}
})

export const newDeleteGroupAction = (groupId: number): ZAction<ShowGroupNavigatorPayload> => ({
    type: ActionType.DeleteGroup,
    payload: {groupId}
})

export const newStartUpdateNotesListAction = (groupId: number): ZAction<StartUpdateNotesListPayload> => ({
    type: ActionType.StartUpdateNotesList,
    payload: {groupId}
})

export const newUpdateNotesListAction = (notes: Note[]): ZAction<UpdateNotesListPayload> => ({
    type: ActionType.UpdateNotesList,
    payload: {notes}
})

export const newDeleteNoteAction = (noteId: number): ZAction<DeleteNotePayload> => ({
    type: ActionType.DeleteNote,
    payload: {noteId}
})

export const newStartEditNoteAction = (noteId: number): ZAction<StartEditNotePayload> => ({
    type: ActionType.StartEditNote,
    payload: {noteId}
})

export const newCancelEditNoteAction = (noteId: number): ZAction<CancelEditNotePayload> => ({
    type: ActionType.CancelEditNote,
    payload: {noteId}
})

export const newShowMoveNoteDialogAction = (noteId: number): ZAction<ShowMoveNoteDialogPayload> => ({
    type: ActionType.ShowMoveNoteDialog,
    payload: {noteId}
})

export const newMoveNoteAction = (noteId: number, groupId: number): ZAction<MoveNotePayload> => ({
    type: ActionType.MoveNote,
    payload: {noteId, groupId}
})

export const newToggleNoteMenuAction = (noteId: number, active: boolean): ZAction<ToggleNoteMenuPayload> => ({
    type: ActionType.ToggleNoteMenu,
    payload: {noteId, active}
})

export const newToggleAddNoteAction = (): ZAction<ToggleAddNotePayload> => ({
    type: ActionType.ToggleAddNote,
    payload: {}
})

export const newCreateNoteAction = (groupId: number, text: string): ZAction<CreateNotePayload> => ({
    type: ActionType.CreateNote,
    payload: {groupId, text}
})

export const newUpdateNoteAction = (noteId: number, text: string): ZAction<UpdateNotePayload> => ({
    type: ActionType.UpdateNote,
    payload: {noteId, text}
})

export const newUpdateSidebarState = (open: boolean): ZAction<UpdateSidebarStatePayload> => ({
    type: ActionType.UpdateSidebarState,
    payload: {open}
})
