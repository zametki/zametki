/**
 * Interface used by server code to trigger client actions.
 */
import {appStore, GroupTreeNode} from '../react/Store'
import {GroupTreeView} from '../react/components/GroupTreeView'
import {createUpdateTreeAction} from '../react/Actions'

function renderGroupTree(elementId: string): void {
  GroupTreeView.wrap(elementId)
}

function onGroupTreeChanged(rootNode: Array<GroupTreeNode>) {
  const updateTreeAction = createUpdateTreeAction(rootNode)
  //noinspection TypeScriptValidateTypes
  appStore.dispatch(updateTreeAction)
}

export default {
  onGroupTreeChanged: onGroupTreeChanged,
  renderGroupTree: renderGroupTree
}