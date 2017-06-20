import * as React from 'react'
import * as ReactDOM from 'react-dom'
import * as ReactRedux from 'react-redux'
import {appStore, AppStore, GROUP_TREE_ROOT_NODE_ID} from './Store'
import {activateGroup} from '../api/ajax'
import {createToggleTreeNodeAction} from './Actions'
import {GTVB} from './GroupTreeCountsBadge'

type OwnProps = {
  nodeId: number
}

type ConnectedState = {
  name: string
  subGroups: Array<number>
  active: boolean
  expanded: boolean
  level: number
}

type ConnectedDispatch = {
  expandNode: (nodeId: number, expanded: boolean) => void
}

type GroupTreeViewProps = ConnectedState & ConnectedDispatch & OwnProps

/** Maps Store state to component props */
const mapStateToProps = (store: AppStore, ownProps: OwnProps): ConnectedState => {
  let node = store.groupTree.nodeById[ownProps.nodeId]
  return {
    name: node.name,
    subGroups: node.children,
    active: node.active,
    expanded: node.expanded,
    level: node.level
  }
}

// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch): ConnectedDispatch {
  return {
    expandNode: (nodeId, expanded) => dispatch(createToggleTreeNodeAction(nodeId, expanded))
  }
}

export class GroupTreeView extends React.Component<GroupTreeViewProps, {}> {

  constructor(props: GroupTreeViewProps, context: any) {
    //noinspection TypeScriptValidateTypes
    super(props, context)
    this.onToggleExpandedState = this.onToggleExpandedState.bind(this)
    this.activateGroup = this.activateGroup.bind(this)
  }

  static wrap(elementId: string) {
    let groupTree = appStore.getState().groupTree
    let nodeById = groupTree.nodeById
    const treeNodes = groupTree.nodeIds
      .filter(id => nodeById[id].parentId === GROUP_TREE_ROOT_NODE_ID)
      .map(id => <GTV nodeId={id} key={'node-' + id} />)
    ReactDOM.render(
      <ReactRedux.Provider store={appStore}>
        <div>
          {treeNodes}
        </div>
      </ReactRedux.Provider>,
      document.getElementById(elementId)
    )
  }

  render() {
    const {nodeId, name, subGroups, active, expanded, level} = this.props
    if (!name) {
      console.error(`Node not found: ${this.props}`)
      return null
    }
    return (
      <div>
        <div className={'tree-node' + (active ? ' tree-node-active' : '')}>
          <div style={{paddingLeft: level * 16}}>
            <table className='w100'>
              <tbody>
              <tr>
                <td className='tree-junction-td'>
                  <a className={'tree-junction' + (subGroups && subGroups.length > 0 ? (expanded ? ' tree-junction-expanded' : ' tree-junction-collapsed') : '')}
                     onClick={this.onToggleExpandedState} />
                </td>
                <td>
                  <div className='tree-content'>
                    <a className='tree-node-group-link' onClick={this.activateGroup}>
                      <GTVB nodeId={nodeId} />
                      <span>{name}</span>
                    </a>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        {expanded && subGroups && subGroups.map(childId => <GTV nodeId={childId} key={'node-' + childId} />)}
      </div>
    )
  }

  private onToggleExpandedState() {
    this.props.expandNode(this.props.nodeId, !this.props.expanded)
  }

  private activateGroup() {
    activateGroup(this.props.nodeId)
  }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GTV: React.ComponentClass<OwnProps> = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeView) as React.ComponentClass<OwnProps>
