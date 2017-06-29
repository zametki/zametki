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

Object.defineProperty(exports, "__esModule", { value: true });
function isAction(action, actionName) {
    return action && action.type && action.type === actionName;
}
exports.isAction = isAction;
class ActionType {
}
ActionType.UpdateGroupTree = 'UpdateTreeAction';
ActionType.ToggleGroupTreeNode = 'ToggleGroupTreeNode';
ActionType.ActivateGroupTreeNode = 'ActivateGroupTreeNode';
ActionType.GroupTreeFilterUpdate = 'GroupTreeFilterUpdate';
exports.ActionType = ActionType;
exports.createUpdateGroupTreeAction = (nodes) => ({ type: ActionType.UpdateGroupTree, payload: { nodes } });
exports.createToggleGroupTreeNodeAction = (nodeId, expanded) => ({ type: ActionType.ToggleGroupTreeNode, payload: { nodeId, expanded } });
exports.createActivateGroupTreeNodeAction = (nodeId) => ({ type: ActionType.ActivateGroupTreeNode, payload: { nodeId } });
exports.createGroupTreeFilterUpdateAction = (filterText) => ({ type: ActionType.GroupTreeFilterUpdate, payload: { filterText } });


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

Object.defineProperty(exports, "__esModule", { value: true });
const Redux = __webpack_require__(4);
const Reducers_1 = __webpack_require__(12);
const ClientStorage_1 = __webpack_require__(5);
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

Object.defineProperty(exports, "__esModule", { value: true });
const store = __webpack_require__(13);
const APP = 'Z';
function getGroupTreeFilterKey() {
    return `${APP}-GT-filter`;
}
function getNodeExpandedKey(nodeId) {
    return `${APP}-GT-n-${nodeId}.expanded`;
}
function update(key, value) {
    value ? store.set(key, value) : store.remove(key);
}
/** Wrapper over localStorage */
class ClientStorage {
    static isNodeExpanded(nodeId) {
        return !!nodeId && store.get(getNodeExpandedKey(nodeId)) === true;
    }
    static setNodeExpanded(nodeId, value) {
        update(getNodeExpandedKey(nodeId), value);
    }
    static setGroupFilterText(value) {
        update(getGroupTreeFilterKey(), value);
    }
    static getGroupFilterText() {
        return store.get(getGroupTreeFilterKey()) || '';
    }
}
exports.ClientStorage = ClientStorage;


/***/ }),
/* 6 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
const callbacks = {
    activateGroup: undefined
};
function activateGroup(groupId) {
    callbacks.activateGroup(groupId);
}
exports.activateGroup = activateGroup;
exports.default = {
    callbacks
};


/***/ }),
/* 7 */
/***/ (function(module, exports) {

module.exports = $;

/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
const SiteDef_1 = __webpack_require__(9);
__webpack_require__(10);
const Server2Client_1 = __webpack_require__(11);
const SiteUtils_1 = __webpack_require__(19);
const Shortcuts_1 = __webpack_require__(20);
const Client2Server_1 = __webpack_require__(6);
__webpack_require__(21);
SiteDef_1.default.Server2Client = Server2Client_1.default;
SiteDef_1.default.Utils = SiteUtils_1.default;
SiteDef_1.default.Shortcuts = Shortcuts_1.default;
SiteDef_1.default.Ajax = Client2Server_1.default;
window.$site = SiteDef_1.default;


/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.default = {
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

Object.defineProperty(exports, "__esModule", { value: true });
/**
 * Interface used by server code to trigger client actions.
 */
const Store_1 = __webpack_require__(3);
const GroupTreeView_1 = __webpack_require__(14);
const Actions_1 = __webpack_require__(0);
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
exports.default = {
    renderGroupTreeView: GroupTreeView_1.renderGroupTreeView,
    dispatchUpdateGroupTreeAction,
    dispatchActivateGroupNodeAction
};


/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
const Redux = __webpack_require__(4);
const Actions_1 = __webpack_require__(0);
const ClientStorage_1 = __webpack_require__(5);
const defaultStoreInstance = {
    groupTree: {
        nodeById: {},
        nodeIds: [],
        filterText: ClientStorage_1.ClientStorage.getGroupFilterText()
    }
};
/** Group Tree reducer */
function groupTree(state = defaultStoreInstance.groupTree, action) {
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
exports.AppReducers = Redux.combineReducers({ groupTree });
function updateGroupTree(state, payload) {
    const nodeById = {};
    const nodeIds = [];
    payload.nodes.map(n => {
        const old = state.nodeById[n.id];
        n.expanded = !!old ? old.expanded : ClientStorage_1.ClientStorage.isNodeExpanded(n.id);
        nodeById[n.id] = n;
        nodeIds.push(n.id);
    });
    return Object.assign({}, state, { nodeById, nodeIds });
}
function toggleGroupTreeNode(state, payload) {
    const nodeById = state.nodeById;
    const n = nodeById[payload.nodeId];
    if (n) {
        n.expanded = payload.expanded;
        ClientStorage_1.ClientStorage.setNodeExpanded(n.id, n.expanded);
    }
    return Object.assign({}, state, { nodeById });
}
function activateGroupTreeNode(state, payload) {
    const nodeById = state.nodeById;
    // deactivate all other nodes.
    for (let id of state.nodeIds) {
        nodeById[id].active = false;
    }
    // activate new node.
    const n = nodeById[payload.nodeId];
    if (!n) {
        return;
    }
    n.active = true;
    // expand all parent nodes to make new node visible.
    let parentNode = nodeById[n.parentId];
    while (parentNode) {
        if (!parentNode.expanded) {
            parentNode.expanded = true;
            ClientStorage_1.ClientStorage.setNodeExpanded(parentNode.id, true);
        }
        parentNode = nodeById[parentNode.parentId];
    }
    return Object.assign({}, state, { nodeById });
}
function updateGroupTreeFilter(state, payload) {
    ClientStorage_1.ClientStorage.setGroupFilterText(payload.filterText);
    return Object.assign({}, state, { filterText: payload.filterText });
}


/***/ }),
/* 13 */
/***/ (function(module, exports) {

module.exports = store;

/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
const React = __webpack_require__(1);
const react_dom_1 = __webpack_require__(15);
const ReactRedux = __webpack_require__(2);
const Store_1 = __webpack_require__(3);
const GroupTreeNodePanel_1 = __webpack_require__(16);
const GroupTreeFilterPanel_1 = __webpack_require__(18);
const mapStateToProps = (store) => {
    const topLevelGroupIds = store.groupTree.nodeIds
        .filter(id => store.groupTree.nodeById[id].parentId === Store_1.GROUP_TREE_ROOT_NODE_ID);
    return { topLevelGroupIds };
};
class GroupTreeViewImpl extends React.Component {
    render() {
        const treeNodes = this.props.topLevelGroupIds.map(id => React.createElement(GroupTreeNodePanel_1.GroupTreeNodePanel, { nodeId: id, key: 'node-' + id }));
        return (React.createElement("div", null,
            React.createElement(GroupTreeFilterPanel_1.GroupTreeFilterPanel, null),
            treeNodes));
    }
}
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

Object.defineProperty(exports, "__esModule", { value: true });
const React = __webpack_require__(1);
const ReactRedux = __webpack_require__(2);
const Store_1 = __webpack_require__(3);
const Client2Server_1 = __webpack_require__(6);
const Actions_1 = __webpack_require__(0);
const GroupTreeCountsBadge_1 = __webpack_require__(17);
/** Maps Store state to component props */
const mapStateToProps = (store, ownProps) => {
    let node = store.groupTree.nodeById[ownProps.nodeId];
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
        toggleExpandedState: (nodeId, expanded) => dispatch(Actions_1.createToggleGroupTreeNodeAction(nodeId, expanded))
    };
}
function matchesByFilter(nodeId, filterText, nodeById) {
    if (filterText.length == 0) {
        return true;
    }
    const node = nodeById[nodeId];
    if (node && node.name.toLowerCase().includes(filterText)) {
        return true;
    }
    return node.children.find(id => matchesByFilter(id, filterText, nodeById)) >= 0;
}
class GroupTreeNodePanelImpl extends React.Component {
    constructor(props, context) {
        //noinspection TypeScriptValidateTypes
        super(props, context);
        this.onToggleExpandedState = this.onToggleExpandedState.bind(this);
        this.activateGroup = this.activateGroup.bind(this);
    }
    render() {
        const { nodeId, name, subGroups, active, expanded, level, filterText } = this.props;
        if (!name) {
            console.error(`Node not found: ${this.props}`);
            return null;
        }
        const filtered = !matchesByFilter(nodeId, filterText.toLowerCase(), Store_1.appStore.getState().groupTree.nodeById);
        const node = filtered ? null :
            (React.createElement("div", { className: 'tree-node' + (active ? ' tree-node-active' : '') },
                React.createElement("div", { style: { paddingLeft: level * 16 } },
                    React.createElement("table", { className: 'w100' },
                        React.createElement("tbody", null,
                            React.createElement("tr", null,
                                React.createElement("td", { className: 'tree-junction-td' },
                                    React.createElement("a", { className: 'tree-junction' + (subGroups && subGroups.length > 0 && (expanded ? ' tree-junction-expanded' : ' tree-junction-collapsed')), onClick: this.onToggleExpandedState })),
                                React.createElement("td", null,
                                    React.createElement("div", { className: 'tree-content' },
                                        React.createElement("a", { className: 'tree-node-group-link', onClick: this.activateGroup },
                                            React.createElement(GroupTreeCountsBadge_1.GroupTreeCountsBadge, { nodeId: nodeId }),
                                            React.createElement("span", null, name))))))))));
        return (React.createElement("div", null,
            node,
            expanded && !filtered && subGroups && subGroups.map(childId => React.createElement(exports.GroupTreeNodePanel, { nodeId: childId, key: 'node-' + childId }))));
    }
    onToggleExpandedState() {
        this.props.toggleExpandedState(this.props.nodeId, !this.props.expanded);
    }
    activateGroup() {
        Client2Server_1.activateGroup(this.props.nodeId);
    }
}
// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
exports.GroupTreeNodePanel = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeNodePanelImpl);


/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
const React = __webpack_require__(1);
const ReactRedux = __webpack_require__(2);
/** Maps Store state to component props */
const mapStateToProps = (store, ownProps) => {
    let node = store.groupTree.nodeById[ownProps.nodeId];
    return {
        entriesCount: node.entriesCount
    };
};
class GroupTreeCountsBadgeImpl extends React.Component {
    shouldComponentUpdate(nextProps) {
        return nextProps.entriesCount !== this.props.entriesCount;
    }
    render() {
        const { entriesCount } = this.props;
        if (entriesCount <= 0) {
            return null;
        }
        return (React.createElement("div", { className: 'badge zametka-count-badge float-right ml-1' }, entriesCount));
    }
}
// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
exports.GroupTreeCountsBadge = ReactRedux.connect(mapStateToProps, null)(GroupTreeCountsBadgeImpl);


/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
const React = __webpack_require__(1);
const ReactRedux = __webpack_require__(2);
const Actions_1 = __webpack_require__(0);
/** Maps Store state to component props */
const mapStateToProps = (store) => {
    return {
        filterText: store.groupTree.filterText
    };
};
// noinspection JSUnusedLocalSymbols
function mapDispatchToProps(dispatch) {
    return {
        updateFilterText: (filterText) => dispatch(Actions_1.createGroupTreeFilterUpdateAction(filterText))
    };
}
class GroupTreeFilterPanelImpl extends React.Component {
    render() {
        const { filterText } = this.props;
        const resetIsOn = filterText.length > 0;
        const resetButtonClassName = `gt-search-reset-button${resetIsOn ? ' gt-search-reset-button-on' : ''}`;
        return (React.createElement("div", { className: "gt-search-block" },
            React.createElement("form", { onReset: this.handleReset.bind(this), onSubmit: e => e.preventDefault() },
                React.createElement("input", { ref: "inputElement", onChange: this.handleTextChange.bind(this), type: "search", className: "gt-search-input", placeholder: " …", value: filterText, title: "Фильтр по имени группы" }),
                React.createElement("button", { className: resetButtonClassName, type: "reset", title: "Сбросить фильтр" }, "\u00D7"))));
    }
    handleReset() {
        this.props.updateFilterText('');
    }
    handleTextChange() {
        this.props.updateFilterText(this.refs.inputElement.value);
    }
}
// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
exports.GroupTreeFilterPanel = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTreeFilterPanelImpl);


/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
const $ = __webpack_require__(7);
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
    let keyCode = (event.which ? event.which : event.keyCode);
    if ((keyCode === 10 || keyCode === 13) && !event.ctrlKey) {
        $(id).click();
        event.preventDefault();
    }
}
function clickOnCtrlEnter(event, id) {
    let keyCode = (event.which ? event.which : event.keyCode);
    if ((keyCode === 10 || keyCode === 13) && event.ctrlKey) {
        $(id).click();
        event.preventDefault();
    }
}
function showMenuByClick(e, id) {
    let evt = e ? e : window.event;
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
    let regExp = new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)');
    return decodeURIComponent((regExp.exec(location.search) || [undefined, ''])[1].replace(/\+/g, '%20')) || undefined;
}
function limitTextArea(textAreaId, $feedback, $button, maxTextLen, minRemainingToShow) {
    const textArea = window.document.getElementById(textAreaId);
    const limitFn = function () {
        let remaining = maxTextLen - textArea.value.length;
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
    $(document).ready(() => {
        let $backTop = $('#back-top');
        if (!$backTop) {
            return;
        }
        $backTop.hide(); // hide #back-top first
        $(() => {
            $(window).scroll(function () {
                if ($(this).scrollTop() > 100) {
                    $('#back-top').fadeIn();
                }
                else {
                    $('#back-top').fadeOut();
                }
            });
            $('#back-top').find('a').click(() => {
                $('body,html').animate({
                    scrollTop: 0
                }, 500);
                return false;
            });
        });
    });
}
function removeServerSideParsleyError(el) {
    const p = $(el).parsley();
    p.removeError('server-side-parsley-error');
}
function scrollToBlock(selector) {
    const $block = $(selector);
    const offset = $block.offset();
    $('html, body').animate({
        scrollTop: offset.top
    });
}
function closeModal(jqSelector) {
    $(jqSelector).closest('.modal').modal('hide');
}
exports.default = {
    setTitle: setTitle,
    focusOnEnter: focusOnEnter,
    clickOnEnter: clickOnEnter,
    clickOnCtrlEnter: clickOnCtrlEnter,
    showMenuByClick: showMenuByClick,
    getURLParameter: getURLParameter,
    limitTextArea,
    enableScrollTop: enableScrollTop,
    removeServerSideParsleyError: removeServerSideParsleyError,
    scrollToBlock: scrollToBlock,
    closeModal: closeModal
};


/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
const $ = __webpack_require__(7);
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
        let element = event.srcElement;
        if (isInInput(element)) {
            if (event.which === 27) {
                const elementId = element.getAttribute('id');
                if (elementId === 'create-zametka-text-area') {
                    $('#create-zametka-cancel-button').click();
                }
            }
            return;
        }
        if (event.which === 65 || event.which === 97) {
            const $btn = $('#add-zametka-button');
            if ($btn.hasClass('active-create')) {
                return;
            }
            $btn.click();
        }
    });
}
exports.default = {
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