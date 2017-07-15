/**
 * Interface used by server code to trigger client actions.
 */
import {appStore, GroupTreeNode} from '../react/Store'
import {renderGroupTreeView} from '../react/components/GroupTreeView'
import {createActivateGroupTreeNodeAction, createUpdateGroupTreeAction} from '../react/Actions'

function renderGroupTree(elementId: string): void {
  renderGroupTreeView(elementId)
}

function dispatchUpdateGroupTreeAction(rootNode: Array<GroupTreeNode>) {
  //noinspection TypeScriptValidateTypes
  appStore.dispatch(createUpdateGroupTreeAction(rootNode))
}

function dispatchActivateGroupNodeAction(nodeId: number) {
  //noinspection TypeScriptValidateTypes
  appStore.dispatch(createActivateGroupTreeNodeAction(nodeId))
}


export default {
  renderGroupTreeView,
  dispatchUpdateGroupTreeAction,
  dispatchActivateGroupNodeAction
}