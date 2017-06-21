import * as store from 'store'

const APP = 'Z'

function getNodeExpandedKey(nodeId: number) {
  return `${APP}-GT-n-${nodeId}.expanded`
}

/** Wrapper over localStorage */

export class ClientStorage {

  public static isNodeExpanded(nodeId: number): boolean {
    return !!nodeId && store.get(getNodeExpandedKey(nodeId)) === true
  }

  public static setNodeExpanded(nodeId: number, v: boolean): void {
    const key = getNodeExpandedKey(nodeId)
    v ? store.set(key, true) : store.remove(key)
  }

}
