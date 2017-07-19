import * as $ from 'jquery'

function isABootstrapModalOpen(): boolean {
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
        if (isABootstrapModalOpen()) {
            return
        }
        const element = event.srcElement
        if (isInInput(element)) {
            if (event.which === 27) { // todo: rework this part. Make it work for editing existing entries too
                const elementId = element.getAttribute('id')
                if (elementId === 'create-zametka-text-area') {
                    setTimeout(() => $('#create-zametka-cancel-button').click(), 100)
                }
            }
            return
        }
        if (event.which === 65 || event.which === 97) {
            const $btn = $('#add-zametka-button')
            if ($btn.hasClass('active-create')) {
                return
            }
            $btn.click()
        }
    })
}

export default {
    bindWorkspacePageKeys
}
