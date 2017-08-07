/**
 * Interface used by server code to trigger client actions.
 */
import {GroupTreeNode} from '../react/Store'
import {renderGroupTreeView} from '../react/components/GroupTreeView'
import {newActivateGroupAction, newUpdateGroupTreeAction} from '../react/Actions'
import {appStore} from '../react/Reducers'
import {renderNavbarView} from '../react/components/Navbar'

function dispatchUpdateGroupTreeAction(rootNode: GroupTreeNode[]) {
    appStore.dispatch(newUpdateGroupTreeAction(rootNode))
}

function dispatchActivateGroupNodeAction(nodeId: number) {
    appStore.dispatch(newActivateGroupAction(nodeId))
}

// noinspection JSUnusedGlobalSymbols
export default {
    renderGroupTreeView,
    renderNavbarView,
    dispatchUpdateGroupTreeAction,
    dispatchActivateGroupNodeAction
}
