/**
 * Interface used by server code to trigger client actions.
 */
import {GroupTreeNode} from '../react/Store'
import {renderGroupTreeView} from '../react/components/GroupTreeView'
import {newActivateGroupTreeNodeAction, newUpdateGroupTreeAction} from '../react/Actions'
import {appStore} from '../react/Reducers'

function renderGroupTree(elementId: string): void {
    renderGroupTreeView(elementId)
}

function dispatchUpdateGroupTreeAction(rootNode: GroupTreeNode[]) {
    //noinspection TypeScriptValidateTypes
    appStore.dispatch(newUpdateGroupTreeAction(rootNode))
}

function dispatchActivateGroupNodeAction(nodeId: number) {
    //noinspection TypeScriptValidateTypes
    appStore.dispatch(newActivateGroupTreeNodeAction(nodeId))
}

export default {
    renderGroupTreeView,
    dispatchUpdateGroupTreeAction,
    dispatchActivateGroupNodeAction
}
