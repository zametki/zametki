import * as React from 'react'
import {render} from 'react-dom'
import {Provider} from 'react-redux'
import {appStore, GROUP_TREE_ROOT_NODE_ID} from '../Store'
import {GroupTreeNodePanel} from './GroupTreeNodePanel'

export class GroupTreeView {

  static wrap(elementId: string) {
    const groupTree = appStore.getState().groupTree
    const nodeById = groupTree.nodeById
    const treeNodes = groupTree.nodeIds
      .filter(id => nodeById[id].parentId === GROUP_TREE_ROOT_NODE_ID)
      .map(id => <GroupTreeNodePanel nodeId={id} key={'node-' + id} />)

    render(
      <Provider store={appStore}>
        <div>
          {treeNodes}
        </div>
      </Provider>,
      document.getElementById(elementId)
    )
  }
}
