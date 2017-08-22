import * as store from 'store'

const APP = 'Z'

const getGroupTreeFilterKey = () => `${APP}-GT-filter`
const getLastUsedGroupKey = () => `${APP}-groupId`
const getNodeExpandedKey = (nodeId: number) => `${APP}-GT-n-${nodeId}.expanded`

function update(key: string, value: any) {
    value ? store.set(key, value) : store.remove(key)
}

/** Wrapper over localStorage */
export class ClientStorage {

    public static isNodeExpanded(nodeId: number): boolean {
        return !!nodeId && store.get(getNodeExpandedKey(nodeId)) === true
    }

    public static setNodeExpanded(nodeId: number, value: boolean): void {
        update(getNodeExpandedKey(nodeId), value)
    }

    static setGroupFilterText(value: string) {
        update(getGroupTreeFilterKey(), value)
    }

    static getGroupFilterText() {
        return store.get(getGroupTreeFilterKey()) || ''
    }

    static setLastUsedGroupId(groupId: number) {
        return store.set(getLastUsedGroupKey(), groupId)
    }

    static getLastUsedGroupId() {
        return store.get(getLastUsedGroupKey())
    }
}
