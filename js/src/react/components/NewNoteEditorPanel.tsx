import * as React from 'react'
import * as ReactRedux from 'react-redux'

type DispatchProps = {}
type State = {}

function applyFormStyle(e: HTMLElement, success: boolean) {
    if (!success) {
        e.classList.remove('form-control-success')
        e.classList.add('form-control-error')
    } else {
        e.classList.add('form-control-success')
        e.classList.remove('form-control-error')
    }
}

class NewNoteEditorPanel extends React.Component<DispatchProps, State> {
    refs: {
        textArea: HTMLTextAreaElement,
        textAreaFeedback: HTMLSpanElement
    }

    validate() {
        const {textArea, textAreaFeedback} = this.refs
        const text = this.refs.textArea.value
        if (text.length < 1) {
            textAreaFeedback.innerText = 'Мин. длина заметки: 1 символ'
            textAreaFeedback.classList.add('form-element-feedback-active')
            applyFormStyle(textArea, false)
            return
        }
        if (text.length > 50000) {
            textAreaFeedback.innerText = 'Имя заметки не может превышать 50000 символов'
            textAreaFeedback.classList.add('form-element-feedback-active')
            applyFormStyle(textArea, false)
            return
        }
        textAreaFeedback.innerText = ''
        textAreaFeedback.classList.remove('form-element-feedback-active')
        applyFormStyle(textArea, true)
    }

    render() {
        return <div>
            <form noValidate={true}>
                <div className="pt-2">
                    <textarea ref="textArea" className="form-control new-note-textarea" onChange={this.validate.bind(this)}/>
                    <span ref="textAreaFeedback" className="form-element-feedback"></span>
                    <div className="mt-2">
                        <a className="btn btn-sm btn-secondary mr-1" title="Отменить (Escape)">Отменить</a>
                        <a className="btn btn-sm btn-primary" title="Сохранить (Ctrl+Enter)">Добавить</a>
                    </div>
                </div>
            </form>
        </div>
    }
}

function mapDispatchToProps(): DispatchProps {
    return {}
}


export default ReactRedux.connect(null, mapDispatchToProps)(NewNoteEditorPanel) as React.ComponentClass<{}>