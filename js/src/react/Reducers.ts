import * as Redux from 'redux'
import {AppStore, GroupTree} from './Store'
import {Action, ActionType, isAction, ToggleTreeNodeActionPayload, UpdateTreeActionPayload} from './Actions'

/** Group Tree reducer */
function groupTree(state: GroupTree = {nodeById: {}, nodeIds: []}, action: Action<any>): GroupTree {
  if (isAction<UpdateTreeActionPayload>(action, ActionType.UpdateTree)) {
    const nodeById = {}
    const nodeIds = []
    action.payload.nodes.map(n => {
      const old = state.nodeById[n.id]
      nodeById[n.id] = n
      nodeById[n.id].expanded = old && old.expanded
      nodeIds.push(n.id)
    })
    return {nodeById, nodeIds}
  } else if (isAction<ToggleTreeNodeActionPayload>(action, ActionType.ToggleTreeNode)) {
    const nodeById = state.nodeById
    const node = nodeById[action.payload.nodeId]
    if (node) {
      node.expanded = action.payload.expanded
    }
    return {...state, nodeById} as GroupTree
  }
  return state
}

export const AppReducers = Redux.combineReducers<AppStore>({groupTree})
