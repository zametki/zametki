import {GroupTree} from "../react/GroupTree";

function renderGroupTree(id: string): void {
    GroupTree.wrap(id);
}

export default {
    renderGroupTree: renderGroupTree
}