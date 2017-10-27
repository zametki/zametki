import * as React from 'react'
import * as ReactRedux from 'react-redux'
// noinspection TypeScriptCheckImport
import Sidebar from 'react-sidebar'
import {appStore} from '../Reducers'
import {render} from 'react-dom'
import GroupTreeView from './GroupTreeView'
import {isDockedSidebarMode} from '../../utils/UIUtils'
import Navbar from './Navbar'
import NotesView from './NotesView'
import {AppStore} from '../Store'
import {newUpdateSidebarState} from '../Actions'

type StateProps = {
    sidebarOpen: boolean
    sidebarDocked: boolean
}

type DispatchProps = {
    updateSidebarState: (open: boolean) => void
}

class Workspace extends React.Component<StateProps & DispatchProps, {}> {

    constructor(props: StateProps & DispatchProps, context: any) {
        // noinspection TypeScriptValidateTypes
        super(props, context)
        this.updateDimensions = this.updateDimensions.bind(this)
    }

    componentWillMount() {
        this.updateDimensions()
        window.addEventListener("resize", this.updateDimensions)
    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.updateDimensions)
    }

    updateDimensions() {
        const isDockedMode = isDockedSidebarMode()
        if (this.props.sidebarDocked != isDockedMode) {
            this.props.updateSidebarState(isDockedMode)
        }

    }

    render() {
        const sidebar = <GroupTreeView/>
        //todo: use transitions in desktop mode also, but disable initial animation
        return (
            <Sidebar sidebar={sidebar}
                     open={this.props.sidebarOpen}
                     docked={this.props.sidebarDocked}
                     rootClassName="workspace"
                     sidebarClassName="sidebar"
                     onSetOpen={open => this.props.updateSidebarState(open)}
                     transitions={!isDockedSidebarMode()}
                     styles={{
                         root: {top: 55}
                     }}>
                <Navbar/>
                <NotesView/>
            </Sidebar>
        )
    }
}

function mapStateToProps(store: AppStore): StateProps {
    return {
        sidebarOpen: store.sidebar.open,
        sidebarDocked: store.sidebar.docked
    }
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        updateSidebarState: (open: boolean) => dispatch(newUpdateSidebarState(open)),
    }
}

const WS = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(Workspace) as React.ComponentClass<{}>

export function renderWorkspace(elementId: string) {
    render(
        <ReactRedux.Provider store={appStore}>
            <div>
                <WS/>
            </div>
        </ReactRedux.Provider>,
        document.getElementById(elementId)
    )
}
