import {createIncrementCounterAction, createUpdateTreeAction, GroupTree, Store, storeInstance} from "../react/GroupTree";
import GroupTreeNode = Store.GroupTreeNode;

function renderGroupTree(id: string): void {
    GroupTree.wrap(id);
}

function onGroupTreeChanged(groupTreeRoot: Store.GroupTreeNode) {
    console.log(groupTreeRoot);

    const updateTreeAction = createUpdateTreeAction(groupTreeRoot);
    //noinspection TypeScriptValidateTypes
    storeInstance.dispatch(updateTreeAction);

    const incrementCounterAction = createIncrementCounterAction(1);
    //noinspection TypeScriptValidateTypes
    storeInstance.dispatch(incrementCounterAction);
}

export default {
    onGroupTreeChanged: onGroupTreeChanged,
    renderGroupTree: renderGroupTree
}