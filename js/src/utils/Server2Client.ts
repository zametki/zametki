/**
 * Interface used by server code to trigger client actions.
 */
import {GroupTreeNode} from '../react/Store'
import {renderGroupTreeView} from '../react/components/GroupTreeView'
import {newStartUpdateNotesListAction, newUpdateGroupTreeAction} from '../react/Actions'
import {appStore} from '../react/Reducers'
import {renderNavbarView} from '../react/components/Navbar'
import {renderNotesView} from '../react/components/NotesView'
import Shortcuts from './Shortcuts'

interface InitContext {
    groups: GroupTreeNode[],
    groupsViewId: string,
    navbarViewId: string
    notesViewId: string
}

function init(ctx: InitContext) {
    // populate store with initial state
    appStore.dispatch(newUpdateGroupTreeAction(ctx.groups))
    appStore.dispatch(newStartUpdateNotesListAction(appStore.getState().activeGroupId))

    // render all components
    renderNotesView(ctx.notesViewId)
    renderNavbarView(ctx.navbarViewId)
    renderGroupTreeView(ctx.groupsViewId)

    Shortcuts.bindWorkspacePageKeys()
}

// noinspection JSUnusedGlobalSymbols
export default {
    init
}
