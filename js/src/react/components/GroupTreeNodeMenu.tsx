import * as React from 'react'
import {MouseEvent} from 'react'
import * as ReactRedux from 'react-redux'
import {createShowCreateGroupAction, createToggleGroupTreeNodeMenuAction, createToggleGroupTreeNodeRenameAction} from '../Actions'
import {AppStore} from '../Store'
import {activateGroup} from '../../utils/Client2Server'

type OwnProps = {
    nodeId: number
}

type StateProps = {
    menuVisible: boolean
}

type DispatchProps = {
    toggleGroupTreeNodeRename: (nodeId: number) => void
    toggleGroupTreeNodeMenu: (nodeId: number, active: boolean) => void
    showCreateGroupModal: (nodeId: number) => void
}

type AllProps = DispatchProps & StateProps & OwnProps

class GroupTreeNodeMenuImpl extends React.Component<AllProps, {}> {

    constructor (props: AllProps, context: any) {
        //noinspection TypeScriptValidateTypes
        super(props, context)
        this.showMenu = this.showMenu.bind(this)
        this.closeMenu = this.closeMenu.bind(this)
    }

    componentDidMount (): void {
        window.addEventListener('keydown', this.closeMenu)
        window.addEventListener('click', this.closeMenu)
    }

    componentWillUnmount (): void {
        window.removeEventListener('keydown', this.closeMenu)
        window.removeEventListener('click', this.closeMenu)
    }

    shouldComponentUpdate (nextProps: Readonly<AllProps>, nextState: Readonly<{}>, nextContext: any): boolean {
        return this.props.menuVisible !== nextProps.menuVisible
    }

    render () {
        return (
            <div className="zametka-group-menu-block">
                <div onClick={this.showMenu.bind(this)} className='zametka-group-menu-link' title="Настроить группу">
                    <i className="fa fa-angle-down"/>
                </div>
                <div className={'dropdown' + (this.props.menuVisible ? ' show' : '')}>
                    <div className="dropdown-menu dropdown-menu-right">
                        <div className="dropdown-item" onClick={() => this.props.showCreateGroupModal(this.props.nodeId)}>
                            Новая группа…
                        </div>
                        <div className="dropdown-item">
                            Переименовать…
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    private showMenu (e: MouseEvent<any>) {
        e.stopPropagation()
        this.props.toggleGroupTreeNodeMenu(this.props.nodeId, true)
    }

    private closeMenu () {
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

function mapDispatchToProps (dispatch): DispatchProps {
    return {
        toggleGroupTreeNodeRename: (nodeId) => dispatch(createToggleGroupTreeNodeRenameAction(nodeId, true)),
        toggleGroupTreeNodeMenu: (nodeId: number, active: boolean) => {
            if (active) {
                activateGroup(nodeId)
            }
            dispatch(createToggleGroupTreeNodeMenuAction(nodeId, active))
        },
        showCreateGroupModal: (nodeId) => dispatch(createShowCreateGroupAction(nodeId))
    }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GroupTreeNodeMenu = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeNodeMenuImpl) as React.ComponentClass<OwnProps>
