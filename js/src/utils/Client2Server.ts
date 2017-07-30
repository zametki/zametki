/** Set of callbacks for Wicket */
type ServerInterface = {
    activateGroup(groupId: number),
    createGroup(parentGroupId: number, name: string)
}

const server: ServerInterface = {
    activateGroup: undefined,
    createGroup: undefined
}

export function activateGroup(groupId: number) {
    server.activateGroup(groupId)
}

export function createGroup(parentGroupId: number, name: string) {
    server.createGroup(parentGroupId, name)
}

export default server
