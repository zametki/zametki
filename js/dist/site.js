/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 8);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
function isAction(action, actionName) {
    return action && action.type && action.type === actionName;
}
exports.isAction = isAction;
var ActionType = (function () {
    function ActionType() {
    }
    ActionType.UpdateGroupTree = 'UpdateTreeAction';
    ActionType.ToggleGroupTreeNode = 'ToggleGroupTreeNode';
    ActionType.ActivateGroupTreeNode = 'ActivateGroupTreeNode';
    ActionType.GroupTreeFilterUpdate = 'GroupTreeFilterUpdate';
    return ActionType;
}());
exports.ActionType = ActionType;
exports.createUpdateGroupTreeAction = function (nodes) { return ({ type: ActionType.UpdateGroupTree, payload: { nodes: nodes } }); };
exports.createToggleGroupTreeNodeAction = function (nodeId, expanded) { return ({ type: ActionType.ToggleGroupTreeNode, payload: { nodeId: nodeId, expanded: expanded } }); };
exports.createActivateGroupTreeNodeAction = function (nodeId) { return ({ type: ActionType.ActivateGroupTreeNode, payload: { nodeId: nodeId } }); };
exports.createGroupTreeFilterUpdateAction = function (filterText) { return ({ type: ActionType.GroupTreeFilterUpdate, payload: { filterText: filterText } }); };


/***/ }),
/* 1 */
/***/ (function(module, exports) {

module.exports = React;

/***/ }),
/* 2 */
/***/ (function(module, exports) {

module.exports = ReactRedux;

/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var Redux = __webpack_require__(4);
var Reducers_1 = __webpack_require__(12);
var ClientStorage_1 = __webpack_require__(5);
exports.GROUP_TREE_ROOT_NODE_ID = 0;
exports.defaultStoreInstance = {
    groupTree: {
        nodeById: {},
        nodeIds: [],
        filterText: ClientStorage_1.ClientStorage.getGroupFilterText()
    }
};
exports.appStore = window['appStore'] = Redux.createStore(Reducers_1.AppReducers, exports.defaultStoreInstance, window['__REDUX_DEVTOOLS_EXTENSION__'] && window['__REDUX_DEVTOOLS_EXTENSION__']()
// Redux.applyMiddleware(thunk),
);


/***/ }),
/* 4 */
/***/ (function(module, exports) {

module.exports = Redux;

/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var store = __webpack_require__(13);
var APP = 'Z';
function getGroupTreeFilterKey() {
    return APP + "-GT-filter";
}
function getNodeExpandedKey(nodeId) {
    return APP + "-GT-n-" + nodeId + ".expanded";
}
function update(key, value) {
    value ? store.set(key, value) : store.remove(key);
}
/** Wrapper over localStorage */
var ClientStorage = (function () {
    function ClientStorage() {
    }
    ClientStorage.isNodeExpanded = function (nodeId) {
        return !!nodeId && store.get(getNodeExpandedKey(nodeId)) === true;
    };
    ClientStorage.setNodeExpanded = function (nodeId, value) {
        update(getNodeExpandedKey(nodeId), value);
    };
    ClientStorage.setGroupFilterText = function (value) {
        update(getGroupTreeFilterKey(), value);
    };
    ClientStorage.getGroupFilterText = function () {
        return store.get(getGroupTreeFilterKey()) || '';
    };
    return ClientStorage;
}());
exports.ClientStorage = ClientStorage;


/***/ }),
/* 6 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var callbacks = {
    activateGroup: undefined
};
function activateGroup(groupId) {
    callbacks.activateGroup(groupId);
}
exports.activateGroup = activateGroup;
exports["default"] = {
    callbacks: callbacks
};


/***/ }),
/* 7 */
/***/ (function(module, exports) {

module.exports = $;

/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var SiteDef_1 = __webpack_require__(9);
__webpack_require__(10);
var Server2Client_1 = __webpack_require__(11);
var SiteUtils_1 = __webpack_require__(19);
var Shortcuts_1 = __webpack_require__(20);
var Client2Server_1 = __webpack_require__(6);
__webpack_require__(21);
SiteDef_1["default"].Server2Client = Server2Client_1["default"];
SiteDef_1["default"].Utils = SiteUtils_1["default"];
SiteDef_1["default"].Shortcuts = Shortcuts_1["default"];
SiteDef_1["default"].Ajax = Client2Server_1["default"];
window.$site = SiteDef_1["default"];


/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
exports["default"] = {
    /** React helpers */
    Server2Client: undefined,
    /** Set of utility functions */
    Utils: undefined,
    /** Key bindings support */
    Shortcuts: undefined,
    /** Ajax calls to Wicket backend */
    Ajax: undefined
};


/***/ }),
/* 10 */
/***/ (function(module, exports) {

if (window.Parsley) {
    //noinspection SpellCheckingInspection
    window.Parsley.addMessages("ru", {
        defaultMessage: "Некорректное значение.",
        type: {
            email: "Введите адрес электронной почты.",
            url: "Введите URL адрес.",
            number: "Введите число.",
            integer: "Введите целое число.",
            digits: "Введите только цифры.",
            alphanum: "Введите буквенно-цифровое значение."
        },
        notblank: "Это поле должно быть заполнено.",
        required: "Обязательное поле.",
        pattern: "Это значение некорректно.",
        min: "Это значение должно быть не менее чем %s.",
        max: "Это значение должно быть не более чем %s.",
        range: "Это значение должно быть от %s до %s.",
        minlength: "Это значение должно содержать не менее %s символов.",
        maxlength: "Это значение должно содержать не более %s символов.",
        length: "Это значение должно содержать от %s до %s символов.",
        mincheck: "Выберите не менее %s значений.",
        maxcheck: "Выберите не более %s значений.",
        check: "Выберите от %s до %s значений.",
        equalto: "Это значение должно совпадать."
    });
    window.Parsley.setLocale("ru");
}


/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
/**
 * Interface used by server code to trigger client actions.
 */
var Store_1 = __webpack_require__(3);
var GroupTreeView_1 = __webpack_require__(14);
var Actions_1 = __webpack_require__(0);
function renderGroupTree(elementId) {
    GroupTreeView_1.renderGroupTreeView(elementId);
}
function dispatchUpdateGroupTreeAction(rootNode) {
    //noinspection TypeScriptValidateTypes
    Store_1.appStore.dispatch(Actions_1.createUpdateGroupTreeAction(rootNode));
}
function dispatchActivateGroupNodeAction(nodeId) {
    //noinspection TypeScriptValidateTypes
    Store_1.appStore.dispatch(Actions_1.createActivateGroupTreeNodeAction(nodeId));
}
exports["default"] = {
    renderGroupTreeView: GroupTreeView_1.renderGroupTreeView,
    dispatchUpdateGroupTreeAction: dispatchUpdateGroupTreeAction,
    dispatchActivateGroupNodeAction: dispatchActivateGroupNodeAction
};


/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __assign = (this && this.__assign) || Object.assign || function(t) {
    for (var s, i = 1, n = arguments.length; i < n; i++) {
        s = arguments[i];
        for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
            t[p] = s[p];
    }
    return t;
};
exports.__esModule = true;
var Redux = __webpack_require__(4);
var Actions_1 = __webpack_require__(0);
var ClientStorage_1 = __webpack_require__(5);
var defaultStoreInstance = {
    groupTree: {
        nodeById: {},
        nodeIds: [],
        filterText: ClientStorage_1.ClientStorage.getGroupFilterText()
    }
};
/** Group Tree reducer */
function groupTree(state, action) {
    if (state === void 0) { state = defaultStoreInstance.groupTree; }
    if (Actions_1.isAction(action, Actions_1.ActionType.UpdateGroupTree)) {
        return updateGroupTree(state, action.payload);
    }
    else if (Actions_1.isAction(action, Actions_1.ActionType.ToggleGroupTreeNode)) {
        return toggleGroupTreeNode(state, action.payload);
    }
    else if (Actions_1.isAction(action, Actions_1.ActionType.ActivateGroupTreeNode)) {
        return activateGroupTreeNode(state, action.payload);
    }
    else if (Actions_1.isAction(action, Actions_1.ActionType.GroupTreeFilterUpdate)) {
        return updateGroupTreeFilter(state, action.payload);
    }
    return state;
}
exports.AppReducers = Redux.combineReducers({ groupTree: groupTree });
function updateGroupTree(state, payload) {
    var nodeById = {};
    var nodeIds = [];
    payload.nodes.map(function (n) {
        var old = state.nodeById[n.id];
        n.expanded = !!old ? old.expanded : ClientStorage_1.ClientStorage.isNodeExpanded(n.id);
        nodeById[n.id] = n;
        nodeIds.push(n.id);
    });
    return __assign({}, state, { nodeById: nodeById, nodeIds: nodeIds });
}
function toggleGroupTreeNode(state, payload) {
    var nodeById = state.nodeById;
    var n = nodeById[payload.nodeId];
    if (n) {
        n.expanded = payload.expanded;
        ClientStorage_1.ClientStorage.setNodeExpanded(n.id, n.expanded);
    }
    return __assign({}, state, { nodeById: nodeById });
}
function activateGroupTreeNode(state, payload) {
    var nodeById = state.nodeById;
    // deactivate all other nodes.
    for (var _i = 0, _a = state.nodeIds; _i < _a.length; _i++) {
        var id = _a[_i];
        nodeById[id].active = false;
    }
    // activate new node.
    var n = nodeById[payload.nodeId];
    if (!n) {
        return;
    }
    n.active = true;
    // expand all parent nodes to make new node visible.
    var parentNode = nodeById[n.parentId];
    while (parentNode) {
        if (!parentNode.expanded) {
            parentNode.expanded = true;
            ClientStorage_1.ClientStorage.setNodeExpanded(parentNode.id, true);
        }
        parentNode = nodeById[parentNode.parentId];
    }
    return __assign({}, state, { nodeById: nodeById });
}
function updateGroupTreeFilter(state, payload) {
    ClientStorage_1.ClientStorage.setGroupFilterText(payload.filterText);
    return __assign({}, state, { filterText: payload.filterText });
}


/***/ }),
/* 13 */
/***/ (function(module, exports) {

module.exports = store;

/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
exports.__esModule = true;
var React = __webpack_require__(1);
var react_dom_1 = __webpack_require__(15);
var ReactRedux = __webpack_require__(2);
var Store_1 = __webpack_require__(3);
var GroupTreeNodePanel_1 = __webpack_require__(16);
var GroupTreeFilterPanel_1 = __webpack_require__(18);
var mapStateToProps = function (store) {
    var topLevelGroupIds = store.groupTree.nodeIds
        .filter(function (id) { return store.groupTree.nodeById[id].parentId === Store_1.GROUP_TREE_ROOT_NODE_ID; });
    return { topLevelGroupIds: topLevelGroupIds };
};
var GroupTreeViewImpl = (function (_super) {
    __extends(GroupTreeViewImpl, _super);
    function GroupTreeViewImpl() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    GroupTreeViewImpl.prototype.render = function () {
        var treeNodes = this.props.topLevelGroupIds.map(function (id) { return React.createElement(GroupTreeNodePanel_1.GroupTreeNodePanel, { nodeId: id, key: 'node-' + id }); });
        return (React.createElement("div", null,
            React.createElement(GroupTreeFilterPanel_1.GroupTreeFilterPanel, null),
            treeNodes));
    };
    return GroupTreeViewImpl;
}(React.Component));
exports.GroupTreeViewImpl = GroupTreeViewImpl;
// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
exports.GroupTreeView = ReactRedux.connect(mapStateToProps, null)(GroupTreeViewImpl);
function renderGroupTreeView(elementId) {
    react_dom_1.render(React.createElement(ReactRedux.Provider, { store: Store_1.appStore },
        React.createElement("div", null,
            React.createElement(exports.GroupTreeView, null))), document.getElementById(elementId));
}
exports.renderGroupTreeView = renderGroupTreeView;


/***/ }),
/* 15 */
/***/ (function(module, exports) {

module.exports = ReactDOM;

/***/ }),
/* 16 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
exports.__esModule = true;
var React = __webpack_require__(1);
var ReactRedux = __webpack_require__(2);
var Client2Server_1 = __webpack_require__(6);
var Actions_1 = __webpack_require__(0);
var GroupTreeCountsBadge_1 = __webpack_require__(17);
/** Maps Store state to component props */
var mapStateToProps = function (store, ownProps) {
    var node = store.groupTree.nodeById[ownProps.nodeId];
    return {
        name: node.name,
        subGroups: node.children,
        active: node.active,
        expanded: node.expanded,
        level: node.level,
        filterText: store.groupTree.filterText
    };
};
// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch) {
    return {
        toggleExpandedState: function (nodeId, expanded) { return dispatch(Actions_1.createToggleGroupTreeNodeAction(nodeId, expanded)); }
    };
}
var GroupTreeNodePanelImpl = (function (_super) {
    __extends(GroupTreeNodePanelImpl, _super);
    function GroupTreeNodePanelImpl(props, context) {
        var _this = 
        //noinspection TypeScriptValidateTypes
        _super.call(this, props, context) || this;
        _this.onToggleExpandedState = _this.onToggleExpandedState.bind(_this);
        _this.activateGroup = _this.activateGroup.bind(_this);
        return _this;
    }
    GroupTreeNodePanelImpl.prototype.render = function () {
        var _a = this.props, nodeId = _a.nodeId, name = _a.name, subGroups = _a.subGroups, active = _a.active, expanded = _a.expanded, level = _a.level, filterText = _a.filterText;
        if (!name) {
            console.error("Node not found: " + this.props);
            return null;
        }
        var filteredMode = filterText.length > 0;
        var node = filteredMode && name.indexOf(filterText) == -1 ? null :
            (React.createElement("div", { className: 'tree-node' + (active ? ' tree-node-active' : '') },
                React.createElement("div", { style: { paddingLeft: (filteredMode ? 0 : level) * 16 } },
                    React.createElement("table", { className: 'w100' },
                        React.createElement("tbody", null,
                            React.createElement("tr", null,
                                React.createElement("td", { className: 'tree-junction-td' },
                                    React.createElement("a", { className: 'tree-junction' + (subGroups && subGroups.length > 0 && !filteredMode ? (expanded ? ' tree-junction-expanded' : ' tree-junction-collapsed') : ''), onClick: this.onToggleExpandedState })),
                                React.createElement("td", null,
                                    React.createElement("div", { className: 'tree-content' },
                                        React.createElement("a", { className: 'tree-node-group-link', onClick: this.activateGroup },
                                            React.createElement(GroupTreeCountsBadge_1.GroupTreeCountsBadge, { nodeId: nodeId }),
                                            React.createElement("span", null, name))))))))));
        return (React.createElement("div", null,
            node,
            (expanded || filteredMode) && subGroups && subGroups.map(function (childId) { return React.createElement(exports.GroupTreeNodePanel, { nodeId: childId, key: 'node-' + childId }); })));
    };
    GroupTreeNodePanelImpl.prototype.onToggleExpandedState = function () {
        this.props.toggleExpandedState(this.props.nodeId, !this.props.expanded);
    };
    GroupTreeNodePanelImpl.prototype.activateGroup = function () {
        Client2Server_1.activateGroup(this.props.nodeId);
    };
    return GroupTreeNodePanelImpl;
}(React.Component));
// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
exports.GroupTreeNodePanel = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeNodePanelImpl);


/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
exports.__esModule = true;
var React = __webpack_require__(1);
var ReactRedux = __webpack_require__(2);
/** Maps Store state to component props */
var mapStateToProps = function (store, ownProps) {
    var node = store.groupTree.nodeById[ownProps.nodeId];
    return {
        entriesCount: node.entriesCount
    };
};
var GroupTreeCountsBadgeImpl = (function (_super) {
    __extends(GroupTreeCountsBadgeImpl, _super);
    function GroupTreeCountsBadgeImpl() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    GroupTreeCountsBadgeImpl.prototype.shouldComponentUpdate = function (nextProps) {
        return nextProps.entriesCount !== this.props.entriesCount;
    };
    GroupTreeCountsBadgeImpl.prototype.render = function () {
        var entriesCount = this.props.entriesCount;
        if (entriesCount <= 0) {
            return null;
        }
        return (React.createElement("div", { className: 'badge zametka-count-badge float-right ml-1' }, entriesCount));
    };
    return GroupTreeCountsBadgeImpl;
}(React.Component));
// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
exports.GroupTreeCountsBadge = ReactRedux.connect(mapStateToProps, null)(GroupTreeCountsBadgeImpl);


/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
exports.__esModule = true;
var React = __webpack_require__(1);
var ReactRedux = __webpack_require__(2);
var Actions_1 = __webpack_require__(0);
/** Maps Store state to component props */
var mapStateToProps = function (store) {
    return {
        filterText: store.groupTree.filterText
    };
};
// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch) {
    return {
        updateFilterText: function (filterText) { return dispatch(Actions_1.createGroupTreeFilterUpdateAction(filterText)); }
    };
}
var GroupTreeFilterPanelImpl = (function (_super) {
    __extends(GroupTreeFilterPanelImpl, _super);
    function GroupTreeFilterPanelImpl() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    GroupTreeFilterPanelImpl.prototype.render = function () {
        var filterText = this.props.filterText;
        var resetIsOn = filterText.length > 0;
        var resetButtonClassName = "gt-search-reset-button" + (resetIsOn ? ' gt-search-reset-button-on' : '');
        return (React.createElement("div", { className: "gt-search-block" },
            React.createElement("form", { onReset: this.handleReset.bind(this), onSubmit: function (e) { return e.preventDefault(); } },
                React.createElement("input", { ref: "inputElement", onChange: this.handleTextChange.bind(this), type: "search", className: "gt-search-input", placeholder: " …", value: filterText, title: "Фильтр по имени группы" }),
                React.createElement("button", { className: resetButtonClassName, type: "reset", title: "Сбросить фильтр" }, "\u00D7"))));
    };
    GroupTreeFilterPanelImpl.prototype.handleReset = function () {
        this.props.updateFilterText('');
    };
    GroupTreeFilterPanelImpl.prototype.handleTextChange = function () {
        this.props.updateFilterText(this.refs.inputElement.value);
    };
    return GroupTreeFilterPanelImpl;
}(React.Component));
// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
exports.GroupTreeFilterPanel = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeFilterPanelImpl);


/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var $ = __webpack_require__(7);
function setTitle(selector, title, root) {
    root = root ? root : window.document.body;
    $(root).find(selector).each(function () {
        if (!$(this).attr('title')) {
            $(this).attr('title', title);
        }
    });
}
function focusOnEnter(event, id) {
    if (event.which === 13) {
        $(id).focus();
        event.preventDefault();
    }
}
function clickOnEnter(event, id) {
    var keyCode = (event.which ? event.which : event.keyCode);
    if ((keyCode === 10 || keyCode === 13) && !event.ctrlKey) {
        $(id).click();
        event.preventDefault();
    }
}
function clickOnCtrlEnter(event, id) {
    var keyCode = (event.which ? event.which : event.keyCode);
    if ((keyCode === 10 || keyCode === 13) && event.ctrlKey) {
        $(id).click();
        event.preventDefault();
    }
}
function showMenuByClick(e, id) {
    var evt = e ? e : window.event;
    if (evt && evt.stopPropagation) {
        evt.stopPropagation();
    }
    if (evt && evt.cancelBubble) {
        evt.cancelBubble = true;
    }
    $('#' + id).dropdown('toggle');
    return false;
}
function getURLParameter(name) {
    var regExp = new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)');
    return decodeURIComponent((regExp.exec(location.search) || [undefined, ''])[1].replace(/\+/g, '%20')) || undefined;
}
function limitTextArea(textAreaId, $feedback, $button, maxTextLen, minRemainingToShow) {
    var textArea = window.document.getElementById(textAreaId);
    var limitFn = function () {
        var remaining = maxTextLen - textArea.value.length;
        if (remaining <= minRemainingToShow) {
            $feedback.html('' + remaining);
        }
        else {
            $feedback.html('');
        }
        if (remaining < 0) {
            $feedback.css('color', 'red');
            if ($button) {
                $button.attr('disabled', '');
            }
        }
        else {
            $feedback.css('color', 'inherit');
            if ($button) {
                $button.removeAttr('disabled');
            }
        }
    };
    textArea.addEventListener('keyup', limitFn);
    limitFn();
}
function enableScrollTop() {
    $(document).ready(function () {
        var $backTop = $('#back-top');
        if (!$backTop) {
            return;
        }
        $backTop.hide(); // hide #back-top first
        $(function () {
            $(window).scroll(function () {
                if ($(this).scrollTop() > 100) {
                    $('#back-top').fadeIn();
                }
                else {
                    $('#back-top').fadeOut();
                }
            });
            $('#back-top').find('a').click(function () {
                $('body,html').animate({
                    scrollTop: 0
                }, 500);
                return false;
            });
        });
    });
}
function removeServerSideParsleyError(el) {
    var p = $(el).parsley();
    p.removeError('server-side-parsley-error');
}
function scrollToBlock(selector) {
    var $block = $(selector);
    var offset = $block.offset();
    $('html, body').animate({
        scrollTop: offset.top
    });
}
function closeModal(jqSelector) {
    $(jqSelector).closest('.modal').modal('hide');
}
exports["default"] = {
    setTitle: setTitle,
    focusOnEnter: focusOnEnter,
    clickOnEnter: clickOnEnter,
    clickOnCtrlEnter: clickOnCtrlEnter,
    showMenuByClick: showMenuByClick,
    getURLParameter: getURLParameter,
    limitTextArea: limitTextArea,
    enableScrollTop: enableScrollTop,
    removeServerSideParsleyError: removeServerSideParsleyError,
    scrollToBlock: scrollToBlock,
    closeModal: closeModal
};


/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var $ = __webpack_require__(7);
function isABootstrapModalOpen() {
    return $('.modal.show').length > 0;
}
function isInInput(element) {
    if (!element || !element.tagName) {
        return false;
    }
    return element.tagName === 'INPUT' || element.tagName === 'TEXTAREA';
}
function bindWorkspacePageKeys() {
    window.document.addEventListener('keydown', function (event) {
        if (isABootstrapModalOpen()) {
            return;
        }
        var element = event.srcElement;
        if (isInInput(element)) {
            if (event.which === 27) {
                var elementId = element.getAttribute('id');
                if (elementId === 'create-zametka-text-area') {
                    $('#create-zametka-cancel-button').click();
                }
            }
            return;
        }
        if (event.which === 65 || event.which === 97) {
            var $btn = $('#add-zametka-button');
            if ($btn.hasClass('active-create')) {
                return;
            }
            $btn.click();
        }
    });
}
exports["default"] = {
    bindWorkspacePageKeys: bindWorkspacePageKeys
};


/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(22)
__webpack_require__(23)
__webpack_require__(24)
__webpack_require__(25)
__webpack_require__(26)
__webpack_require__(27)
__webpack_require__(28)
__webpack_require__(29)
__webpack_require__(30)
__webpack_require__(31)
__webpack_require__(32)
__webpack_require__(33)
__webpack_require__(34)
__webpack_require__(35)
__webpack_require__(36)
__webpack_require__(37)
__webpack_require__(38)

/***/ }),
/* 22 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 23 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 24 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 25 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 26 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 27 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 28 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 29 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 30 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 31 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 32 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 33 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 34 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 35 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 36 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 37 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 38 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ })
/******/ ]);