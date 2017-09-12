import * as $ from 'jquery'
import {appStore} from '../react/Reducers'
import {newToggleAddNoteAction} from '../react/Actions'

function isModalOpen(): boolean {
    return $('.modal.show').length > 0
}

function isInInput(element: Element): boolean {
    if (!element || !element.tagName) {
        return false
    }
    return element.tagName === 'INPUT' || element.tagName === 'TEXTAREA'
}

function bindWorkspacePageKeys() {
    window.document.addEventListener('keydown', (event: KeyboardEvent) => {
        if (isModalOpen()) {
            return
        }
        const element = event.srcElement
        if (!isInInput(element)) { // when 'a' is pressed -> show 'Add Note' input
            if (event.which === 65 || event.which === 97) {
                if (!appStore.getState().addNoteIsActive) {
                    event.preventDefault()
                    appStore.dispatch(newToggleAddNoteAction())
                }
            }
        }
    })
}

export default {
    bindWorkspacePageKeys
}
