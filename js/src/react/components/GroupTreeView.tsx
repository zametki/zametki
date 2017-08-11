import * as React from 'react'
import {render} from 'react-dom'
import * as ReactRedux from 'react-redux'
import {AppStore, GROUP_TREE_ROOT_NODE_ID} from '../Store'
import GroupTreeNodePanel from './GroupTreeNodePanel'
import GroupTreeFilterPanel from './GroupTreeFilterPanel'
import {appStore} from '../Reducers'
import CreateGroupModalOverlay from './overlays/CreateGroupModalOverlay'
import RenameGroupModalOverlay from "./overlays/RenameGroupModalOverlay"
import MoveGroupModalOverlay from './overlays/MoveGroupModalOverlay'
import GroupNavigatorModalOverlay from './overlays/GroupNavigatorModalOverlay'

type OwnProps = {}
type StateProps = {
    topLevelGroupIds: number[]
}

type AllProps = OwnProps & StateProps

const mapStateToProps = (store: AppStore): StateProps => {
    const topLevelGroupIds = store.groupTree.nodeIds
        .filter(id => store.groupTree.nodeById[id].parentId === GROUP_TREE_ROOT_NODE_ID)
    return {topLevelGroupIds}
}

export class GroupTreeViewImpl extends React.Component<AllProps, {}> {

    render() {
        const treeNodes = this.props.topLevelGroupIds.map(id => <GroupTreeNodePanel nodeId={id} key={'node-' + id}/>)
        return (
            <div>
                <GroupTreeFilterPanel/>
                {treeNodes}
            </div>
        )

    }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GroupTreeView = ReactRedux.connect(mapStateToProps, null)(GroupTreeViewImpl) as React.ComponentClass<OwnProps>

export function renderGroupTreeView(elementId: string) {
    render(
        <ReactRedux.Provider store={appStore}>
            <div>
                <GroupTreeView/>
                <CreateGroupModalOverlay/>
                <RenameGroupModalOverlay/>
                <MoveGroupModalOverlay/>
                <GroupNavigatorModalOverlay/>
            </div>
        </ReactRedux.Provider>,
        document.getElementById(elementId)
    )
}
