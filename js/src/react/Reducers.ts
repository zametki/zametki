import * as Redux from 'redux'
import asyncDispatchMiddleware from './AsyncDispatchMiddleware'

import {AppStore, GROUP_TREE_INVALID_ID, storeInitialState} from './Store'
import {
    ActionType,
    ChangeGroupPayload,
    CreateGroupPayload,
    DeleteGroupPayload,
    MoveGroupPayload,
    newChangeGroupAction,
    newStartUpdateNotesListAction,
    newUpdateGroupTreeAction,
    newUpdateNotesListAction,
    RenameGroupPayload,
    ShowCreateGroupDialogPayload,
    ShowMoveGroupDialogPayload,
    ShowRenameGroupDialogPayload,
    StartUpdateNotesListPayload,
    ToggleGroupTreeNodeMenuPayload,
    ToggleGroupTreeNodePayload,
    UpdateGroupTreeFilterPayload,
    UpdateGroupTreePayload,
    UpdateNotesListPayload,
    ZAction
} from './Actions'
import {ClientStorage} from '../utils/ClientStorage'
import {CREATE_GROUP_MODAL_ID} from './components/overlays/CreateGroupModalOverlay'
import {RENAME_GROUP_MODAL_ID} from "./components/overlays/RenameGroupModalOverlay"
import {MOVE_GROUP_MODAL_ID} from './components/overlays/MoveGroupModalOverlay'
import {GROUP_NAVIGATOR_MODAL_ID} from './components/overlays/GroupNavigatorModalOverlay'

const REDUCERS = {}
REDUCERS[ActionType.UpdateGroupTree] = updateGroupTree
REDUCERS[ActionType.ToggleGroupTreeNode] = toggleGroupTreeNode
REDUCERS[ActionType.ChangeGroup] = changeGroup
REDUCERS[ActionType.UpdateGroupTreeFilter] = updateGroupTreeFilter
REDUCERS[ActionType.ToggleGroupTreeNodeMenu] = toggleGroupTreeNodeMenu
REDUCERS[ActionType.ShowCreateGroupDialog] = showCreateGroupDialog
REDUCERS[ActionType.CreateGroup] = createGroup
REDUCERS[ActionType.ShowRenameGroupDialog] = showRenameGroupDialog
REDUCERS[ActionType.RenameGroup] = renameGroup
REDUCERS[ActionType.ShowMoveGroupDialog] = showMoveGroupDialog
REDUCERS[ActionType.MoveGroup] = moveGroup
REDUCERS[ActionType.HideModal] = hideModal
REDUCERS[ActionType.ShowGroupNavigator] = showGroupNavigator
REDUCERS[ActionType.DeleteGroup] = deleteGroup
REDUCERS[ActionType.StartUpdateNotesList] = startUpdateNotesList
REDUCERS[ActionType.UpdateNotesList] = updateNotesList

type AsyncDispatch = (newAction: ZAction<any>) => void

function allReducers(state: AppStore = storeInitialState, action: ZAction<any>): AppStore {
    if (!action || !action.type || !action.payload) {
        return state
    }
    let asyncDispatch = (newAction: ZAction<any>) => {
        action.asyncDispatch && action.asyncDispatch(newAction)
    }
    const reducer = REDUCERS[action.type]
    return reducer ? reducer(state, action.payload, asyncDispatch) : state
}

function updateGroupTree(state: AppStore, payload: UpdateGroupTreePayload): AppStore {
    const nodeById = {}
    const nodeIds = []
    payload.nodes.map(n => {
        const old = state.groupTree.nodeById[n.id]
        n.expanded = !!old ? old.expanded : ClientStorage.isNodeExpanded(n.id)
        nodeById[n.id] = n
        nodeIds.push(n.id)
    })
    // noinspection TypeScriptValidateTypes
    return {...state, groupTree: {...state.groupTree, nodeById, nodeIds}}
}

function toggleGroupTreeNode(state: AppStore, payload: ToggleGroupTreeNodePayload): AppStore {
    const nodeById = state.groupTree.nodeById
    const n = nodeById[payload.groupId]
    if (n) {
        n.expanded = payload.expanded
        ClientStorage.setNodeExpanded(n.id, n.expanded)
    }
    // noinspection TypeScriptValidateTypes
    return {...state, groupTree: {...state.groupTree, nodeById}}
}

function changeGroup(state: AppStore, payload: ChangeGroupPayload, asyncDispatch: AsyncDispatch): AppStore {
    const nodeById = state.groupTree.nodeById
    const n = nodeById[payload.groupId]
    if (n) {
        // expand all parent nodes to make new node visible.
        let parentNode = nodeById[n.parentId]
        while (parentNode) {
            if (!parentNode.expanded) {
                parentNode.expanded = true
                ClientStorage.setNodeExpanded(parentNode.id, true)
            }
            parentNode = nodeById[parentNode.parentId]
        }
    } else if (payload.groupId !== GROUP_TREE_INVALID_ID) {
        return state
    }

    ClientStorage.setLastUsedGroupId(payload.groupId)
    asyncDispatch(newStartUpdateNotesListAction(payload.groupId))

    // noinspection TypeScriptValidateTypes
    return {...state, activeGroupId: payload.groupId}
}

function updateGroupTreeFilter(state: AppStore, payload: UpdateGroupTreeFilterPayload): AppStore {
    ClientStorage.setGroupFilterText(payload.filterText)
    // noinspection TypeScriptValidateTypes
    return {...state, groupTree: {...state.groupTree, filterText: payload.filterText}}
}

function toggleGroupTreeNodeMenu(state: AppStore, payload: ToggleGroupTreeNodeMenuPayload): AppStore {
    if (payload.active) {
        // noinspection TypeScriptValidateTypes
        return {...state, activeGroupId: payload.groupId, groupTree: {...state.groupTree, contextMenuIsActive: true}}
    }
    if (state.activeGroupId === payload.groupId) {
        // noinspection TypeScriptValidateTypes
        return {...state, groupTree: {...state.groupTree, contextMenuIsActive: false}}
    }
    return state
}

function showCreateGroupDialog(state: AppStore, payload: ShowCreateGroupDialogPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: CREATE_GROUP_MODAL_ID, activeGroupId: payload.parentGroupId}
}

function createGroup(state: AppStore, payload: CreateGroupPayload): AppStore {
    const xhr = new XMLHttpRequest()
    xhr.open('GET', `/ajax/create-group/${payload.parentGroupId}/${payload.name}`, true)
    xhr.responseType = 'json'
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            appStore.dispatch(newUpdateGroupTreeAction(xhr.response.groups))
        }
    }
    xhr.send()
    return state
}

function showRenameGroupDialog(state: AppStore, payload: ShowRenameGroupDialogPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: RENAME_GROUP_MODAL_ID, activeGroupId: payload.groupId}
}

function renameGroup(state: AppStore, payload: RenameGroupPayload): AppStore {
    const xhr = new XMLHttpRequest()
    xhr.open('GET', `/ajax/rename-group/${payload.groupId}/${payload.name}`, true)
    xhr.responseType = 'json'
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            appStore.dispatch(newUpdateGroupTreeAction(xhr.response.groups))
        }
    }
    xhr.send()
    return state
}

function showMoveGroupDialog(state: AppStore, payload: ShowMoveGroupDialogPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: MOVE_GROUP_MODAL_ID, activeGroupId: payload.groupId}
}

function moveGroup(state: AppStore, payload: MoveGroupPayload): AppStore {
    const xhr = new XMLHttpRequest()
    xhr.open('GET', `/ajax/move-group/${payload.groupId}/${payload.parentId}`, true)
    xhr.responseType = 'json'
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            appStore.dispatch(newUpdateGroupTreeAction(xhr.response.groups))
        }
    }
    xhr.send()
    return state
}


function hideModal(state: AppStore): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: null}
}

function showGroupNavigator(state: AppStore): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: GROUP_NAVIGATOR_MODAL_ID}
}

function deleteGroup(state: AppStore, payload: DeleteGroupPayload): AppStore {
    const group = state.groupTree.nodeById[payload.groupId]
    if (!group) {
        return
    }
    const newActiveGroupId = group.parentId

    const xhr = new XMLHttpRequest()
    xhr.open('GET', `/ajax/delete-group/${payload.groupId}`, true)
    xhr.responseType = 'json'
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            appStore.dispatch(newChangeGroupAction(newActiveGroupId))
            appStore.dispatch(newUpdateGroupTreeAction(xhr.response.groups))
        }
    }
    xhr.send()
    return state
}

function startUpdateNotesList(state: AppStore, payload: StartUpdateNotesListPayload): AppStore {
    const xhr = new XMLHttpRequest()
    xhr.open('GET', `/ajax/notes/${payload.groupId}`, true)
    xhr.responseType = 'json'
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            appStore.dispatch(newUpdateNotesListAction(xhr.response.notes))
        }
    }
    xhr.send()
    return state
}

function updateNotesList(state: AppStore, payload: UpdateNotesListPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, notes: payload.notes}
}


const composeWithEnhancers = window['__REDUX_DEVTOOLS_EXTENSION_COMPOSE__'] || Redux.compose
const createStoreWithMiddleware = composeWithEnhancers(Redux.applyMiddleware(asyncDispatchMiddleware))(Redux.createStore)

// todo: do not export, use listeners!!
export const appStore: Redux.Store<AppStore> = window['appStore'] = createStoreWithMiddleware(allReducers, storeInitialState)

