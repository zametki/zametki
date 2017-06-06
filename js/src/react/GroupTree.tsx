import * as React from "react";
import * as ReactDOM from "react-dom";
import * as Redux from "redux";
import * as ReactRedux from "react-redux";
import GroupTreeNode = Store.GroupTreeNode;

export namespace Store {

    export type GroupTreeNode = {
        name: string,
        children?: Array<GroupTreeNode>
    }

    export type Counter = { value: number }

    export type Root = {
        counterData: Counter
        groupTreeRoot?: GroupTreeNode
    }
}

const initialCounterState: Store.Counter = {value: 0}
const initialGroupTreeState: Store.GroupTreeNode = null

const initialRootState: Store.Root = {
    counterData: initialCounterState,
    groupTreeRoot: initialGroupTreeState
}

type OwnProps = {}

type ConnectedState = {
    counter: Store.Counter,
    groupTreeRoot: Store.GroupTreeNode
}

type ConnectedDispatch = {
    increment: (n: number) => void,
    reset: () => void,
    updateTree: (groupTreeRoot: Store.GroupTreeNode) => void
}

export interface Action<T> extends Redux.Action {
    payload: T
}

export function isAction<T>(action: Action<any>, actionName: string): action is Action<T> {
    return action && action.type && action.type == actionName;
}

const ActionType_Increment = "IncrementAction";
type IncrementActionPayload = { delta: number; }

const ActionType_Reset = "ResetAction";
const ActionType_UpdateTree = "UpdateTreeAction";
type UpdateTreeActionPayload = { groupTreeRoot: Store.GroupTreeNode; }

export const createIncrementCounterAction = (delta: number): Action<IncrementActionPayload> => ({type: ActionType_Increment, payload: {delta},})
export const createResetCounterAction = (): Action<object> => ({type: ActionType_Reset, payload: {}});
export const createUpdateTreeAction = (groupTreeRoot: GroupTreeNode): Action<UpdateTreeActionPayload> => ({type: ActionType_UpdateTree, payload: {groupTreeRoot}});

function handleCounterActions(state: Store.Counter = initialCounterState, action: Action<any>): Store.Counter {
    if (isAction<IncrementActionPayload>(action, ActionType_Increment)) {
        return {value: state.value + action.payload.delta}
    } else if (isAction<void>(action, ActionType_Reset)) {
        return initialCounterState
    }
    return state;
}

function handleGroupActions(state: Store.GroupTreeNode = initialGroupTreeState, action: Action<any>): Store.GroupTreeNode {
    if (isAction<UpdateTreeActionPayload>(action, ActionType_UpdateTree)) {
        return action.payload.groupTreeRoot
    }
    return state;
}

export const reducers = Redux.combineReducers<Store.Root>({
    counterData: handleCounterActions,
    groupTreeRoot: handleGroupActions
})


const mapStateToProps = (storeState: Store.Root, ownProps: OwnProps): ConnectedState => ({
    counter: storeState.counterData,
    groupTreeRoot: storeState.groupTreeRoot,
})

function mapDispatchToProps(dispatch): ConnectedDispatch {
    return {
        increment: n => dispatch(createIncrementCounterAction(n)),
        reset: () => dispatch(createResetCounterAction()),
        updateTree: groupTreeRoot => dispatch(createUpdateTreeAction(groupTreeRoot))
    }
}

export const storeInstance: Redux.Store<Store.Root> = Redux.createStore(
    reducers,
    {} as Store.Root,
    window["__REDUX_DEVTOOLS_EXTENSION__"] && window["__REDUX_DEVTOOLS_EXTENSION__"]()
    // Redux.applyMiddleware(thunk),
)

export class GroupTree extends React.Component<ConnectedState & ConnectedDispatch & OwnProps, {}> {

    constructor(props: ConnectedState & ConnectedDispatch & OwnProps, context: any) {
        //noinspection TypeScriptValidateTypes
        super(props, context);
        this.onClickIncrement = this.onClickIncrement.bind(this)
        this.onClickReset = this.onClickReset.bind(this)
    }

    render() {
        console.log("RENDER!");
        const props = this.props as ConnectedState & ConnectedDispatch & OwnProps;
        return (
            <div>
                <strong>{props.counter.value}</strong>
                <button onClick={this.onClickIncrement}>increment</button>
                <button onClick={this.onClickReset}>reset</button>
                <div>Child count: {props.groupTreeRoot && props.groupTreeRoot.children ? props.groupTreeRoot.children.length : "?"}</div>
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
        ReactDOM.render(
            <ReactRedux.Provider store={storeInstance}>
                <GT />
            </ReactRedux.Provider>,
            document.getElementById(id)
        );
    }
}

// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
export const GT: React.ComponentClass<OwnProps> = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTree) as React.ComponentClass<OwnProps>;
