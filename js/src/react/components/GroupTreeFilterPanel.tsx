import * as React from 'react'
import * as ReactRedux from 'react-redux'
import {AppStore} from '../Store'
import {newGroupTreeFilterUpdateAction} from '../Actions'

type OwnProps = {}

type StateProps = {
    filterText: string
}

type DispatchProps = {
    updateFilterText: (filterText: string) => void
}

type AllProps = StateProps & DispatchProps & OwnProps

class GroupTreeFilterPanel extends React.Component<AllProps, {}> {
    public refs: {
        inputElement: HTMLInputElement;
    }

    render() {
        const {filterText} = this.props
        const resetIsOn = filterText.length > 0
        const resetButtonClassName = `gt-search-reset-button${resetIsOn ? ' gt-search-reset-button-on' : ''}`
        return (
            <div className="gt-search-block">
                <form onReset={this.handleReset.bind(this)}
                      onSubmit={e => e.preventDefault()}>
                    <input ref="inputElement"
                           onChange={this.handleTextChange.bind(this)}
                           type="search"
                           className="gt-search-input"
                           placeholder=" …"
                           value={filterText}
                           title="Фильтр по имени группы"/>
                    <button className={resetButtonClassName} type="reset" title="Сбросить фильтр">×</button>
                </form>
            </div>
        )
    }

    handleReset() {
        this.props.updateFilterText('')
    }

    handleTextChange() {
        this.props.updateFilterText(this.refs.inputElement.value)
    }
}

/** Maps Store state to component props */
const mapStateToProps = (store: AppStore): StateProps => {
    return {
        filterText: store.groupTree.filterText
    }
}

function mapDispatchToProps(dispatch): DispatchProps {
    return {
        updateFilterText: (filterText) => dispatch(newGroupTreeFilterUpdateAction(filterText))
    }
}

export default ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeFilterPanel) as React.ComponentClass<OwnProps>

