import * as React from "react";
import * as ReactDOM from "react-dom";
import * as Redux from "redux";
import * as ReactRedux from "react-redux";

export namespace Store {
    export type Counter = { value: number }
    export type All = { counterData: Counter }
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

export interface Action<T> {
    type: string;
    payload: T
}

export function isAction<T>(action: Action<any>, actionName: string): action is Action<T> {
    return action && action.type && action.type == actionName;
}

const ActionType_Increment = "IncrementAction";
type IncrementActionPayload = { delta: number; }

const ActionType_Reset = "ResetAction";
type ResetActionPayload = {}

export const createIncrementCounterAction = (delta: number): Action<IncrementActionPayload> => ({
    type: ActionType_Increment,
    payload: {delta: delta},
})

export const createResetCounterAction = (): Action<ResetActionPayload> => ({
    type: ActionType_Reset,
    payload: {}
})


function handleActions(state: Store.Counter = initialState, action: Action<any>): Store.Counter {
    if (isAction<IncrementActionPayload>(action, ActionType_Increment)) {
        return {value: state.value + action.payload.delta}
    } else if (isAction<ResetActionPayload>(action, ActionType_Reset)) {
        return initialState
    }
    return state;
}

export const reducers = Redux.combineReducers<Store.All>({
    counterData: handleActions
})


const mapStateToProps = (state: Store.All, ownProps: OwnProps): ConnectedState => ({
    counter: state.counterData
})

//noinspection TypeScriptValidateTypes
const mapDispatchToProps = (dispatch: Redux.Dispatch<Store.All>): ConnectedDispatch => ({
    increment: (n: number) => dispatch(createIncrementCounterAction(n)),
    reset: () => dispatch(createResetCounterAction()),

})

export class GroupTree extends React.Component<ConnectedState & ConnectedDispatch & OwnProps, {}> {

    constructor(props: ConnectedState & ConnectedDispatch & OwnProps, context: any) {
        //noinspection TypeScriptValidateTypes
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
        const store: Redux.Store<Store.All> = Redux.createStore(
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
