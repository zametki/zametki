/**
 * Interface used by server code to trigger client actions.
 */
import {GroupTreeNode} from '../react/Store'
import {newStartUpdateNotesListAction, newUpdateGroupTreeAction} from '../react/Actions'
import {appStore} from '../react/Reducers'
import Shortcuts from './Shortcuts'
import {renderWorkspace} from '../react/components/Workspace'

interface InitContext {
    groups: GroupTreeNode[],
    workspaceElementId: string
    // groupsViewId: string,
    // navbarViewId: string
    // notesViewId: string
}

function init(ctx: InitContext) {
    // populate store with initial state
    appStore.dispatch(newUpdateGroupTreeAction(ctx.groups))
    appStore.dispatch(newStartUpdateNotesListAction(appStore.getState().activeGroupId))

    renderWorkspace(ctx.workspaceElementId)
    // render all components
    // renderNotesView(ctx.notesViewId)
    // renderNavbarView(ctx.navbarViewId)
    // renderGroupTreeView(ctx.groupsViewId)

    Shortcuts.bindWorkspacePageKeys()
}

// noinspection JSUnusedGlobalSymbols
export default {
    init
}
