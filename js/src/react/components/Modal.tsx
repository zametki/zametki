import * as React from 'react'

type OwnProps = {
    show: boolean,
    close: () => void
}

export default class Modal extends React.Component<OwnProps, {}> {

    constructor(props: OwnProps, ctx: any) {
        // noinspection TypeScriptValidateTypes
        super(props, ctx)

        this.handleKeyDown = this.handleKeyDown.bind(this)
    }

    hideOnOuterClick(e: React.SyntheticEvent<HTMLElement>) {
        if ((e.target as HTMLElement).dataset.modal) {
            this.props.close()
        }
    }

    handleKeyDown(e: KeyboardEvent) {
        if (e.keyCode === 27) {
            this.props.close()
        }
    }

    componentDidMount() {
        document.addEventListener('keydown', this.handleKeyDown)
    }

    componentWillUnmount() {
        document.removeEventListener('keydown', this.handleKeyDown)
    }

    render() {
        if (!this.props.show) {
            return null
        }
        return (
            <div className="z-modal-overlay" onClick={this.hideOnOuterClick.bind(this)} data-modal="true">
                <div className="z-modal">
                    {this.props.children}
                </div>
            </div>
        )
    }
}
