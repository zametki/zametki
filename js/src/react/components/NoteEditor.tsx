import * as React from 'react'
import {SyntheticEvent} from 'react'
import * as ReactRedux from 'react-redux'
import TextareaAutosize from 'react-autosize-textarea'
import {AppStore} from '../Store'
import {newCancelEditNoteAction, newUpdateNoteAction} from '../Actions'

type OwnProps = {
    noteId: number
}

type StateProps = {
    origNoteText: string
}

type DispatchProps = {
    updateNote: (noteId: number, text: string) => void
    cancelEdit: (noteId: number) => void
}

type State = {
    validationResult: ValidationResult
}

export type ValidationResult = {
    hasErrors: boolean,
    errorMessage: string
}

class NoteEditor extends React.Component<OwnProps & StateProps & DispatchProps, State> {
    textArea: HTMLTextAreaElement = null

    constructor(props: OwnProps & StateProps & DispatchProps, ctx: any) {
        // noinspection TypeScriptValidateTypes
        super(props, ctx)
        this.state = this.validate(props.origNoteText)
    }

    validate(text: string): State {
        const validationResult = {hasErrors: false, errorMessage: ''}
        if (text.length < 1) {
            validationResult.hasErrors = true
        }
        if (text.length > 50000) {
            validationResult.hasErrors = true
            validationResult.errorMessage = 'Имя заметки не может превышать 50000 символов'
        }
        return {...this.state, validationResult}
    }


    shouldComponentUpdate(nextProps: Readonly<StateProps & DispatchProps>, nextState: Readonly<State>): boolean {
        const vr1 = this.state.validationResult
        const vr2 = nextState.validationResult
        return vr1.errorMessage !== vr2.errorMessage || vr1.hasErrors !== vr2.hasErrors
    }

    render() {
        const v = this.state.validationResult
        return (
            <div className="pb-3">
                <form noValidate={true}>
                    <div className="pt-2">
                        <TextareaAutosize innerRef={ref => this.textArea = ref}
                                          className={'form-control new-note-textarea' + (v.hasErrors ? ' form-control-error' : ' form-control-success')}
                                          defaultValue={this.props.origNoteText}
                                          onKeyDown={this.onKeyDown.bind(this)}
                                          onChange={this.onChange.bind(this)}
                                          autoFocus={true}/>
                        <span ref="textAreaFeedback" className={"form-element-feedback" + (v.hasErrors ? ' form-element-feedback-active' : '')}>{v.errorMessage}</span>
                        <div className="mt-2">
                            <a onClick={this.onCancelClicked.bind(this)}
                               className="btn btn-sm btn-secondary mr-1"
                               title="Отменить (Escape)">Отменить</a>
                            <a onClick={this.onSaveClicked.bind(this)}
                               className={'btn btn-sm btn-primary' + (v.hasErrors ? ' disabled' : '')}
                               title="Сохранить (Ctrl+Enter)">Сохранить</a>
                        </div>
                    </div>
                </form>
            </div>
        )
    }

    onChange() {
        this.setState(this.validate(this.textArea.value))
    }

    onKeyDown(se: SyntheticEvent<any>) {
        const e = se.nativeEvent as KeyboardEvent
        if (e.target == this.textArea && e.keyCode == 27) {
            this.onCancelClicked()
        } else if (e.ctrlKey && e.keyCode == 13) {
            if (!this.state.validationResult.hasErrors) {
                this.onSaveClicked()
            }
        }
    }

    onCancelClicked() {
        this.props.cancelEdit(this.props.noteId)
    }

    onSaveClicked() {
        if (this.state.validationResult.hasErrors) {
            console.error('Validation error: ' + this.state.validationResult.errorMessage)
            return
        }
        this.props.updateNote(this.props.noteId, this.textArea.value)
    }

}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        cancelEdit: (noteId: number) => dispatch(newCancelEditNoteAction(noteId)),
        updateNote: (noteId: number, text: string) => dispatch(newUpdateNoteAction(noteId, text))
    }
}

function mapStateToProps(state: AppStore, ownProps: OwnProps): StateProps {
    const note = state.noteById[ownProps.noteId]
    const origNoteText = note && note.content
    return {origNoteText}
}


export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(NoteEditor) as React.ComponentClass<OwnProps>