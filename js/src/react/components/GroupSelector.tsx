import * as React from 'react'
import * as ReactRedux from 'react-redux'
import * as Select from 'react-select'

type OwnProps = {}

type StateProps = {}

class GroupSelector extends React.Component<OwnProps & StateProps, any> {
    state: {
        selectedNodeId: number
    }

    render() {
        const options: Array<Select.Option> = []
        options.push({value: 1, label: 'One'})
        options.push({value: 2, label: 'Two'})

        return <Select value={this.state && this.state.selectedNodeId}
                       options={options} menuContainerStyle={{'zIndex': 999}}
                       onChange={this.onChange.bind(this)}/>
    }

    onChange(id: number) {
        this.setState({selectedNodeId: id})
    }
}


export default ReactRedux.connect(null, null)(GroupSelector) as React.ComponentClass<OwnProps>