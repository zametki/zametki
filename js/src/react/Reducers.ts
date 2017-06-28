import * as Redux from 'redux'
import {AppStore,  GroupTree} from './Store'
import {Action, ActionType, ActivateGroupTreeNodeActionPayload, GroupTreeFilterUpdatePayload, isAction, ToggleGroupTreeNodeActionPayload, UpdateGroupTreeActionPayload} from './Actions'
import {ClientStorage} from '../utils/ClientStorage'

const defaultStoreInstance: AppStore = {
  groupTree: {
    nodeById: {},
    nodeIds: [],
    filterText: ClientStorage.getGroupFilterText()
  }
}

/** Group Tree reducer */
function groupTree(state: GroupTree = defaultStoreInstance.groupTree, action: Action<any>): GroupTree {
  if (isAction<UpdateGroupTreeActionPayload>(action, ActionType.UpdateGroupTree)) {
    return updateGroupTree(state, action.payload)
  } else if (isAction<ToggleGroupTreeNodeActionPayload>(action, ActionType.ToggleGroupTreeNode)) {
    return toggleGroupTreeNode(state, action.payload)
  } else if (isAction<ActivateGroupTreeNodeActionPayload>(action, ActionType.ActivateGroupTreeNode)) {
    return activateGroupTreeNode(state, action.payload)
  } else if (isAction<GroupTreeFilterUpdatePayload>(action, ActionType.GroupTreeFilterUpdate)) {
    return updateGroupTreeFilter(state, action.payload)
  }
  return state
}

export const AppReducers = Redux.combineReducers<AppStore>({groupTree})

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
  for (let id of state.nodeIds) {
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
  ClientStorage.setGroupFilterText(payload.filterText);
  return {...state, filterText: payload.filterText} as GroupTree
}


