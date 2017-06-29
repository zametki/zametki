import * as $ from 'jquery'
import * as Parsley from 'parsleyjs'

function setTitle(selector: string, title: string, root?: HTMLElement): void {
  root = root ? root : window.document.body
  $(root).find(selector).each(function () {
    if (!$(this).attr('title')) {
      $(this).attr('title', title)
    }
  })
}

function focusOnEnter(event: KeyboardEvent, id: string): void {
  if (event.which === 13) {
    $(id).focus()
    event.preventDefault()
  }
}

function clickOnEnter(event: KeyboardEvent, id: string): void {
  let keyCode = (event.which ? event.which : event.keyCode)
  if ((keyCode === 10 || keyCode === 13) && !event.ctrlKey) {
    $(id).click()
    event.preventDefault()
  }
}

function clickOnCtrlEnter(event: KeyboardEvent, id: string): void {
  let keyCode = (event.which ? event.which : event.keyCode)
  if ((keyCode === 10 || keyCode === 13) && event.ctrlKey) {
    $(id).click()
    event.preventDefault()
  }
}

function showMenuByClick(e: Event, id: string): boolean {
  let evt = e ? e : window.event
  if (evt && evt.stopPropagation) {
    evt.stopPropagation()
  }
  if (evt && evt.cancelBubble) {
    evt.cancelBubble = true
  }
  $('#' + id).dropdown('toggle')
  return false
}


function getURLParameter(name: string): string {
  let regExp = new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)')
  return decodeURIComponent((regExp.exec(location.search) || [undefined, ''])[1].replace(/\+/g, '%20')) || undefined
}

function limitTextArea(textAreaId: string, $feedback: JQuery, $button: JQuery, maxTextLen: number, minRemainingToShow: number): void {
  const textArea = window.document.getElementById(textAreaId) as HTMLTextAreaElement
  const limitFn = function () {
    let remaining = maxTextLen - textArea.value.length
    if (remaining <= minRemainingToShow) {
      $feedback.html('' + remaining)
    } else {
      $feedback.html('')
    }
    if (remaining < 0) {
      $feedback.css('color', 'red')
      if ($button) {
        $button.attr('disabled', '')
      }
    } else {
      $feedback.css('color', 'inherit')
      if ($button) {
        $button.removeAttr('disabled')
      }
    }
  }
  textArea.addEventListener('keyup', limitFn)
  limitFn()
}


function enableScrollTop(): void {
  $(document).ready(() => {
    let $backTop = $('#back-top')
    if (!$backTop) {
      return
    }
    $backTop.hide() // hide #back-top first
    $(() => { // fade in #back-top
      $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
          $('#back-top').fadeIn()
        } else {
          $('#back-top').fadeOut()
        }
      })
      $('#back-top').find('a').click(() => { // scroll body to 0px on click
        $('body,html').animate({
          scrollTop: 0
        }, 500)
        return false
      })
    })
  })
}

function removeServerSideParsleyError(el: HTMLElement) {
  const p: Parsley = $(el).parsley()
  p.removeError('server-side-parsley-error')
}

function scrollToBlock(selector: string): void {
  const $block = $(selector)
  const offset = $block.offset()
  $('html, body').animate({
    scrollTop: offset.top
  })
}

function closeModal(jqSelector: HTMLElement | string) {
  $(jqSelector).closest('.modal').modal('hide')
}

export default {
  setTitle: setTitle,
  focusOnEnter: focusOnEnter,
  clickOnEnter: clickOnEnter,
  clickOnCtrlEnter: clickOnCtrlEnter,
  showMenuByClick: showMenuByClick,
  getURLParameter: getURLParameter,
  limitTextArea,
  enableScrollTop: enableScrollTop,
  removeServerSideParsleyError: removeServerSideParsleyError,
  scrollToBlock: scrollToBlock,
  closeModal: closeModal
}
