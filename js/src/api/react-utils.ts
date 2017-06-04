import * as React from "react";
import * as ReactDOM from "react-dom";
import {GroupTree} from "../react/GroupTree";

function renderGroupTree(id: string): void {
    ReactDOM.render(React.createElement(GroupTree), document.getElementById(id));
}

export default {
    renderGroupTree: renderGroupTree
}