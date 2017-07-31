///<reference path="Actions.ts"/>
import * as Redux from 'redux'
import {AppStore, defaultStoreInstance} from './Store'
import {
    ActionType,
    ActivateGroupTreeNodeActionPayload,
    CreateGroupPayload,
    GroupTreeFilterUpdatePayload,
    RenameGroupPayload,
    ToggleGroupTreeNodeActionPayload,
    ToggleGroupTreeNodeMenuPayload,
    UpdateGroupTreeActionPayload,
    ZAction
} from './Actions'
import {ClientStorage} from '../utils/ClientStorage'
import {CREATE_GROUP_MODAL_ID} from './components/CreateGroupModalOverlay'
import {RENAME_GROUP_MODAL_ID} from "./components/RenameGroupModalOverlay";

const REDUCERS = {}
REDUCERS[ActionType.UpdateGroupTree] = updateGroupTree
REDUCERS[ActionType.ToggleGroupTreeNode] = toggleGroupTreeNode
REDUCERS[ActionType.ActivateGroupTreeNode] = activateGroupTreeNode
REDUCERS[ActionType.GroupTreeFilterUpdate] = updateGroupTreeFilter
REDUCERS[ActionType.ToggleGroupTreeNodeMenu] = toggleGroupTreeNodeMenu
REDUCERS[ActionType.CreateGroup] = handleCreateGroup
REDUCERS[ActionType.RenameGroup] = handleRenameGroup
REDUCERS[ActionType.HideModal] = handleHideModal

function allReducers(state: AppStore = defaultStoreInstance, action: ZAction<any>): AppStore {
    if (!action || !action.type || !action.payload) {
        return state
    }
    const reducer = REDUCERS[action.type]
    return reducer ? reducer(state, action.payload) : state
}

function updateGroupTree(state: AppStore, payload: UpdateGroupTreeActionPayload): AppStore {
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

function toggleGroupTreeNode(state: AppStore, payload: ToggleGroupTreeNodeActionPayload): AppStore {
    const nodeById = state.groupTree.nodeById
    const n = nodeById[payload.nodeId]
    if (n) {
        n.expanded = payload.expanded
        ClientStorage.setNodeExpanded(n.id, n.expanded)
    }
    // noinspection TypeScriptValidateTypes
    return {...state, groupTree: {...state.groupTree, nodeById}}
}

function activateGroupTreeNode(state: AppStore, payload: ActivateGroupTreeNodeActionPayload): AppStore {
    const nodeById = state.groupTree.nodeById

    const n = nodeById[payload.nodeId]
    if (!n) {
        return state
    }

    // expand all parent nodes to make new node visible.
    let parentNode = nodeById[n.parentId]
    while (parentNode) {
        if (!parentNode.expanded) {
            parentNode.expanded = true
            ClientStorage.setNodeExpanded(parentNode.id, true)
        }
        parentNode = nodeById[parentNode.parentId]
    }
    // noinspection TypeScriptValidateTypes
    return {...state, activeGroupId: payload.nodeId}
}

function updateGroupTreeFilter(state: AppStore, payload: GroupTreeFilterUpdatePayload): AppStore {
    ClientStorage.setGroupFilterText(payload.filterText)
    // noinspection TypeScriptValidateTypes
    return {...state, groupTree: {...state.groupTree, filterText: payload.filterText}}
}

function toggleGroupTreeNodeMenu(state: AppStore, payload: ToggleGroupTreeNodeMenuPayload): AppStore {
    if (payload.active) {
        // noinspection TypeScriptValidateTypes
        return {...state, activeGroupId: payload.nodeId, groupTree: {...state.groupTree, contextMenuIsActive: true}}
    }
    if (state.activeGroupId === payload.nodeId) {
        // noinspection TypeScriptValidateTypes
        return {...state, groupTree: {...state.groupTree, contextMenuIsActive: false}}
    }
    return state
}

function handleCreateGroup(state: AppStore, payload: CreateGroupPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: CREATE_GROUP_MODAL_ID, activeGroupId: payload.parentNodeId}
}

function handleRenameGroup(state: AppStore, payload: RenameGroupPayload): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: RENAME_GROUP_MODAL_ID, activeGroupId: payload.nodeId}
}


function handleHideModal(state: AppStore): AppStore {
    // noinspection TypeScriptValidateTypes
    return {...state, activeModalId: null}
}

// todo: do not export, use listeners!!
//noinspection TsLint
export const appStore: Redux.Store<AppStore> = window['appStore'] = Redux.createStore(
    allReducers,
    defaultStoreInstance,
    window['__REDUX_DEVTOOLS_EXTENSION__'] && window['__REDUX_DEVTOOLS_EXTENSION__']()
    // Redux.applyMiddleware(thunk),
)
