type CallbacksMap = {
    activateGroup(groupId: number)
}

const callbacks: CallbacksMap = {
    activateGroup: undefined
}

export function activateGroup(groupId: number) {
    callbacks.activateGroup(groupId)
}

export default {
    callbacks
}
