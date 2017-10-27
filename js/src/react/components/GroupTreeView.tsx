import * as React from 'react'
import * as ReactRedux from 'react-redux'
import {AppStore, GROUP_TREE_ROOT_NODE_ID} from '../Store'
import GroupTreeNodePanel from './GroupTreeNodePanel'
import GroupTreeFilterPanel from './GroupTreeFilterPanel'

type OwnProps = {}
type StateProps = {
    topLevelGroupIds: number[]
}

class GroupTreeView extends React.Component<OwnProps & StateProps, {}> {

    render() {
        const treeNodes = this.props.topLevelGroupIds.map(id => <GroupTreeNodePanel nodeId={id} key={'node-' + id}/>)
        return (
            <div className="tree">
                <GroupTreeFilterPanel/>
                {treeNodes}
            </div>
        )

    }
}

function mapStateToProps(store: AppStore): StateProps {
    const topLevelGroupIds = store.groupTree.nodeIds.filter(id => store.groupTree.nodeById[id].parentId === GROUP_TREE_ROOT_NODE_ID)
    return {topLevelGroupIds}
}

export default ReactRedux.connect(mapStateToProps, null)(GroupTreeView) as React.ComponentClass<OwnProps>
