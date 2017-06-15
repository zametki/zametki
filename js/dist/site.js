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
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
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
/******/ 	return __webpack_require__(__webpack_require__.s = 12);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
function isAction(action, actionName) {
    return action && action.type && action.type == actionName;
}
exports.isAction = isAction;
exports.ActionType_UpdateTree = "UpdateTreeAction";
exports.ActionType_ToggleTreeNode = "ToggleTreeNode";
exports.createUpdateTreeAction = function (rootNode) { return ({ type: exports.ActionType_UpdateTree, payload: { rootNode: rootNode } }); };
exports.createToggleTreeNodeAction = function (nodeId, expanded) { return ({ type: exports.ActionType_ToggleTreeNode, payload: { nodeId: nodeId, expanded: expanded } }); };


/***/ }),
/* 1 */
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
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var Redux = __webpack_require__(4);
var Reducers_1 = __webpack_require__(11);
exports.GROUP_TREE_ROOT_NODE_ID = 0;
exports.appStore = Redux.createStore(Reducers_1.AppReducers, {
    groupTree: { nodeById: {} }
}, window["__REDUX_DEVTOOLS_EXTENSION__"] && window["__REDUX_DEVTOOLS_EXTENSION__"]()
// Redux.applyMiddleware(thunk),
);


/***/ }),
/* 3 */
/***/ (function(module, exports) {

module.exports = $;

/***/ }),
/* 4 */
/***/ (function(module, exports) {

module.exports = Redux;

/***/ }),
/* 5 */
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
/* 6 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var Store_1 = __webpack_require__(2);
var GroupTreeView_1 = __webpack_require__(10);
var Actions_1 = __webpack_require__(0);
function renderGroupTree(id) {
    console.log("renderGroupTree: " + id);
    GroupTreeView_1.GroupTreeView.wrap(id);
}
function onGroupTreeChanged(rootNode) {
    console.log("onGroupTreeChanged: " + rootNode);
    var updateTreeAction = Actions_1.createUpdateTreeAction(rootNode);
    //noinspection TypeScriptValidateTypes
    Store_1.appStore.dispatch(updateTreeAction);
}
exports["default"] = {
    onGroupTreeChanged: onGroupTreeChanged,
    renderGroupTree: renderGroupTree
};


/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var $ = __webpack_require__(3);
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
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var $ = __webpack_require__(3);
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
/* 10 */
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
var React = __webpack_require__(13);
var ReactDOM = __webpack_require__(14);
var ReactRedux = __webpack_require__(15);
var Store_1 = __webpack_require__(2);
var ajax_1 = __webpack_require__(1);
var Actions_1 = __webpack_require__(0);
/** Maps Store state to component props */
var mapStateToProps = function (store, ownProps) {
    return {
        node: store.groupTree.nodeById[ownProps.nodeId]
    };
};
// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch) {
    return {
        expandNode: function (nodeId, expanded) { return dispatch(Actions_1.createToggleTreeNodeAction(nodeId, expanded)); }
    };
}
var GroupTreeView = (function (_super) {
    __extends(GroupTreeView, _super);
    function GroupTreeView(props, context) {
        var _this = 
        //noinspection TypeScriptValidateTypes
        _super.call(this, props, context) || this;
        if (typeof props.nodeId === "undefined") {
            throw new Error("no node id");
        }
        _this.onToggleExpandedState = _this.onToggleExpandedState.bind(_this);
        _this.activateGroup = _this.activateGroup.bind(_this);
        return _this;
    }
    GroupTreeView.prototype.render = function () {
        if (!this.props.node) {
            return null;
        }
        var children = this.props.node.children;
        var isRoot = this.props.nodeId === Store_1.GROUP_TREE_ROOT_NODE_ID;
        var renderSubtree = children && children.length > 0 && (isRoot || this.props.node.expanded);
        var subTree = renderSubtree ? children.map(function (child) { return React.createElement(exports.GTV, { nodeId: child.id }); }) : undefined;
        var nodeComponent;
        var treeNodeClass = "tree-node" + (this.props.node.active ? " tree-node-active" : "");
        var treeJunctionClass = "tree-junction" + (children && children.length > 0 ? (this.props.node.expanded ? " tree-junction-expanded" : " tree-junction-collapsed") : "");
        var countLabel = null;
        if (this.props.node.entriesCount > 0) {
            countLabel = React.createElement("span", { className: "badge zametka-count-badge float-right ml-1" }, this.props.node.entriesCount);
        }
        if (this.props.node.id != 0) {
            nodeComponent = (React.createElement("div", { className: treeNodeClass },
                React.createElement("div", { style: { paddingLeft: this.props.node.level * 16 } },
                    React.createElement("table", { className: "w100" },
                        React.createElement("tr", null,
                            React.createElement("td", { className: "tree-junction-td" },
                                React.createElement("a", { className: treeJunctionClass, onClick: this.onToggleExpandedState })),
                            React.createElement("td", null,
                                React.createElement("div", { className: "tree-content" },
                                    React.createElement("a", { className: "tree-node-group-link", onClick: this.activateGroup },
                                        countLabel,
                                        React.createElement("span", null, this.props.node.name)))))))));
        }
        return (React.createElement("div", null,
            nodeComponent,
            subTree));
    };
    GroupTreeView.wrap = function (id) {
        ReactDOM.render(React.createElement(ReactRedux.Provider, { store: Store_1.appStore },
            React.createElement(exports.GTV, { nodeId: Store_1.GROUP_TREE_ROOT_NODE_ID })), document.getElementById(id));
    };
    GroupTreeView.prototype.onToggleExpandedState = function () {
        this.props.expandNode(this.props.node.id, !this.props.node.expanded);
    };
    GroupTreeView.prototype.activateGroup = function () {
        ajax_1.activateGroup(this.props.node.id);
    };
    return GroupTreeView;
}(React.Component));
exports.GroupTreeView = GroupTreeView;
// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
exports.GTV = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeView);


/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var Redux = __webpack_require__(4);
var Actions_1 = __webpack_require__(0);
function flattenTree(node, nodeById) {
    nodeById[node.id] = node;
    if (node.children) {
        for (var _i = 0, _a = node.children; _i < _a.length; _i++) {
            var child = _a[_i];
            flattenTree(child, nodeById);
        }
    }
    return nodeById;
}
/** Group Tree reducer */
function groupTree(state, action) {
    if (state === void 0) { state = { nodeById: {} }; }
    if (Actions_1.isAction(action, Actions_1.ActionType_UpdateTree)) {
        return {
            nodeById: flattenTree(action.payload.rootNode, {})
        };
    }
    else if (Actions_1.isAction(action, Actions_1.ActionType_ToggleTreeNode)) {
        console.log("expanded: " + action.payload.nodeId + "->" + action.payload.expanded);
        var nodeById = state.nodeById;
        var node = nodeById[action.payload.nodeId];
        if (node) {
            node.expanded = action.payload.expanded;
        }
        return { nodeById: nodeById };
    }
    return state;
}
exports.AppReducers = Redux.combineReducers({ groupTree: groupTree });


/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var site_def_1 = __webpack_require__(8);
__webpack_require__(5);
var react_utils_1 = __webpack_require__(6);
var site_utils_1 = __webpack_require__(9);
var shortcuts_1 = __webpack_require__(7);
var ajax_1 = __webpack_require__(1);
site_def_1["default"].ReactUtils = react_utils_1["default"];
site_def_1["default"].Utils = site_utils_1["default"];
site_def_1["default"].Shortcuts = shortcuts_1["default"];
site_def_1["default"].Ajax = ajax_1["default"];
window.$site = site_def_1["default"];


/***/ }),
/* 13 */
/***/ (function(module, exports) {

module.exports = React;

/***/ }),
/* 14 */
/***/ (function(module, exports) {

module.exports = ReactDOM;

/***/ }),
/* 15 */
/***/ (function(module, exports) {

module.exports = ReactRedux;

/***/ })
/******/ ]);