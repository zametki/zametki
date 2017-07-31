import * as React from 'react'
import * as ReactRedux from 'react-redux'
import {AppStore, GroupTreeNode} from '../Store'
import {activateGroup} from '../../utils/Client2Server'
import {newToggleGroupTreeNodeAction} from '../Actions'
import GroupTreeCountsBadge from './GroupTreeCountsBadge'
import GroupTreeNodeMenu from './GroupTreeNodeMenu'
import {appStore} from '../Reducers'

type OwnProps = {
    nodeId: number
}

type StateProps = {
    name: string
    subGroups: number[],
    active: boolean
    expanded: boolean
    level: number,
    filterText: string
}

type DispatchProps = {
    toggleExpandedState: (nodeId: number, expanded: boolean) => void
}

type AllProps = StateProps & DispatchProps & OwnProps

/** Maps Store state to component props */
const mapStateToProps = (state: AppStore, ownProps: OwnProps): StateProps => {
    const node = state.groupTree.nodeById[ownProps.nodeId]
    return {
        name: node.name,
        subGroups: node.children,
        active: node.id === state.activeGroupId,
        expanded: node.expanded,
        level: node.level,
        filterText: state.groupTree.filterText
    }
}

// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch): DispatchProps {
    return {
        toggleExpandedState: (nodeId, expanded) => dispatch(newToggleGroupTreeNodeAction(nodeId, expanded))
    }
}

function matchesByFilter(nodeId: number, filterText: string, nodeById: { [nodeId: number]: GroupTreeNode }): boolean {
    if (filterText.length === 0) {
        return true
    }
    const node = nodeById[nodeId]
    if (node && node.name.toLowerCase().includes(filterText)) {
        return true
    }
    return node.children.find(id => matchesByFilter(id, filterText, nodeById)) >= 0
}

class GroupTreeNodePanelImpl extends React.Component<AllProps, {}> {

    constructor(props: AllProps, context: any) {
        //noinspection TypeScriptValidateTypes
        super(props, context)
        this.onToggleExpandedState = this.onToggleExpandedState.bind(this)
        this.activateGroup = this.activateGroup.bind(this)
    }

    render() {
        const {nodeId, name, subGroups, active, expanded, level, filterText} = this.props
        if (!name) {
            // console.error(`Node not found: ${this.props}`)
            return null
        }
        const filtered = !matchesByFilter(nodeId, filterText.toLowerCase(), appStore.getState().groupTree.nodeById)
        const node = filtered ? null :
            (
                <div className={'tree-node' + (active ? ' tree-node-active' : '')}>
                    <div style={{paddingLeft: level * 16}}>
                        <table className='w100'>
                            <tbody>
                            <tr>
                                <td className='tree-junction-td'>
                                    <a className={'tree-junction' + (subGroups && subGroups.length > 0 && (expanded ? ' tree-junction-expanded' : ' tree-junction-collapsed'))}
                                       onClick={this.onToggleExpandedState}/>
                                </td>
                                <td>
                                    <div className='tree-content'>
                                        <a className='tree-node-group-link' onClick={this.activateGroup}>
                                            <GroupTreeNodeMenu nodeId={nodeId}/>
                                            <GroupTreeCountsBadge nodeId={nodeId}/>
                                            <span>{name}</span>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            )

        return (
            <div>
                {node}
                {expanded && !filtered && subGroups && subGroups.map(
                    childId => <GroupTreeNodePanel nodeId={childId} key={'node-' + childId}/>)
                }
            </div>
        )
    }

    private onToggleExpandedState() {
        this.props.toggleExpandedState(this.props.nodeId, !this.props.expanded)
    }

    private activateGroup() {
        activateGroup(this.props.nodeId)
    }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
const GroupTreeNodePanel = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeNodePanelImpl) as React.ComponentClass<OwnProps>
export default GroupTreeNodePanel
