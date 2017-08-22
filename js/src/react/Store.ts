import {ClientStorage} from '../utils/ClientStorage'

export const GROUP_TREE_INVALID_ID = -1
export const GROUP_TREE_ROOT_NODE_ID = 0

export type GroupTreeNode = {
    id: number,
    name: string,
    parentId: number,
    level: number,
    entriesCount: number
    children: number[]
    expanded?: boolean
}

export type GroupTree = {
    nodeById: { [nodeId: number]: GroupTreeNode }
    nodeIds: number[]
    filterText: string,
    contextMenuIsActive: boolean
}

export type Note = {
    id: number,
    group: number,
    body: string,
    dateText: string
}

export type AppStore = {
    groupTree: GroupTree,
    activeModalId: string,
    activeGroupId: number,
    notes: Note[]
}

export const storeInitialState: AppStore = {
    //todo: start with a valid group tree!
    groupTree: {
        nodeById: {},
        nodeIds: [],
        //todo: cross client data?
        filterText: ClientStorage.getGroupFilterText(),
        contextMenuIsActive: false
    },
    activeModalId: null,
    //todo: validation?
    activeGroupId: ClientStorage.getLastUsedGroupId(),
    notes: []

}

