import * as React from 'react'
import {SyntheticEvent} from 'react'
import * as ReactRedux from 'react-redux'
import TextareaAutosize from 'react-autosize-textarea'
import {AppStore} from '../Store'
import {newCreateNoteAction, newToggleAddNoteAction} from '../Actions'
import {ValidationResult} from './NoteEditor'

type StateProps = {
    active: boolean,
    groupId: number
}

type DispatchProps = {
    toggleAddNote: () => void
    createNote: (groupId: number, text: string) => void
}

type State = {
    validationResult: ValidationResult
}

class NoteEditor extends React.Component<StateProps & DispatchProps, State> {
    textArea: HTMLTextAreaElement = null

    constructor(props: StateProps & DispatchProps, ctx: any) {
        // noinspection TypeScriptValidateTypes
        super(props, ctx)
        this.state = this.validate()
    }

    validate(): State {
        const validationResult = {hasErrors: false, errorMessage: ''}
        if (this.props.groupId <= 0) {
            validationResult.hasErrors = true
            validationResult.errorMessage = 'Не выбрана группа'
        } else if (!this.refs || !this.textArea) {
            validationResult.hasErrors = true
        } else {
            const text = this.textArea.value
            if (text.length < 1) {
                validationResult.hasErrors = true
            }
            if (text.length > 50000) {
                validationResult.hasErrors = true
                validationResult.errorMessage = 'Имя заметки не может превышать 50000 символов'
            }
        }
        return {...this.state, validationResult}
    }


    shouldComponentUpdate(nextProps: Readonly<StateProps & DispatchProps>, nextState: Readonly<State>): boolean {
        const v1 = this.state.validationResult
        const v2 = nextState.validationResult
        return nextProps.active !== this.props.active || v1.errorMessage !== v2.errorMessage || v1.hasErrors !== v2.hasErrors
    }

    render() {
        if (!this.props.active) {
            return null
        }
        const v = this.state.validationResult
        return (
            <div className="pb-3">
                <form noValidate={true}>
                    <div className="pt-2">
                        <TextareaAutosize innerRef={ref => this.textArea = ref}
                                          className={'form-control new-note-textarea' + (v.hasErrors ? ' form-control-error' : ' form-control-success')}
                                          onKeyDown={this.onKeyDown.bind(this)}
                                          onChange={this.onChange.bind(this)}
                                          autoFocus={true}/>
                        <span ref="textAreaFeedback" className={"form-element-feedback" + (v.hasErrors ? ' form-element-feedback-active' : '')}>{v.errorMessage}</span>
                        <div className="mt-2">
                            <a onClick={this.onCancelClicked.bind(this)}
                               className="btn btn-sm btn-secondary mr-1"
                               title="Отменить (Escape)">Отменить</a>
                            <a onClick={this.onCreateClicked.bind(this)}
                               className={'btn btn-sm btn-primary' + (v.hasErrors ? ' disabled' : '')}
                               title="Сохранить (Ctrl+Enter)">Добавить</a>
                        </div>
                    </div>
                </form>
            </div>
        )
    }

    onChange() {
        this.setState(this.validate())
    }

    onKeyDown(se: SyntheticEvent<any>) {
        const e = se.nativeEvent as KeyboardEvent
        if (e.target == this.textArea && e.keyCode == 27) {
            this.onCancelClicked()
        } else if (e.ctrlKey && e.keyCode == 13) {
            if (!this.state.validationResult.hasErrors) {
                this.onCreateClicked()
            }
        }
    }

    onCancelClicked() {
        this.props.toggleAddNote()
    }

    onCreateClicked() {
        if (this.state.validationResult.hasErrors) {
            console.error('Validation error: ' + this.state.validationResult.errorMessage)
            return
        }
        this.props.createNote(this.props.groupId, this.textArea.value)
    }

}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        toggleAddNote: () => dispatch(newToggleAddNoteAction()),
        createNote: (groupId: number, text: string) => dispatch(newCreateNoteAction(groupId, text))
    }
}

function mapStateToProps(state: AppStore): StateProps {
    return {
        active: state.addNoteIsActive,
        groupId: state.activeGroupId
    }
}


export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(NoteEditor) as React.ComponentClass<{}>