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
/******/ 	return __webpack_require__(__webpack_require__.s = 7);
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
    return ActionType;
}());
ActionType.UpdateTree = 'UpdateTreeAction';
ActionType.ToggleTreeNode = 'ToggleTreeNode';
exports.ActionType = ActionType;
exports.createUpdateTreeAction = function (nodes) { return ({ type: ActionType.UpdateTree, payload: { nodes: nodes } }); };
exports.createToggleTreeNodeAction = function (nodeId, expanded) { return ({ type: ActionType.ToggleTreeNode, payload: { nodeId: nodeId, expanded: expanded } }); };


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
var Reducers_1 = __webpack_require__(11);
exports.GROUP_TREE_ROOT_NODE_ID = 0;
exports.appStore = Redux.createStore(Reducers_1.AppReducers, {
    groupTree: { nodeById: {} }
}, window['__REDUX_DEVTOOLS_EXTENSION__'] && window['__REDUX_DEVTOOLS_EXTENSION__']()
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
/* 6 */
/***/ (function(module, exports) {

module.exports = $;

/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var site_def_1 = __webpack_require__(8);
__webpack_require__(9);
var react_utils_1 = __webpack_require__(10);
var site_utils_1 = __webpack_require__(18);
var shortcuts_1 = __webpack_require__(19);
var ajax_1 = __webpack_require__(5);
site_def_1["default"].ReactUtils = react_utils_1["default"];
site_def_1["default"].Utils = site_utils_1["default"];
site_def_1["default"].Shortcuts = shortcuts_1["default"];
site_def_1["default"].Ajax = ajax_1["default"];
window.$site = site_def_1["default"];


/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
exports["default"] = {
    /** React helpers */
    ReactUtils: undefined,
    /** Set of utility functions */
    Utils: undefined,
    /** Key bindings support */
    Shortcuts: undefined,
    /** Ajax calls to Wicket backend */
    Ajax: undefined
};


/***/ }),
/* 9 */
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
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var Store_1 = __webpack_require__(3);
var GroupTreeView_1 = __webpack_require__(14);
var Actions_1 = __webpack_require__(0);
function renderGroupTree(elementId) {
    GroupTreeView_1.GroupTreeView.wrap(elementId);
}
function onGroupTreeChanged(rootNode) {
    var updateTreeAction = Actions_1.createUpdateTreeAction(rootNode);
    //noinspection TypeScriptValidateTypes
    Store_1.appStore.dispatch(updateTreeAction);
}
exports["default"] = {
    onGroupTreeChanged: onGroupTreeChanged,
    renderGroupTree: renderGroupTree
};


/***/ }),
/* 11 */
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
var ClientStorage_1 = __webpack_require__(12);
/** Group Tree reducer */
function groupTree(state, action) {
    if (state === void 0) { state = { nodeById: {}, nodeIds: [] }; }
    if (Actions_1.isAction(action, Actions_1.ActionType.UpdateTree)) {
        var nodeById_1 = {};
        var nodeIds_1 = [];
        action.payload.nodes.map(function (n) {
            var old = state.nodeById[n.id];
            n.expanded = !!old ? old.expanded : ClientStorage_1.ClientStorage.isNodeExpanded(n.id);
            nodeById_1[n.id] = n;
            nodeIds_1.push(n.id);
        });
        return { nodeById: nodeById_1, nodeIds: nodeIds_1 };
    }
    else if (Actions_1.isAction(action, Actions_1.ActionType.ToggleTreeNode)) {
        var nodeById = state.nodeById;
        var n = nodeById[action.payload.nodeId];
        if (n) {
            n.expanded = action.payload.expanded;
            ClientStorage_1.ClientStorage.setNodeExpanded(n.id, n.expanded);
        }
        return __assign({}, state, { nodeById: nodeById });
    }
    return state;
}
exports.AppReducers = Redux.combineReducers({ groupTree: groupTree });


/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var store = __webpack_require__(13);
var APP = 'Z';
function getNodeExpandedKey(nodeId) {
    return APP + "-GT-n-" + nodeId + ".expanded";
}
/** Wrapper over localStorage */
var ClientStorage = (function () {
    function ClientStorage() {
    }
    ClientStorage.isNodeExpanded = function (nodeId) {
        return !!nodeId && store.get(getNodeExpandedKey(nodeId)) === true;
    };
    ClientStorage.setNodeExpanded = function (nodeId, v) {
        var key = getNodeExpandedKey(nodeId);
        v ? store.set(key, true) : store.remove(key);
    };
    return ClientStorage;
}());
exports.ClientStorage = ClientStorage;


/***/ }),
/* 13 */
/***/ (function(module, exports) {

module.exports = store;

/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var React = __webpack_require__(1);
var react_dom_1 = __webpack_require__(15);
var react_redux_1 = __webpack_require__(2);
var Store_1 = __webpack_require__(3);
var GroupTreeNodePanel_1 = __webpack_require__(16);
var GroupTreeView = (function () {
    function GroupTreeView() {
    }
    GroupTreeView.wrap = function (elementId) {
        var groupTree = Store_1.appStore.getState().groupTree;
        var nodeById = groupTree.nodeById;
        var treeNodes = groupTree.nodeIds
            .filter(function (id) { return nodeById[id].parentId === Store_1.GROUP_TREE_ROOT_NODE_ID; })
            .map(function (id) { return React.createElement(GroupTreeNodePanel_1.GroupTreeNodePanel, { nodeId: id, key: 'node-' + id }); });
        react_dom_1.render(React.createElement(react_redux_1.Provider, { store: Store_1.appStore },
            React.createElement("div", null, treeNodes)), document.getElementById(elementId));
    };
    return GroupTreeView;
}());
exports.GroupTreeView = GroupTreeView;


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
var ajax_1 = __webpack_require__(5);
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
        level: node.level
    };
};
// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch) {
    return {
        expandNode: function (nodeId, expanded) { return dispatch(Actions_1.createToggleTreeNodeAction(nodeId, expanded)); }
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
        var _a = this.props, nodeId = _a.nodeId, name = _a.name, subGroups = _a.subGroups, active = _a.active, expanded = _a.expanded, level = _a.level;
        if (!name) {
            console.error("Node not found: " + this.props);
            return null;
        }
        return (React.createElement("div", null,
            React.createElement("div", { className: 'tree-node' + (active ? ' tree-node-active' : '') },
                React.createElement("div", { style: { paddingLeft: level * 16 } },
                    React.createElement("table", { className: 'w100' },
                        React.createElement("tbody", null,
                            React.createElement("tr", null,
                                React.createElement("td", { className: 'tree-junction-td' },
                                    React.createElement("a", { className: 'tree-junction' + (subGroups && subGroups.length > 0 ? (expanded ? ' tree-junction-expanded' : ' tree-junction-collapsed') : ''), onClick: this.onToggleExpandedState })),
                                React.createElement("td", null,
                                    React.createElement("div", { className: 'tree-content' },
                                        React.createElement("a", { className: 'tree-node-group-link', onClick: this.activateGroup },
                                            React.createElement(GroupTreeCountsBadge_1.GroupTreeCountsBadge, { nodeId: nodeId }),
                                            React.createElement("span", null, name))))))))),
            expanded && subGroups && subGroups.map(function (childId) { return React.createElement(exports.GroupTreeNodePanel, { nodeId: childId, key: 'node-' + childId }); })));
    };
    GroupTreeNodePanelImpl.prototype.onToggleExpandedState = function () {
        this.props.expandNode(this.props.nodeId, !this.props.expanded);
    };
    GroupTreeNodePanelImpl.prototype.activateGroup = function () {
        ajax_1.activateGroup(this.props.nodeId);
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

exports.__esModule = true;
var $ = __webpack_require__(6);
function setTitle(selector, title, root) {
    root = root ? root : window.document.body;
    $(root).find(selector).each(function () {
        if (!$(this).attr("title")) {
            $(this).attr("title", title);
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
    $("#" + id).dropdown("toggle");
    return false;
}
function getURLParameter(name) {
    var regExp = new RegExp("[?|&]" + name + "=" + "([^&;]+?)(&|#|;|$)");
    return decodeURIComponent((regExp.exec(location.search) || [undefined, ""])[1].replace(/\+/g, "%20")) || undefined;
}
function limitTextArea($textArea, $feedback, $button, maxTextLen, minRemainingToShow) {
    var f = function () {
        var remaining = maxTextLen - $textArea.val().length;
        if (remaining <= minRemainingToShow) {
            $feedback.html("" + remaining);
        }
        else {
            $feedback.html("");
        }
        if (remaining < 0) {
            $feedback.css("color", "red");
            if ($button) {
                $button.attr("disabled", "");
            }
        }
        else {
            $feedback.css("color", "inherit");
            if ($button) {
                $button.removeAttr("disabled");
            }
        }
    };
    $textArea.keyup(f);
    f();
}
function enableScrollTop() {
    $(document).ready(function () {
        var $backTop = $("#back-top");
        if (!$backTop) {
            return;
        }
        $backTop.hide(); // hide #back-top first
        $(function () {
            $(window).scroll(function () {
                if ($(this).scrollTop() > 100) {
                    $("#back-top").fadeIn();
                }
                else {
                    $("#back-top").fadeOut();
                }
            });
            $("#back-top").find("a").click(function () {
                $("body,html").animate({
                    scrollTop: 0
                }, 500);
                return false;
            });
        });
    });
}
function removeServerSideParsleyError(el) {
    var p = $(el).parsley();
    p.removeError("server-side-parsley-error");
}
function scrollToBlock(selector) {
    var $block = $(selector);
    var offset = $block.offset();
    $("html, body").animate({
        scrollTop: offset.top
    });
}
function closeModal(jqSelector) {
    $(jqSelector).closest(".modal").modal("hide");
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
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var $ = __webpack_require__(6);
function isABootstrapModalOpen() {
    return $(".modal.show").length > 0;
}
function isInInput(e) {
    var el = $(e.target);
    return (el.is("input") || el.is("textarea"));
}
function bindWorkspacePageKeys() {
    $(document).on("keydown", function (e) {
        if (isABootstrapModalOpen() || isInInput(e)) {
            return;
        }
        if (e.which === 65 || e.which === 97) {
            var $btn = $("#add-zametka-button");
            if ($btn.hasClass("active-create")) {
                return;
            }
            $btn.click();
        }
        else if (e.which === 27) {
            var clicked = e.originalEvent.srcElement.getAttribute("id");
            console.log(clicked);
            if (clicked === "create-zametka-text-area") {
                $("#create-zametka-cancel-button").click();
            }
        }
    });
}
exports["default"] = {
    bindWorkspacePageKeys: bindWorkspacePageKeys
};


/***/ })
/******/ ]);