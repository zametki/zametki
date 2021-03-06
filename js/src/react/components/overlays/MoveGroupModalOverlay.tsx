import * as React from 'react'
import * as ReactRedux from 'react-redux'

import Modal from '../Modal'
import {AppStore} from '../../Store'
import {newHideModalAction, newMoveGroupAction} from '../../Actions'
import GroupSelector from '../GroupSelector'

type OwnProps = {}

type StateProps = {
    show: boolean
    groupId: number
    currentParentId: number
    groupName: string
}

type DispatchProps = {
    hideModal: () => void,
    moveGroup: (groupId: number, parentId: number) => void
}

export const MOVE_GROUP_MODAL_ID = 'move-group-modal'

type State = {
    newParentGroupId: number
}

class MoveGroupModalOverlay extends React.Component<OwnProps & StateProps & DispatchProps, State> {

    render() {
        if (!this.props.show) return null
        return (
            <Modal show={this.props.show} close={this.close.bind(this)}>
                <div className="z-modal-body" style={{overflow: "visible", height: "150px"}}>
                    <form className="mt10 mb10" onSubmit={this.move.bind(this)}>
                        <div>
                            Переместить группу «{this.props.groupName}»
                        </div>
                        <div className="mt10">
                            <GroupSelector groupToExclude={this.props.groupId} onChange={this.onChange.bind(this)} autofocus={true}/>
                        </div>
                        <div className="float-right mt20">
                            <button type="button" onClick={this.close.bind(this)} className="btn btn-sm btn-secondary">Отмена</button>
                            <button type="submit" className="btn btn-sm btn-primary ml10">Переместить</button>
                        </div>
                    </form>
                </div>
            </Modal>
        )
    }

    private onChange(id: number) {
        this.setState({newParentGroupId: id})
    }

    close() {
        this.props.hideModal()
    }

    move(e?: React.FormEvent<any>) {
        e && e.preventDefault()
        if (this.state.newParentGroupId >= 0 && this.state.newParentGroupId != this.props.currentParentId) {
            this.props.moveGroup(this.props.groupId, this.state.newParentGroupId)
        }
        this.close()
    }
}

function mapStateToProps(store: AppStore): StateProps {
    const groupId = store.activeGroupId
    const group = store.groupTree && store.groupTree.nodeById[groupId]
    const groupName = group && group.name
    const currentParentId = group && group.parentId
    const show = store.activeModalId === MOVE_GROUP_MODAL_ID && !!groupName
    return {show, groupId, currentParentId, groupName}
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        hideModal: () => dispatch(newHideModalAction()),
        moveGroup: (groupId: number, parentId: number) => dispatch(newMoveGroupAction(groupId, parentId))
    }
}


export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(MoveGroupModalOverlay) as React.ComponentClass<OwnProps>
