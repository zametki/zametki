import * as React from 'react'
import {MouseEvent} from 'react'
import * as ReactRedux from 'react-redux'
import {newShowCreateGroupAction, newToggleGroupTreeNodeMenuAction, newShowRenameGroupAction, newShowMoveGroupAction} from '../Actions'
import {AppStore} from '../Store'
import {activateGroup} from '../../utils/Client2Server'

type OwnProps = {
    nodeId: number
}

type StateProps = {
    menuVisible: boolean
}

type DispatchProps = {
    toggleGroupTreeNodeMenu: (nodeId: number, active: boolean) => void
    showCreateGroupDialog: (nodeId: number) => void
    showRenameGroupDialog: (nodeId: number) => void
    showMoveGroupDialog: (nodeId: number) => void
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
        return (
            <div className="tree-node-menu-block">
                <div onClick={this.showMenu.bind(this)} className='tree-node-menu-link' title="Действия над группой">
                    <i className="fa fa-angle-down  f14px"/>
                </div>
                <div className='dropdown'>
                    <div className={'dropdown-menu dropdown-menu-right' + (this.props.menuVisible ? ' show' : '')}>
                        <div className="dropdown-item" onClick={() => this.props.showCreateGroupDialog(this.props.nodeId)}>
                            Новая группа
                        </div>
                        <div className="dropdown-item" onClick={() => this.props.showRenameGroupDialog(this.props.nodeId)}>
                            Переименовать
                        </div>
                        <div className="dropdown-item" onClick={() => this.props.showMoveGroupDialog(this.props.nodeId)}>
                            Переместить
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    private showMenu(e: MouseEvent<any>) {
        e.stopPropagation()
        this.props.toggleGroupTreeNodeMenu(this.props.nodeId, true)
    }

    private closeMenu() {
        if (this.props.menuVisible) {
            this.props.toggleGroupTreeNodeMenu(this.props.nodeId, false)
        }
    }
}

/** Maps Store state to component props */
const mapStateToProps = (state: AppStore, ownProps: OwnProps): StateProps => {
    return {
        menuVisible: state.activeGroupId === ownProps.nodeId && state.groupTree.contextMenuIsActive
    }
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        toggleGroupTreeNodeMenu: (nodeId: number, active: boolean) => {
            if (active) {
                activateGroup(nodeId)
            }
            dispatch(newToggleGroupTreeNodeMenuAction(nodeId, active))
        },
        showCreateGroupDialog: nodeId => dispatch(newShowCreateGroupAction(nodeId)),
        showRenameGroupDialog: nodeId => dispatch(newShowRenameGroupAction(nodeId)),
        showMoveGroupDialog: nodeId => dispatch(newShowMoveGroupAction(nodeId))
    }
}

export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeNodeMenu) as React.ComponentClass<OwnProps>
