import * as React from 'react'
import {render} from 'react-dom'
import * as ReactRedux from 'react-redux'
import {appStore} from '../Reducers'
import LogoPanel from './LogoPanel'
import {AppStore, GROUP_TREE_INVALID_ID} from '../Store'
import {newChangeGroupAction, newShowGroupNavigatorAction, newToggleAddNoteAction} from '../Actions'

type OwnProps = {}

type DispatchProps = {
    resetActiveGroup: () => void
    showGroupNavigator: () => void
    toggleAddNote: () => void
}

type StateProps = {
    lentaMode: boolean,
    addNoteIsActive: boolean
}

class NavbarImpl extends React.Component<StateProps & OwnProps & DispatchProps, any> {

    render() {
        // noinspection HtmlUnknownTarget
        return (
            <div className="navbar navbar-dark fixed-top bg-dark lp-navbar">
                <div className="navbar-logo">
                    <a href="/">
                        <img src="/img/feather_32.png" className="nav-feather-img"/>
                        <LogoPanel/>
                    </a>
                </div>
                <div className="navbar-menu">
                    <div className="navbar-menu-item">
                        {/* TODO: Shortcut? */}
                        <a onClick={this.onAddNoteLinkClicked.bind(this)}
                           className={"btn btn-sm" + (this.props.addNoteIsActive ? " active-create" : "")}
                           title="Добавить заметку (A)">
                            <img src="/img/plus.svg"/>
                        </a>
                    </div>
                    <div className="navbar-menu-item">
                        <a onClick={this.onShowGroupSelectorClicked.bind(this)} className="btn btn-sm" title="Группы">
                            <img src="/img/tree.png" style={{filter: "invert(0)", marginLeft: "4px"}}/>
                        </a>
                    </div>
                    <div className="navbar-menu-item">
                        <a onClick={() => this.props.resetActiveGroup()}
                           className={'btn btn-sm ' + (this.props.lentaMode ? "active-all" : "")}
                           title="Все заметки">
                            <img src="/img/all.svg" style={{transform: "rotate(180deg)"}}/>
                        </a>
                    </div>
                    <div className="navbar-menu-item">
                        <div className="dropdown">
                            <a className="btn btn-sm dropdown-toggle nav-menu-link" data-toggle="dropdown" title="Меню">
                                <i className="fa fa-sun-o bld f24px pr5 pt5"></i>
                            </a>
                            <div className="dropdown-menu dropdown-menu-right">
                                <a href="/my/profile" className="dropdown-item">
                                    <i className="fa fa-cogs f18px black mr5"></i> Настройки
                                </a>
                                <div className="dropdown-divider"></div>
                                <a href="/logout" className="dropdown-item"><img src="/img/log-out.svg" style={{width: "20px"}}/> Выход</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    onShowGroupSelectorClicked() {
        this.props.showGroupNavigator()
    }

    onAddNoteLinkClicked() {
        this.props.toggleAddNote()
    }
}

const mapStateToProps = (store: AppStore): StateProps => {
    return {
        lentaMode: store.activeGroupId == GROUP_TREE_INVALID_ID,
        addNoteIsActive: store.addNoteIsActive
    }
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        showGroupNavigator: () => dispatch(newShowGroupNavigatorAction()),
        resetActiveGroup: () => dispatch(newChangeGroupAction(GROUP_TREE_INVALID_ID)),
        toggleAddNote: () => dispatch(newToggleAddNoteAction())
    }

}

export const Navbar = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(NavbarImpl) as React.ComponentClass<OwnProps>

export function renderNavbarView(elementId: string) {
    render(
        <ReactRedux.Provider store={appStore}>
            <Navbar/>
        </ReactRedux.Provider>,
        document.getElementById(elementId)
    )
}
