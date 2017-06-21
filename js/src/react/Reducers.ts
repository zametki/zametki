import * as Redux from 'redux'
import {AppStore, GroupTree} from './Store'
import {Action, ActionType, isAction, ToggleTreeNodeActionPayload, UpdateTreeActionPayload} from './Actions'
import {ClientStorage} from '../utils/ClientStorage'

/** Group Tree reducer */
function groupTree(state: GroupTree = {nodeById: {}, nodeIds: []}, action: Action<any>): GroupTree {
  if (isAction<UpdateTreeActionPayload>(action, ActionType.UpdateTree)) {
    const nodeById = {}
    const nodeIds = []
    action.payload.nodes.map(n => {
      const old = state.nodeById[n.id]
      n.expanded = !!old ? old.expanded : ClientStorage.isNodeExpanded(n.id)
      nodeById[n.id] = n
      nodeIds.push(n.id)
    })
    return {nodeById, nodeIds}
  } else if (isAction<ToggleTreeNodeActionPayload>(action, ActionType.ToggleTreeNode)) {
    const nodeById = state.nodeById
    const n = nodeById[action.payload.nodeId]
    if (n) {
      n.expanded = action.payload.expanded
      ClientStorage.setNodeExpanded(n.id, n.expanded)
    }
    return {...state, nodeById} as GroupTree
  }
  return state
}

export const AppReducers = Redux.combineReducers<AppStore>({groupTree})
