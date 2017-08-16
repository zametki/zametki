import * as React from 'react'
import {render} from 'react-dom'
import * as ReactRedux from 'react-redux'

import {appStore} from '../Reducers'
import {AppStore, Note} from '../Store'

type DispatchProps = {}

type StateProps = {
    activeGroupName: string,
    notes: Note[]
}

class NotesViewImpl extends React.Component<StateProps & DispatchProps, {}> {
    render() {
        const notes = this.renderNotes()
        return (
            <div>
                {/*Header*/}
                <div className="text-center">
                    <div className="group-header">
                        {this.props.activeGroupName}
                    </div>
                </div>
                <div>
                    {notes}
                </div>
            </div>
        )
    }

    renderNotes(): JSX.Element[] {
        const res = []
        this.props.notes.forEach(z => {
            res.push(
                <div key={'zametka-' + z.id} className="zametka-panel">
                    <div>
                        <span className="txt-muted f12px">{z.dateText}</span>
                    </div>
                    <div className="zametka-content">{z.body}</div>
                </div>
            )
        })
        return res
    }
}

const mapStateToProps = (store: AppStore): StateProps => {
    const activeGroup = store.groupTree.nodeById[store.activeGroupId]
    const activeGroupName = activeGroup ? activeGroup.name : "Все заметки"
    return {
        activeGroupName,
        notes: store.notes,
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
