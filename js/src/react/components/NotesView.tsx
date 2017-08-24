import * as React from 'react'
import {render} from 'react-dom'
import * as ReactRedux from 'react-redux'

import {appStore} from '../Reducers'
import {AppStore, Note} from '../Store'
import NoteMenu from './NoteMenu'

type DispatchProps = {}

type StateProps = {
    activeGroupName: string,
    noteIds: number[]
    noteById: { [noteId: number]: Note }
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
                <div>
                    {notes}
                </div>
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

            <div className="zametka-content">{z.body}</div>
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
    }
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {}
}

export const NotesView = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(NotesViewImpl) as React.ComponentClass<{}>

export function renderNotesView(elementId: string) {
    render(
        <ReactRedux.Provider store={appStore}>
            <NotesView/>
        </ReactRedux.Provider>,
        document.getElementById(elementId)
    )
}
