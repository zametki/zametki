import * as React from 'react'
import * as ReactRedux from 'react-redux'

import Modal from '../Modal'
import {AppStore} from '../../Store'
import {newHideModalAction} from '../../Actions'
import {renameGroup} from "../../../utils/Client2Server"

type OwnProps = {}

type StateProps = {
    show: boolean
    groupId: number
    currentGroupName: string
}

type DispatchProps = {
    hideModal: () => void
}

export const RENAME_GROUP_MODAL_ID = 'rename-group-modal'

class RenameGroupModalOverlay extends React.Component<OwnProps & StateProps & DispatchProps, any> {
    refs: {
        nameInput: HTMLInputElement
    }

    componentDidUpdate() {
        this.refs && this.refs.nameInput && this.refs.nameInput.select()
    }

    render() {
        if (!this.props.show) return null
        return (
            <Modal show={this.props.show} close={this.close.bind(this)}>
                <div className="z-modal-body">
                    <form className="mt10 mb10" onSubmit={this.rename.bind(this)}>
                        <div className="mt10">
                            Новое имя группы
                            <input ref="nameInput"
                                   defaultValue={this.props.currentGroupName}
                                   className="form-control form-control mt5"
                                   autoFocus={true}
                            />
                        </div>
                        <div className="float-right mt20">
                            <button type="button" onClick={this.close.bind(this)} className="btn btn-sm btn-secondary">Отмена</button>
                            <button type="submit" className="btn btn-sm btn-primary ml10">Переименовать</button>
                        </div>
                    </form>
                </div>
            </Modal>
        )
    }

    close() {
        this.props.hideModal()
    }

    rename(e?: React.FormEvent<any>) {
        e && e.preventDefault()
        const name = this.refs.nameInput.value
        if (name.length > 0 && name != this.props.currentGroupName) {
            renameGroup(this.props.groupId, name)
        }
        this.close()
    }
}

function mapStateToProps(store: AppStore): StateProps {
    const groupId = store.activeGroupId
    const group = store.groupTree && store.groupTree.nodeById[groupId]
    const currentGroupName = group && group.name
    return {
        show: store.activeModalId === RENAME_GROUP_MODAL_ID && !!currentGroupName,
        groupId,
        currentGroupName
    }
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {hideModal: () => dispatch(newHideModalAction())}
}


export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(RenameGroupModalOverlay) as React.ComponentClass<OwnProps>
