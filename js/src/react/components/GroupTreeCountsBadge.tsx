import * as React from 'react'
import {AppStore} from '../Store'
import * as ReactRedux from 'react-redux'

type OwnProps = {
  nodeId: number
};

type StateProps = {
  entriesCount: number
}

type AllProps = StateProps & OwnProps

/** Maps Store state to component props */
const mapStateToProps = (store: AppStore, ownProps: OwnProps): StateProps => {
  let node = store.groupTree.nodeById[ownProps.nodeId]
  return {
    entriesCount: node.entriesCount
  }
}

class GroupTreeCountsBadgeImpl extends React.Component<AllProps, {}> {

  shouldComponentUpdate(nextProps: Readonly<AllProps>): boolean {
    return nextProps.entriesCount !== this.props.entriesCount
  }

  render() {
    const {entriesCount} = this.props
    if (entriesCount <= 0) {
      return null
    }
    return (
      <div className='badge zametka-count-badge float-right ml-1'>{entriesCount}</div>
    )
  }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GroupTreeCountsBadge = ReactRedux.connect(mapStateToProps, null)(GroupTreeCountsBadgeImpl) as React.ComponentClass<OwnProps>
