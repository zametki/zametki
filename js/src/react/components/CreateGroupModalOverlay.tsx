import * as React from 'react'
import * as ReactRedux from 'react-redux'

import Modal from './Modal'
import {AppStore} from '../Store'
import {newHideModalAction} from '../Actions'
import {createGroup} from "../../utils/Client2Server"
import GroupSelector from './GroupSelector'

type OwnProps = {}

type StateProps = {
    show: boolean
    parentGroupId: number
}

type DispatchProps = {
    hideModal: () => void
}

export const CREATE_GROUP_MODAL_ID = 'create-group-modal'

type State = {
    parentGroupId: number
}

class CreateGroupModalOverlay extends React.Component<OwnProps & StateProps & DispatchProps, State> {
    refs: {
        nameInput: HTMLInputElement
    }

    render() {
        if (!this.props.show) return null
        return (
            <Modal show={this.props.show} close={this.close.bind(this)}>
                <div className="z-modal-body" style={{overflow: "visible", height: "225px"}}>
                    <form className="mt10 mb10" onSubmit={this.create.bind(this)}>
                        <div>Родительская группа</div>
                        <div className="mt10">
                            <GroupSelector selectedGroupId={this.props.parentGroupId} onChange={this.onParentChanged.bind(this)}/>
                        </div>
                        <div className="mt10">
                            Имя новой группы
                            <input ref="nameInput" className="form-control form-control mt5" autoFocus={true} />
                        </div>
                        <div className="float-right mt20">
                            <button type="button" onClick={this.close.bind(this)} className="btn btn-sm btn-secondary">Отмена</button>
                            <button type="submit" className="btn btn-sm btn-primary ml10">Создать</button>
                        </div>
                    </form>
                </div>
            </Modal>
        )
    }

    private close() {
        this.props.hideModal()
    }

    private create(e?: React.FormEvent<any>) {
        console.log('create!')
        e && e.preventDefault()
        const name = this.refs.nameInput.value
        if (name.length == 0) {
            return
        }
        const parentGroupId = this.props.parentGroupId || this.state.parentGroupId
        createGroup(parentGroupId, name)
        this.close()
    }

    private onParentChanged(parentGroupId: number) {
        this.setState({parentGroupId})
    }
}

function mapStateToProps(store: AppStore): StateProps {
    return {
        show: store.activeModalId === CREATE_GROUP_MODAL_ID,
        parentGroupId: store.activeGroupId
    }
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {hideModal: () => dispatch(newHideModalAction())}
}


export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(CreateGroupModalOverlay) as React.ComponentClass<OwnProps>
