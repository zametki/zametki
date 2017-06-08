import {appStore, GroupTreeNode} from "../react/Store";
import {GroupTreeView} from "../react/GroupTreeView";
import {createUpdateTreeAction} from "../react/Actions";

function renderGroupTree(id: string): void {
    console.log(`renderGroupTree: ${id}`);
    GroupTreeView.wrap(id);
}

function onGroupTreeChanged(rootNode: GroupTreeNode) {
    console.log(`onGroupTreeChanged: ${rootNode}`);

    const updateTreeAction = createUpdateTreeAction(rootNode);
    //noinspection TypeScriptValidateTypes
    appStore.dispatch(updateTreeAction);
}

export default {
    onGroupTreeChanged: onGroupTreeChanged,
    renderGroupTree: renderGroupTree
}