/** Set of callbacks for Wicket */
type ServerInterface = {
    activateGroup(groupId: number),
    createGroup(parentGroupId: number, name: string)
    renameGroup(groupId: number, name: string)
}

const Server: ServerInterface = {
    activateGroup: undefined,
    createGroup: undefined,
    renameGroup: undefined
}

export function activateGroup(groupId: number) {
    Server.activateGroup(groupId)
}

export function createGroup(parentGroupId: number, name: string) {
    Server.createGroup(parentGroupId, name)
}

export function renameGroup(groupId: number, name: string) {
    Server.renameGroup(groupId, name)
}

export default Server
