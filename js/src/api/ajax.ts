type CallbacksMap = {
    toggleGroupExpandedState(groupId: number, expanded: boolean)
}

const callbacks: CallbacksMap = {
    toggleGroupExpandedState: undefined
}

export function toggleGroupExpandedState(groupId: number, expanded: boolean) {
    callbacks.toggleGroupExpandedState(groupId, expanded);
}

export default {
    callbacks
}