import * as React from 'react'
import {SyntheticEvent} from 'react'
import * as ReactRedux from 'react-redux'
import {AppStore} from '../Store'
import {newToggleAddNoteAction} from '../Actions'

type StateProps = {
    active: boolean
}

type DispatchProps = {
    toggleAddNote: () => void
}

type State = {
    validationResult: ValidationResult
}

type ValidationResult = {
    hasErrors: boolean,
    errorMessage: string
}

class NewNoteEditorPanel extends React.Component<StateProps & DispatchProps, State> {
    refs: {
        textArea: HTMLTextAreaElement,
    }

    constructor(props: StateProps & DispatchProps, ctx: any) {
        super(props, ctx)
        this.validate()
        this.state = this.validate()
    }

    validate(): State {
        const validationResult = {hasErrors: false, errorMessage: ''}
        if (!this.refs || !this.refs.textArea) {
            validationResult.hasErrors = true
        } else {
            const text = this.refs.textArea.value
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
                        <textarea ref="textArea"
                                  className={'form-control new-note-textarea' + (v.hasErrors ? ' form-control-error' : ' form-control-success')}
                                  onKeyDown={this.onKeyDown.bind(this)}
                                  onChange={this.onChange.bind(this)}
                                  autoFocus={true}/>
                        <span ref="textAreaFeedback" className={"form-element-feedback" + (v.hasErrors ? ' form-element-feedback-active' : '')}>{v.errorMessage}</span>
                        <div className="mt-2">
                            <a onClick={this.onCancelClicked.bind(this)} className="btn btn-sm btn-secondary mr-1" title="Отменить (Escape)">Отменить</a>
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
        if (e.target == this.refs.textArea && e.keyCode == 27) {
            this.props.toggleAddNote()
        }
    }

    onCancelClicked() {
        this.props.toggleAddNote()
    }

    onCreateClicked() {
        this.validate()
        alert('Save! Has errors: ' + this.state.validationResult.hasErrors)
    }

}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        toggleAddNote: () => dispatch(newToggleAddNoteAction())
    }
}

function mapStateToProps(state: AppStore): StateProps {
    return {active: state.addNoteIsActive}
}


export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(NewNoteEditorPanel) as React.ComponentClass<{}>