import * as React from 'react'
import * as ReactRedux from 'react-redux'
import ReactSelect, {Option} from 'react-select'
import {AppStore, GROUP_TREE_ROOT_NODE_ID, GroupTree} from '../Store'

type OwnProps = {
    onChange: (id: number) => void

    selectedGroupId?: number

    /** Subtree to exclude. If <=0 -> not used */
    groupToExclude?: number

    autofocus?: boolean,

    hideRoot?: boolean
}

type StateProps = {
    groupTree: GroupTree
}

interface GroupOption extends Option {
    depth: number
}

type State = {
    selectedGroupId: number
}

class GroupSelector extends React.Component<OwnProps & StateProps, State> {

    render() {
        const options: Array<GroupOption> = []
        if (!this.props.hideRoot) {
            options.push({value: GROUP_TREE_ROOT_NODE_ID, label: "«без группы»", depth: 0})
        }
        const startDepth = this.props.hideRoot ? 0 : 1
        this.props.groupTree.nodeIds
            .filter(id => this.props.groupTree.nodeById[id].parentId === GROUP_TREE_ROOT_NODE_ID)
            .forEach(id => this.flattenGroupTree(id, options, startDepth))

        return <ReactSelect value={(this.state || this.props).selectedGroupId}
                            options={options}
                            onChange={this.onChange.bind(this)}
                            optionRenderer={GroupSelector.renderOption}
                            placeholder="Выбор группы"
                            autofocus={this.props.autofocus}/>
    }

    private onChange(option: GroupOption) {
        const selectedGroupId = option && option.value as number
        this.setState({selectedGroupId})
        this.props.onChange(selectedGroupId)
    }

    private flattenGroupTree(nodeId: number, result: Array<GroupOption>, depth: number) {
        const node = this.props.groupTree.nodeById[nodeId]
        if (!node || nodeId === this.props.groupToExclude) {
            return
        }

        result.push({value: nodeId, label: node.name, depth})
        node.children.map(id => this.flattenGroupTree(id, result, depth + 1))
    }

    private static renderOption(option: GroupOption): JSX.Element {
        const style: React.CSSProperties = {
            paddingLeft: (option.depth * 15) + "px"
        }
        return <div style={style}>{option.label}</div>
    }
}


/** Maps Store state to component props */
const mapStateToProps = (store: AppStore): StateProps => {
    return {groupTree: store.groupTree}
}


export default ReactRedux.connect(mapStateToProps, null)(GroupSelector) as React.ComponentClass<OwnProps>