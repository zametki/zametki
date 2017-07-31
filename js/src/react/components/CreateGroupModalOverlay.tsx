import * as React from 'react'
import * as ReactRedux from 'react-redux'

import Modal from './Modal'
import {AppStore} from '../Store'
import {newHideModalAction} from '../Actions'
import {createGroup} from "../../utils/Client2Server";

type OwnProps = {}

type StateProps = {
    show: boolean
    parentGroupId: number
}

type DispatchProps = {
    hideModal: () => void
}

export const CREATE_GROUP_MODAL_ID = 'create-group-modal'

class CreateGroupModalOverlay extends React.Component<OwnProps & StateProps & DispatchProps, any> {
    refs: {
        nameInput: HTMLInputElement
    }

    render() {
        if (!this.props.show) return null
        return (
            <Modal show={this.props.show} close={this.close.bind(this)}>
                <div className="z-modal-body">
                    <form className="mt10 mb10" onSubmit={this.create.bind(this)}>
                        <div>
                            Имя новой группы
                            <input ref="nameInput" className="form-control form-control-sm mt5" autoFocus={true}/>
                        </div>
                        <div className="float-right mt20">
                            <a onClick={this.close.bind(this)} className="btn btn-sm btn-secondary">Отмена</a>
                            <a onClick={this.create.bind(this)} className="btn btn-sm btn-primary ml10">Создать</a>
                        </div>
                    </form>
                </div>
            </Modal>
        )
    }

    close() {
        this.props.hideModal()
    }

    create(e?: React.FormEvent<any>) {
        e && e.preventDefault()
        const name = this.refs.nameInput.value
        if (name.length == 0) {
            return
        }
        createGroup(this.props.parentGroupId, name);
        this.close()
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
