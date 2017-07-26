import * as Redux from 'redux'
import {AppStore, defaultStoreInstance, GroupTree} from './Store'
import {
    ActionType,
    ActivateGroupTreeNodeActionPayload,
    GroupTreeFilterUpdatePayload,
    HideModalPayload,
    isAction,
    ShowCreateGroupPayload,
    ToggleGroupTreeNodeActionPayload,
    ToggleGroupTreeNodeMenuPayload,
    ToggleGroupTreeNodeRenamePayload,
    UpdateGroupTreeActionPayload
} from './Actions'
import {ClientStorage} from '../utils/ClientStorage'
import {CREATE_GROUP_MODAL_ID} from './components/CreateGroupModalOverlay'

/** Group Tree reducer */
function groupTree(state: GroupTree = defaultStoreInstance.groupTree, action: Redux.Action): GroupTree {
    if (isAction<UpdateGroupTreeActionPayload>(action, ActionType.UpdateGroupTree)) {
        return updateGroupTree(state, action.payload)
    } else if (isAction<ToggleGroupTreeNodeActionPayload>(action, ActionType.ToggleGroupTreeNode)) {
        return toggleGroupTreeNode(state, action.payload)
    } else if (isAction<ActivateGroupTreeNodeActionPayload>(action, ActionType.ActivateGroupTreeNode)) {
        return activateGroupTreeNode(state, action.payload)
    } else if (isAction<GroupTreeFilterUpdatePayload>(action, ActionType.GroupTreeFilterUpdate)) {
        return updateGroupTreeFilter(state, action.payload)
    } else if (isAction<ToggleGroupTreeNodeRenamePayload>(action, ActionType.ToggleGroupTreeNodeRename)) {
        return toggleGroupTreeNodeRename(state, action.payload)
    } else if (isAction<ToggleGroupTreeNodeMenuPayload>(action, ActionType.ToggleGroupTreeNodeMenu)) {
        return toggleGroupTreeNodeMenu(state, action.payload)
    }
    return state
}

/** Active modal reducers */
function activeModalId(state: string = defaultStoreInstance.activeModalId, action: Redux.Action): string {
    if (isAction<ShowCreateGroupPayload>(action, ActionType.ShowCreateGroup)) {
        return handleShowCreateGroup(state, action.payload)
    } else if (isAction<HideModalPayload>(action, ActionType.HideModal)) {
        return handleHideModal(state, action.payload)
    }

    return state
}

export const AppReducers = Redux.combineReducers<AppStore>({groupTree, activeModalId})

function updateGroupTree(state: GroupTree, payload: UpdateGroupTreeActionPayload): GroupTree {
    const nodeById = {}
    const nodeIds = []
    payload.nodes.map(n => {
        const old = state.nodeById[n.id]
        n.expanded = !!old ? old.expanded : ClientStorage.isNodeExpanded(n.id)
        nodeById[n.id] = n
        nodeIds.push(n.id)
    })
    return {...state, nodeById, nodeIds} as GroupTree
}

function toggleGroupTreeNode(state: GroupTree, payload: ToggleGroupTreeNodeActionPayload): GroupTree {
    const nodeById = state.nodeById
    const n = nodeById[payload.nodeId]
    if (n) {
        n.expanded = payload.expanded
        ClientStorage.setNodeExpanded(n.id, n.expanded)
    }
    return {...state, nodeById} as GroupTree
}

function activateGroupTreeNode(state: GroupTree, payload: ActivateGroupTreeNodeActionPayload): GroupTree {
    const nodeById = state.nodeById

    // deactivate all other nodes.
    for (const id of state.nodeIds) {
        nodeById[id].active = false
    }

    // activate new node.
    const n = nodeById[payload.nodeId]
    if (!n) {
        return
    }
    n.active = true

    // expand all parent nodes to make new node visible.
    let parentNode = nodeById[n.parentId]
    while (parentNode) {
        if (!parentNode.expanded) {
            parentNode.expanded = true
            ClientStorage.setNodeExpanded(parentNode.id, true)
        }
        parentNode = nodeById[parentNode.parentId]
    }
    return {...state, nodeById} as GroupTree
}

function updateGroupTreeFilter(state: GroupTree, payload: GroupTreeFilterUpdatePayload): GroupTree {
    ClientStorage.setGroupFilterText(payload.filterText)
    return {...state, filterText: payload.filterText} as GroupTree
}

function toggleGroupTreeNodeRename(state: GroupTree, payload: ToggleGroupTreeNodeRenamePayload) {
    return state
}

function toggleGroupTreeNodeMenu(state: GroupTree, payload: ToggleGroupTreeNodeMenuPayload) {
    if (payload.active) {
        return {...state, contextMenuNodeId: payload.nodeId}
    }
    if (state.contextMenuNodeId === payload.nodeId) {
        return {...state, contextMenuNodeId: -1}
    }
    return state
}

function handleShowCreateGroup(state: string, payload: ShowCreateGroupPayload): string {
    return CREATE_GROUP_MODAL_ID
}

function handleHideModal(state: string, payload: HideModalPayload): string {
    return null
}

// todo: do not export, use listeners!!
//noinspection TsLint
export const appStore: Redux.Store<AppStore> = window['appStore'] = Redux.createStore(
    AppReducers,
    defaultStoreInstance,
    window['__REDUX_DEVTOOLS_EXTENSION__'] && window['__REDUX_DEVTOOLS_EXTENSION__']()
    // Redux.applyMiddleware(thunk),
)
