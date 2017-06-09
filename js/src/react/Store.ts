import * as Redux from "redux";
import {AppReducers} from "./Reducers";

export type GroupTreeNode = {
    id: number,
    name: string,
    parentId: number,
    level: number,
    children?: Array<GroupTreeNode>
}

export type GroupTree = {
    nodeById: { [nodeId: number]: GroupTreeNode }
}

export type AppStore = {
    groupTree: GroupTree
}

export const GROUP_TREE_ROOT_NODE_ID = 0;

export const appStore: Redux.Store<AppStore> = Redux.createStore(
    AppReducers,
    {
        groupTree: {nodeById: {}}
    } as AppStore,
    window["__REDUX_DEVTOOLS_EXTENSION__"] && window["__REDUX_DEVTOOLS_EXTENSION__"]()
    // Redux.applyMiddleware(thunk),
)
