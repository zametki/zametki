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
  window.document.addEventListener('keydown', function (event: KeyboardEvent) {
    if (isABootstrapModalOpen()) {
      return
    }
    let element = event.srcElement
    if (isInInput(element)) {
      if (event.which === 27) { //todo: rework this part. Make it work for editing existing entries too
        const elementId = element.getAttribute('id')
        if (elementId === 'create-zametka-text-area') {
          $('#create-zametka-cancel-button').click()
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
  bindWorkspacePageKeys: bindWorkspacePageKeys
}