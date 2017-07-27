import * as React from 'react'
import * as ReactRedux from 'react-redux'

import Modal from './Modal'
import {AppStore} from '../Store'
import {createHideModalAction} from '../Actions'

type OwnProps = {}

type StateProps = {
    show: boolean
}

type DispatchProps = {
    hideModal
}

export const CREATE_GROUP_MODAL_ID = 'create-group-modal'

class CreateGroupModalOverlayImpl extends React.Component<OwnProps & StateProps & DispatchProps, any> {
    /**
     * React render method, which displays the component.
     */
    render () {
        return (
            <Modal show={this.props.show} close={this.close.bind(this)}>
                <div className="z-modal-header">
                    <a onClick={this.close.bind(this)} className="z-modal-header-close">&times;</a>
                    <h4 className="z-modal-header-content">Создание новой группы</h4>
                </div>
                <div className="z-modal-body">
                    TODO
                </div>
            </Modal>
        )
    }

    close () {
        console.log('CreateGroupModalOverlayImpl: close')
        this.props.hideModal()
    }
}

function mapStateToProps (store: AppStore, ownProps: OwnProps): StateProps {
    return {show: store.activeModalId === CREATE_GROUP_MODAL_ID}
}

function mapDispatchToProps (dispatch): DispatchProps {
    return {hideModal: () => dispatch(createHideModalAction())}
}


// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const CreateGroupModalOverlay = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(CreateGroupModalOverlayImpl) as React.ComponentClass<OwnProps>
