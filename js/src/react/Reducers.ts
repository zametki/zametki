import * as Redux from 'redux'
import asyncDispatchMiddleware from './AsyncDispatchMiddleware'

import {AppStore, GROUP_TREE_INVALID_ID, storeInitialState} from './Store'
import {
    ActionType,
    ChangeGroupPayload,
    CreateGroupPayload,
    DeleteGroupPayload,
    GroupTreeFilterUpdatePayload,
    MoveGroupPayload,
    newStartUpdateNotesListAction,
    newUpdateNotesListAction,
    RenameGroupPayload,
    StartUpdateNotesListPayload,
    ToggleGroupTreeNodeMenuPayload,
    ToggleGroupTreeNodePayload,
    UpdateGroupTreePayload,
    UpdateNotesListPayload,
    ZAction
} from './Actions'
import {ClientStorage} from '../utils/ClientStorage'
import {CREATE_GROUP_MODAL_ID} from './components/overlays/CreateGroupModalOverlay'
import {RENAME_GROUP_MODAL_ID} from "./components/overlays/RenameGroupModalOverlay"
import {MOVE_GROUP_MODAL_ID} from './components/overlays/MoveGroupModalOverlay'
import {GROUP_NAVIGATOR_MODAL_ID} from './components/overlays/GroupNavigatorModalOverlay'
import {deleteGroup} from "../utils/Client2Server"

const REDUCERS = {}
REDUCERS[ActionType.UpdateGroupTree] = updateGroupTree
REDUCERS[ActionType.ToggleGroupTreeNode] = toggleGroupTreeNode
REDUCERS[ActionType.ChangeGroup] = handleChangeGroup
REDUCERS[ActionType.GroupTreeFilterUpdate] = updateGroupTreeFilter
REDUCERS[ActionType.ToggleGroupTreeNodeMenu] = toggleGroupTreeNodeMenu
REDUCERS[ActionType.CreateGroup] = handleCreateGroup
REDUCERS[ActionType.RenameGroup] = handleRenameGroup
REDUCERS[ActionType.MoveGroup] = handleMoveGroup
REDUCERS[ActionType.HideModal] = handleHideModal
REDUCERS[ActionType.ShowGroupNavigator] = handleShowGroupNavigator
REDUCERS[ActionType.DeleteGroup] = handleDeleteGroup
REDUCERS[ActionType.StartUpdateNotesList] = handleStartUpdateNotesList
REDUCERS[ActionType.UpdateNotesList] = handleUpdateNotesList

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

function handleChangeGroup(state: AppStore, payload: ChangeGroupPayload, asyncDispatch: AsyncDispatch): AppStore {
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

function updateGroupTreeFilter(state: AppStore, payload: GroupTreeFilterUpdatePayload): AppStore {
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

function handleCreateGroup(state: AppStore, payload: CreateGroupPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: CREATE_GROUP_MODAL_ID, activeGroupId: payload.parentGroupId}
}

function handleRenameGroup(state: AppStore, payload: RenameGroupPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: RENAME_GROUP_MODAL_ID, activeGroupId: payload.groupId}
}

function handleMoveGroup(state: AppStore, payload: MoveGroupPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: MOVE_GROUP_MODAL_ID, activeGroupId: payload.groupId}
}


function handleHideModal(state: AppStore): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: null}
}

function handleShowGroupNavigator(state: AppStore): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: GROUP_NAVIGATOR_MODAL_ID}
}

function handleDeleteGroup(state: AppStore, payload: DeleteGroupPayload): AppStore {
    deleteGroup(payload.groupId)
    return state
}

function handleStartUpdateNotesList(state: AppStore, payload: StartUpdateNotesListPayload): AppStore {
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

function handleUpdateNotesList(state: AppStore, payload: UpdateNotesListPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, notes: payload.notes}
}


const composeWithEnhancers = window['__REDUX_DEVTOOLS_EXTENSION_COMPOSE__'] || Redux.compose
const createStoreWithMiddleware = composeWithEnhancers(Redux.applyMiddleware(asyncDispatchMiddleware))(Redux.createStore)

// todo: do not export, use listeners!!
export const appStore: Redux.Store<AppStore> = window['appStore'] = createStoreWithMiddleware(allReducers, storeInitialState)

