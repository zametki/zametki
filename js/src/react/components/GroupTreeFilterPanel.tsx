import * as React from 'react'
import * as ReactRedux from 'react-redux'
import {AppStore} from '../Store'
import {createGroupTreeFilterUpdateAction} from '../Actions'

type OwnProps = {}

type StateProps = {
  filterText: string
}

type DispatchProps = {
  updateFilterText: (filterText: string) => void
}

type AllProps = StateProps & DispatchProps & OwnProps

/** Maps Store state to component props */
const mapStateToProps = (store: AppStore): StateProps => {
  return {
    filterText: store.groupTree.filterText
  }
}

// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch): DispatchProps {
  return {
    updateFilterText: (filterText) => dispatch(createGroupTreeFilterUpdateAction(filterText))
  }
}

class GroupTreeFilterPanelImpl extends React.Component<AllProps, {}> {
  public refs: {
    inputElement: HTMLInputElement;
  }

  render() {
    return (
      <div className="search-wrapper">
        <form onReset={this.handleReset.bind(this)}>
          <input ref="inputElement"
                 onChange={this.handleTextChange.bind(this)}
                 type="text" name="focus"
                 className="search-box"
                 placeholder="TODO: filter"
                 required />
          <button className="close-icon" type="reset" />
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

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GroupTreeFilterPanel = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeFilterPanelImpl) as React.ComponentClass<OwnProps>

