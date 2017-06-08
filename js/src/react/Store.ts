import * as Redux from "redux";
import {AppReducers} from "./Reducers";

export type GroupTreeNode = {
    id: string,
    name: string,
    children?: Array<GroupTreeNode>
}

export type GroupTree = {
    rootNodeId: string,
    nodeById: { [nodeId: string]: GroupTreeNode }
}

export type AppStore = {
    groupTree: GroupTree
}


export const appStore: Redux.Store<AppStore> = Redux.createStore(
    AppReducers,
    {
        groupTree: {
            rootNodeId: null,
            nodeById: {}
        }
    } as AppStore,
    window["__REDUX_DEVTOOLS_EXTENSION__"] && window["__REDUX_DEVTOOLS_EXTENSION__"]()
    // Redux.applyMiddleware(thunk),
)
