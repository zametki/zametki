import * as React from 'react'
import {AppStore} from '../Store'
import * as ReactRedux from 'react-redux'

type OwnProps = {
    groupId: number
}

type StateProps = {
    entriesCount: number
}

type AllProps = StateProps & OwnProps

class GroupTreeCountsBadge extends React.Component<AllProps, {}> {

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


/** Maps Store state to component props */
const mapStateToProps = (store: AppStore, ownProps: OwnProps): StateProps => {
    const node = store.groupTree.nodeById[ownProps.groupId]
    return {
        entriesCount: node.entriesCount
    }
}

export default ReactRedux.connect(mapStateToProps, null)(GroupTreeCountsBadge) as React.ComponentClass<OwnProps>
