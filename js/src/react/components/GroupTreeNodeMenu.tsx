import * as React from 'react'
import {MouseEvent} from 'react'
import * as ReactRedux from 'react-redux'
// noinspection TypeScriptCheckImport
import {Manager, Target, Popper, Arrow} from 'react-popper'
import {
    newChangeGroupAction,
    newDeleteGroupAction,
    newShowCreateGroupAction,
    newShowMoveGroupDialogAction,
    newShowRenameGroupAction,
    newToggleGroupTreeNodeMenuAction
} from '../Actions'
import {AppStore} from '../Store'

type OwnProps = {
    groupId: number
}

type StateProps = {
    menuVisible: boolean
    isEmpty: boolean
}

type DispatchProps = {
    toggleGroupTreeNodeMenu: (nodeId: number, active: boolean) => void
    showCreateGroupDialog: (nodeId: number) => void
    showRenameGroupDialog: (nodeId: number) => void
    showMoveGroupDialog: (nodeId: number) => void
    deleteGroup: (nodeId: number) => void
}

type AllProps = DispatchProps & StateProps & OwnProps

class GroupTreeNodeMenu extends React.Component<AllProps, {}> {

    constructor(props: AllProps, context: any) {
        //noinspection TypeScriptValidateTypes
        super(props, context)
        this.showMenu = this.showMenu.bind(this)
        this.closeMenu = this.closeMenu.bind(this)
    }

    componentDidMount(): void {
        //todo:
        window.addEventListener('keydown', this.closeMenu)
        window.addEventListener('click', this.closeMenu)
    }

    componentWillUnmount(): void {
        window.removeEventListener('keydown', this.closeMenu)
        window.removeEventListener('click', this.closeMenu)
    }

    shouldComponentUpdate(nextProps: Readonly<AllProps>, nextState: Readonly<{}>, nextContext: any): boolean {
        return this.props.menuVisible !== nextProps.menuVisible
    }

    render() {
        let groupId = this.props.groupId
        return (
            <div className="tree-node-menu-block">
                <Manager>
                    <Target>
                        <div onClick={this.showMenu.bind(this)} className='tree-node-menu-link' title="Действия над группой">
                            <i className="fa fa-angle-down  f14px"/>
                        </div>
                    </Target>
                    <Popper placement="bottom">
                        <div className='dropdown'>
                            <div className={'dropdown-menu-popper ' + (this.props.menuVisible ? '' : ' d-none')}>
                                <div className="dropdown-item" onClick={() => this.props.showCreateGroupDialog(groupId)}>Новая группа</div>
                                <div className="dropdown-item" onClick={() => this.props.showRenameGroupDialog(groupId)}>Переименовать</div>
                                <div className="dropdown-item" onClick={() => this.props.showMoveGroupDialog(groupId)}>Переместить</div>
                                {this.props.isEmpty ? <div className="dropdown-item" onClick={() => this.props.deleteGroup(groupId)}>Удалить</div> : null}
                            </div>
                        </div>
                    </Popper>
                </Manager>
            </div>
        )
    }

    private showMenu(e: MouseEvent<any>) {
        e.stopPropagation()
        this.props.toggleGroupTreeNodeMenu(this.props.groupId, true)
    }

    private closeMenu() {
        if (this.props.menuVisible) {
            this.props.toggleGroupTreeNodeMenu(this.props.groupId, false)
        }
    }
}

/** Maps Store state to component props */
const mapStateToProps = (state: AppStore, ownProps: OwnProps): StateProps => {
    const node = state.groupTree.nodeById[ownProps.groupId]
    const isEmpty = node && node.children.length == 0 && node.entriesCount == 0
    return {
        menuVisible: state.activeGroupId === ownProps.groupId && state.groupTree.contextMenuIsActive,
        isEmpty
    }
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        toggleGroupTreeNodeMenu: (groupId: number, active: boolean) => {
            if (active) {
                dispatch(newChangeGroupAction(groupId))
            }
            dispatch(newToggleGroupTreeNodeMenuAction(groupId, active))
        },
        showCreateGroupDialog: groupId => dispatch(newShowCreateGroupAction(groupId)),
        showRenameGroupDialog: groupId => dispatch(newShowRenameGroupAction(groupId)),
        showMoveGroupDialog: groupId => dispatch(newShowMoveGroupDialogAction(groupId)),
        deleteGroup: groupId => dispatch(newDeleteGroupAction(groupId))
    }
}

export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeNodeMenu) as React.ComponentClass<OwnProps>
