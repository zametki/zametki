/** Set of callbacks for Wicket */
type ServerInterface = {
    activateGroup(groupId: number),
    createGroup(parentGroupId: number, name: string)
    renameGroup(groupId: number, name: string)
    moveGroup(groupId: number, parentGroupId: number)
    deleteGroup(groupId: number)
}

const Server: ServerInterface = {
    activateGroup: undefined,
    createGroup: undefined,
    renameGroup: undefined,
    moveGroup: undefined,
    deleteGroup: undefined
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

export function moveGroup(groupId: number, parentGroupId: number) {
    Server.moveGroup(groupId, parentGroupId)
}

export function deleteGroup(groupId: number) {
    Server.deleteGroup(groupId)
}

export default Server
