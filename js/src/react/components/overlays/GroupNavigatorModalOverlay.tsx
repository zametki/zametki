import * as React from 'react'
import * as ReactRedux from 'react-redux'
import {AppStore, GROUP_TREE_ROOT_NODE_ID, GroupTree, GroupTreeNode} from '../../Store'
import Modal from '../Modal'
import GroupTreeCountsBadge from '../GroupTreeCountsBadge'
import {newChangeGroupAction, newHideModalAction} from '../../Actions'

type OwnProps = {}

type StateProps = {
    show: boolean
    groupTree: GroupTree
}

type DispatchProps = {
    activateGroup: (groupId: number) => void
    hideModal: () => void
}

export const GROUP_NAVIGATOR_MODAL_ID = 'group-navigator-modal'

type FlatTreeNode = {
    node: GroupTreeNode,
    depth: number
}

class GroupNavigatorModalOverlay extends React.Component<OwnProps & StateProps & DispatchProps> {

    render() {
        if (!this.props.show) return null
        const {nodeIds, nodeById} = this.props.groupTree
        const flatTreeNodes: Array<FlatTreeNode> = []
        nodeIds.filter(id => nodeById[id] && nodeById[id].parentId === GROUP_TREE_ROOT_NODE_ID)
            .forEach(id => this.flattenTree(id, 0, flatTreeNodes))
        return (
            <Modal show={this.props.show} close={() => this.props.hideModal()}>
                <div className="z-modal-header">
                    <div className="z-modal-header-close" onClick={() => this.props.hideModal()}>×</div>
                    <div className="z-modal-header-content">Выбор группы</div>
                </div>
                <div className="z-modal-body">
                    <div className="mt10 mb10">
                        <ul className="nav nav-pills flex-column">
                            {flatTreeNodes.filter(n => !!n).map(n => this.createFlatTreeElement(n))}
                        </ul>
                    </div>
                </div>
            </Modal>
        )
    }

    private flattenTree(groupId: number, depth: number, result: Array<FlatTreeNode>) {
        const node = this.props.groupTree.nodeById[groupId]
        if (!node) {
            return null
        }
        result.push({node, depth})
        node.children.forEach(c => this.flattenTree(c, depth + 1, result))
    }

    private createFlatTreeElement(n: FlatTreeNode): React.ReactElement<void> {
        return (
            <li className="nav-item" key={'group-navigator-' + n.node.id}>
                <a onClick={() => {
                    this.props.hideModal()
                    this.props.activateGroup(n.node.id)
                }}
                   className="nav-link"
                   style={{padding: "0.3em 0.2em"}}
                >
                    <GroupTreeCountsBadge groupId={n.node.id}/>
                    <span style={{paddingLeft: 15 * n.depth + 'px'}}>{n.node.name}</span>
                </a>
            </li>
        )
    }
}

function mapStateToProps(store: AppStore): StateProps {
    const show = store.activeModalId === GROUP_NAVIGATOR_MODAL_ID
    return {show, groupTree: store.groupTree}
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        hideModal: () => dispatch(newHideModalAction()),
        activateGroup: (groupId: number) => dispatch(newChangeGroupAction(groupId))
    }
}


export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupNavigatorModalOverlay) as React.ComponentClass<OwnProps>
