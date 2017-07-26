import {ClientStorage} from '../utils/ClientStorage'

export type GroupTreeNode = {
    id: number,
    name: string,
    parentId: number,
    level: number,
    entriesCount: number
    children: number[]
    active?: boolean
    expanded?: boolean
}

export type GroupTree = {
    nodeById: { [nodeId: number]: GroupTreeNode }
    nodeIds: number[]
    filterText: string
    contextMenuNodeId: number
}

export type AppStore = {
    groupTree: GroupTree,
    activeModalId: string
}

export const GROUP_TREE_ROOT_NODE_ID = 0

export const defaultStoreInstance: AppStore = {
    groupTree: {
        nodeById: {},
        nodeIds: [],
        filterText: ClientStorage.getGroupFilterText(),
        contextMenuNodeId: -1
    },
    activeModalId: null
}
