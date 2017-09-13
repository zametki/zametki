import * as React from 'react'
import {MouseEvent} from 'react'
import * as ReactRedux from 'react-redux'
import {AppStore} from '../Store'
import {newDeleteNoteAction, newShowMoveNoteDialogAction, newStartEditNoteAction, newToggleNoteMenuAction} from '../Actions'

type OwnProps = {
    noteId: number
}

type StateProps = {
    menuVisible: boolean
}

type DispatchProps = {
    toggleNoteMenu: (noteId: number, active: boolean) => void
    startEditNote: (noteId: number) => void
    deleteNote: (noteId: number) => void
    moveNote: (noteId: number) => void
}

type AllProps = DispatchProps & StateProps & OwnProps

class NoteMenu extends React.Component<AllProps, {}> {

    constructor(props: AllProps, context: any) {
        //noinspection TypeScriptValidateTypes
        super(props, context)
        this.showMenu = this.showMenu.bind(this)
        this.onKeyDown = this.onKeyDown.bind(this)
        this.onClick = this.onClick.bind(this)
    }

    componentDidMount(): void {
        //todo:
        window.addEventListener('keydown', this.onKeyDown)
        window.addEventListener('click', this.onClick)
    }

    componentWillUnmount(): void {
        window.removeEventListener('keydown', this.onKeyDown)
        window.removeEventListener('click', this.onClick)
    }

    shouldComponentUpdate(nextProps: Readonly<AllProps>, nextState: Readonly<{}>, nextContext: any): boolean {
        return this.props.menuVisible !== nextProps.menuVisible
    }

    render() {
        return (
            <div>
                <div onClick={this.showMenu.bind(this)} className='note-menu-link' title="Действия над заметкой">
                    <i className="fa fa-angle-down txt-muted f14px"/>
                </div>
                <div className='dropdown'>
                    <div className={'dropdown-menu dropdown-menu-right' + (this.props.menuVisible ? ' show' : '')}>
                        <div className="dropdown-item f14px" onClick={this.onEditNoteClicked.bind(this)}>Редактировать</div>
                        <div className="dropdown-item f14px" onClick={this.onMoveNoteClicked.bind(this)}>Переместить</div>
                        <div className="dropdown-item f14px" onClick={this.onDeleteNoteClicked.bind(this)}>Удалить</div>
                    </div>
                </div>
            </div>
        )
    }

    private onKeyDown() {
        this.closeMenu()
    }

    private onClick() {
        this.closeMenu()
    }

    private onEditNoteClicked() {
        this.props.startEditNote(this.props.noteId)
    }

    private onMoveNoteClicked() {
        this.props.moveNote(this.props.noteId)
    }

    private onDeleteNoteClicked() {
        this.props.deleteNote(this.props.noteId)
    }

    private showMenu(e: MouseEvent<any>) {
        e.stopPropagation()
        this.props.toggleNoteMenu(this.props.noteId, true)
    }

    private closeMenu() {
        if (this.props.menuVisible) {
            this.props.toggleNoteMenu(this.props.noteId, false)
        }
    }
}

/** Maps Store state to component props */
const mapStateToProps = (state: AppStore, ownProps: OwnProps): StateProps => {
    return {
        menuVisible: state.notesViewState.noteMenuNoteId == ownProps.noteId
    }
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        toggleNoteMenu: (noteId: number, active: boolean) => dispatch(newToggleNoteMenuAction(noteId, active)),
        startEditNote: noteId => dispatch(newStartEditNoteAction(noteId)),
        deleteNote: noteId => dispatch(newDeleteNoteAction(noteId)),
        moveNote: noteId => dispatch(newShowMoveNoteDialogAction(noteId))
    }
}

export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(NoteMenu) as React.ComponentClass<OwnProps>
