import * as React from 'react'
import * as ReactRedux from 'react-redux'

import Modal from '../Modal'
import {AppStore} from '../../Store'
import {newHideModalAction, newMoveNoteAction} from '../../Actions'
import GroupSelector from '../GroupSelector'

type OwnProps = {}

type StateProps = {
    show: boolean
    noteId: number
    currentParentId: number
}

type DispatchProps = {
    hideModal: () => void,
    moveNote: (noteId: number, groupId: number) => void
}

export const MOVE_NOTE_MODAL_ID = 'move-note-modal'

type State = {
    newParentGroupId: number
}

class MoveNoteModalOverlay extends React.Component<OwnProps & StateProps & DispatchProps, State> {

    render() {
        if (!this.props.show) return null
        return (
            <Modal show={this.props.show} close={this.close.bind(this)}>
                <div className="z-modal-body" style={{overflow: "visible", height: "150px"}}>
                    <form className="mt10 mb10" onSubmit={this.move.bind(this)}>
                        <div>
                            Переместить заметку в другую группу
                        </div>
                        <div className="mt10">
                            <GroupSelector selectedGroupId={this.props.currentParentId}
                                           hideRoot={true}
                                           onChange={this.onChange.bind(this)}
                                           autofocus={true}/>
                        </div>
                        <div className="float-right mt20">
                            <button type="button" onClick={this.close.bind(this)} className="btn btn-sm btn-secondary">Отмена</button>
                            <button type="submit" className="btn btn-sm btn-primary ml10">Переместить</button>
                        </div>
                    </form>
                </div>
            </Modal>
        )
    }

    private onChange(id: number) {
        this.setState({newParentGroupId: id})
    }

    close() {
        this.props.hideModal()
    }

    move(e?: React.FormEvent<any>) {
        e && e.preventDefault()
        if (this.state.newParentGroupId >= 0 && this.state.newParentGroupId != this.props.currentParentId) {
            this.props.moveNote(this.props.noteId, this.state.newParentGroupId)
        }
        this.close()
    }
}

function mapStateToProps(store: AppStore): StateProps {
    const noteId = store.notesViewState.lastActionNoteId
    const note = noteId && store.noteById[noteId]
    const currentParentId = note && note.group
    const show = store.activeModalId === MOVE_NOTE_MODAL_ID
    return {show, noteId, currentParentId,}
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        hideModal: () => dispatch(newHideModalAction()),
        moveNote: (noteId: number, groupId: number) => dispatch(newMoveNoteAction(noteId, groupId))
    }
}


export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(MoveNoteModalOverlay) as React.ComponentClass<OwnProps>
