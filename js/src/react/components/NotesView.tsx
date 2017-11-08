import * as React from 'react'
import * as ReactRedux from 'react-redux'
// noinspection TypeScriptCheckImport
import Linkify from 'react-linkify'
import {AppStore, Note} from '../Store'
import NoteMenu from './NoteMenu'
import NoteEditor from './NoteEditor'
import NewNoteEditor from './NewNoteEditor'
import MoveNoteModalOverlay from './overlays/MoveNoteModalOverlay'
import MoveGroupModalOverlay from './overlays/MoveGroupModalOverlay'
import RenameGroupModalOverlay from './overlays/RenameGroupModalOverlay'
import CreateGroupModalOverlay from './overlays/CreateGroupModalOverlay'

type DispatchProps = {}

type StateProps = {
    activeGroupName: string,
    noteIds: number[]
    noteById: { [noteId: number]: Note }
    editedNoteIds: number[],
    showLoadingIndicator: boolean
}

class NotesView extends React.Component<StateProps & DispatchProps, {}> {
    render() {
        const notes = this.renderNotes()
        const loadingIndicator = this.props.showLoadingIndicator ? (<div>loading...</div>) : null
        return (
            <div className="notes-view">
                {/*Header*/}
                <div className="text-center">
                    <div className="group-header">{this.props.activeGroupName}</div>
                </div>
                {loadingIndicator}
                <NewNoteEditor/>
                <div>
                    {notes}
                </div>
                <CreateGroupModalOverlay/>
                <RenameGroupModalOverlay/>
                <MoveGroupModalOverlay/>
                <MoveNoteModalOverlay/>
            </div>
        )
    }

    renderNotes(): JSX.Element[] {
        const res = []
        this.props.noteIds.forEach(id => {
            const z = this.props.noteById[id]
            z && res.push(this.createNoteElement(z))
        })
        return res
    }

    private createNoteElement(z: Note): JSX.Element {
        if (this.props.editedNoteIds.indexOf(z.id) >= 0) {
            return <NoteEditor key={'zametka-editor-' + z.id} noteId={z.id}/>
        }
        return <div key={'zametka-' + z.id} className="zametka-panel">
            <div>
                <table>
                    <tbody>
                    <tr>
                        <td><span className="txt-muted f12px">{z.dateText}</span></td>
                        <td className='pl-1'><NoteMenu noteId={z.id}/></td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div className="zametka-content">
                <Linkify properties={{target: '_blank'}}>
                    {z.content}
                </Linkify>
            </div>
        </div>
    }
}

const mapStateToProps = (store: AppStore): StateProps => {
    const activeGroup = store.groupTree.nodeById[store.activeGroupId]
    const activeGroupName = activeGroup ? activeGroup.name : "Все заметки"
    return {
        activeGroupName,
        noteIds: store.noteIds,
        noteById: store.noteById,
        editedNoteIds: store.editedNoteIds,
        showLoadingIndicator: store.notesViewState.hasPendingNotesListRequest
    }
}

export default ReactRedux.connect(mapStateToProps, null)(NotesView) as React.ComponentClass<{}>

