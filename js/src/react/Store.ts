import * as Redux from 'redux'
import {AppReducers} from './Reducers'
import {ClientStorage} from '../utils/ClientStorage'

export type GroupTreeNode = {
  id: number,
  name: string,
  parentId: number,
  level: number,
  entriesCount: number
  children: Array<number>
  active?: boolean
  expanded?: boolean
}

export type GroupTree = {
  nodeById: { [nodeId: number]: GroupTreeNode }
  nodeIds: Array<number>
  filterText: string
}

export type AppStore = {
  groupTree: GroupTree
}

export const GROUP_TREE_ROOT_NODE_ID = 0

export const defaultStoreInstance: AppStore = {
  groupTree: {
    nodeById: {},
    nodeIds: [],
    filterText: ClientStorage.getGroupFilterText()
  }
}

export const appStore: Redux.Store<AppStore> = window['appStore'] = Redux.createStore(
  AppReducers,
  defaultStoreInstance,
  window['__REDUX_DEVTOOLS_EXTENSION__'] && window['__REDUX_DEVTOOLS_EXTENSION__']()
  // Redux.applyMiddleware(thunk),
)
