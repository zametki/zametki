import * as React from 'react'
import {AppStore} from './Store'
import * as ReactRedux from 'react-redux'

type OwnProps = {
  nodeId: number
};

type ConnectedState = {
  entriesCount: number
}

type Props = ConnectedState & OwnProps

/** Maps Store state to component props */
const mapStateToProps = (store: AppStore, ownProps: OwnProps): ConnectedState => {
  let node = store.groupTree.nodeById[ownProps.nodeId]
  return {
    entriesCount: node.entriesCount
  }
}

class GroupTreeCountsBadge extends React.Component<Props, {}> {

  shouldComponentUpdate(nextProps: Readonly<Props>): boolean {
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
export const GTVB: React.ComponentClass<OwnProps> = ReactRedux.connect(mapStateToProps, null)(GroupTreeCountsBadge) as React.ComponentClass<OwnProps>
