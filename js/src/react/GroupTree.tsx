import * as React from "react";
import * as ReactDOM from "react-dom";
import * as Redux from "redux";
import * as ReactRedux from "react-redux";

export namespace Store {
    export type Counter = { value: number }
    export type All = { counter: Counter }
}

const initialState: Store.Counter = {value: 0}

type OwnProps = {}

type ConnectedState = {
    counter: { value: number }
}

type ConnectedDispatch = {
    increment: (n: number) => void
    reset: () => void
}

export type Action =
    { type: "INCREMENT_COUNTER", delta: number } |
    { type: "RESET_COUNTER" }


export const incrementCounter = (delta: number): Action => ({
    type: "INCREMENT_COUNTER",
    delta,
})

export const resetCounter = (): Action => ({
    type: "RESET_COUNTER"
})


function counter(state: Store.Counter = initialState, action: Action): Store.Counter {
    switch (action.type) {
        case "INCREMENT_COUNTER":
            return {value: state.value + action.delta}
        case "RESET_COUNTER":
            return {value: 0}
        default:
            return state
    }
}

export const reducers = Redux.combineReducers<Store.All>({
    counter
})


const mapStateToProps = (state: Store.All, ownProps: OwnProps): ConnectedState => ({
    counter: state.counter
})

//noinspection TypeScriptValidateTypes
const mapDispatchToProps = (dispatch: Redux.Dispatch<Store.All>): ConnectedDispatch => ({
    increment: (n: number) => dispatch(incrementCounter(n)),
    reset: () => dispatch(resetCounter()),

})

export class GroupTree extends React.Component<ConnectedState & ConnectedDispatch & OwnProps, {}> {

    constructor(props: ConnectedState & ConnectedDispatch & OwnProps, context: any) {
        super(props, context);
        this.onClickIncrement = this.onClickIncrement.bind(this)
        this.onClickReset = this.onClickReset.bind(this)
    }

    render() {
        const props = this.props as ConnectedState & ConnectedDispatch & OwnProps;
        return (
            <div>
                <strong>{props.counter.value}</strong>
                <button ref='increment' onClick={this.onClickIncrement}>increment</button>
                <button ref='increment' onClick={this.onClickReset}>reset</button>
            </div>
        )
    }

    private onClickIncrement(e: React.SyntheticEvent<HTMLButtonElement>) {
        const props = this.props as ConnectedState & ConnectedDispatch & OwnProps;
        e.preventDefault();
        props.increment(1);
    }

    private onClickReset(e: React.SyntheticEvent<HTMLButtonElement>) {
        const props = this.props as ConnectedState & ConnectedDispatch & OwnProps;
        e.preventDefault();
        props.reset();
    }

    static wrap(id: string) {
        let store: Redux.Store<Store.All> = Redux.createStore(
            reducers,
            {} as Store.All,
            // Redux.applyMiddleware(thunk),
        )

        ReactDOM.render(
            <ReactRedux.Provider store={store}>
                <GT />
            </ReactRedux.Provider>,
            document.getElementById(id)
        );
    }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GT: React.ComponentClass<OwnProps> = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTree) as React.ComponentClass<OwnProps>;
