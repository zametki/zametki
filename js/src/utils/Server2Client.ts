/**
 * Interface used by server code to trigger client actions.
 */
import {GroupTreeNode, Note} from '../react/Store'
import {renderGroupTreeView} from '../react/components/GroupTreeView'
import {newActivateGroupAction, newUpdateGroupTreeAction, newUpdateNotesListAction} from '../react/Actions'
import {appStore} from '../react/Reducers'
import {renderNavbarView} from '../react/components/Navbar'
import {renderNotesView} from '../react/components/NotesView'

function dispatchUpdateGroupTreeAction(rootNode: GroupTreeNode[]) {
    appStore.dispatch(newUpdateGroupTreeAction(rootNode))
}

function dispatchActivateGroupNodeAction(nodeId: number) {
    appStore.dispatch(newActivateGroupAction(nodeId))
}

function dispatchUpdateNotesListAction(notes: Note[]) {
    appStore.dispatch(newUpdateNotesListAction(notes))
}

// noinspection JSUnusedGlobalSymbols
export default {
    renderGroupTreeView,
    renderNavbarView,
    renderNotesView,
    dispatchUpdateGroupTreeAction,
    dispatchActivateGroupNodeAction,
    dispatchUpdateNotesListAction
}
