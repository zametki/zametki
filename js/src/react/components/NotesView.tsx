import * as React from 'react'
import {render} from 'react-dom'
import * as ReactRedux from 'react-redux'
import Linkify from 'react-linkify'

import {appStore} from '../Reducers'
import {AppStore, Note} from '../Store'
import NoteMenu from './NoteMenu'
import NoteEditor from './NoteEditor'
import NewNoteEditor from './NewNoteEditor'
import MoveNoteModalOverlay from './overlays/MoveNoteModalOverlay'
import GroupNavigatorModalOverlay from './overlays/GroupNavigatorModalOverlay'
import MoveGroupModalOverlay from './overlays/MoveGroupModalOverlay'
import RenameGroupModalOverlay from './overlays/RenameGroupModalOverlay'
import CreateGroupModalOverlay from './overlays/CreateGroupModalOverlay'

type DispatchProps = {}

type StateProps = {
    activeGroupName: string,
    noteIds: number[]
    noteById: { [noteId: number]: Note }
    editedNoteIds: number[]
}

class NotesViewImpl extends React.Component<StateProps & DispatchProps, {}> {
    render() {
        const notes = this.renderNotes()
        return (
            <div>
                {/*Header*/}
                <div className="text-center">
                    <div className="group-header">{this.props.activeGroupName}</div>
                </div>
                <NewNoteEditor/>
                <div>
                    {notes}
                </div>
                <CreateGroupModalOverlay/>
                <RenameGroupModalOverlay/>
                <MoveGroupModalOverlay/>
                <GroupNavigatorModalOverlay/>
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
        editedNoteIds: store.editedNoteIds
    }
}

export const NotesView = ReactRedux.connect(mapStateToProps, null)(NotesViewImpl) as React.ComponentClass<{}>

export function renderNotesView(elementId: string) {
    render(
        <ReactRedux.Provider store={appStore}>
            <div>
                <NotesView/>
            </div>
        </ReactRedux.Provider>,
        document.getElementById(elementId)
    )
}
