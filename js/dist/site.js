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
/******/ 	return __webpack_require__(__webpack_require__.s = 7);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports) {

module.exports = $;

/***/ }),
/* 1 */
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
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var GroupTree_1 = __webpack_require__(6);
function renderGroupTree(id) {
    GroupTree_1.GroupTree.wrap(id);
}
exports["default"] = {
    renderGroupTree: renderGroupTree
};


/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var $ = __webpack_require__(0);
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
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
exports["default"] = {
    /** React helpers */
    ReactUtils: undefined,
    /** Set of utility functions */
    Utils: undefined,
    /** Key bindings support */
    Shortcuts: undefined
};


/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var $ = __webpack_require__(0);
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
/* 6 */
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
var React = __webpack_require__(8);
var ReactDOM = __webpack_require__(9);
var Redux = __webpack_require__(11);
var ReactRedux = __webpack_require__(10);
var initialState = { value: 0 };
exports.incrementCounter = function (delta) { return ({
    type: "INCREMENT_COUNTER",
    delta: delta
}); };
exports.resetCounter = function () { return ({
    type: "RESET_COUNTER"
}); };
function counter(state, action) {
    if (state === void 0) { state = initialState; }
    switch (action.type) {
        case "INCREMENT_COUNTER":
            return { value: state.value + action.delta };
        case "RESET_COUNTER":
            return { value: 0 };
        default:
            return state;
    }
}
exports.reducers = Redux.combineReducers({
    counter: counter
});
var mapStateToProps = function (state, ownProps) { return ({
    counter: state.counter
}); };
//noinspection TypeScriptValidateTypes
var mapDispatchToProps = function (dispatch) { return ({
    increment: function (n) { return dispatch(exports.incrementCounter(n)); },
    reset: function () { return dispatch(exports.resetCounter()); }
}); };
var GroupTree = (function (_super) {
    __extends(GroupTree, _super);
    function GroupTree(props, context) {
        var _this = _super.call(this, props, context) || this;
        _this.onClickIncrement = _this.onClickIncrement.bind(_this);
        _this.onClickReset = _this.onClickReset.bind(_this);
        return _this;
    }
    GroupTree.prototype.render = function () {
        var props = this.props;
        return (React.createElement("div", null,
            React.createElement("strong", null, props.counter.value),
            React.createElement("button", { ref: 'increment', onClick: this.onClickIncrement }, "increment"),
            React.createElement("button", { ref: 'increment', onClick: this.onClickReset }, "reset")));
    };
    GroupTree.prototype.onClickIncrement = function (e) {
        var props = this.props;
        e.preventDefault();
        props.increment(1);
    };
    GroupTree.prototype.onClickReset = function (e) {
        var props = this.props;
        e.preventDefault();
        props.reset();
    };
    GroupTree.wrap = function (id) {
        var store = Redux.createStore(exports.reducers, {});
        ReactDOM.render(React.createElement(ReactRedux.Provider, { store: store },
            React.createElement(exports.GT, null)), document.getElementById(id));
    };
    return GroupTree;
}(React.Component));
exports.GroupTree = GroupTree;
// https://github.com/DefinitelyTyped/DefinitelyTyped/issues/8787
exports.GT = ReactRedux.connect(mapStateToProps, mapDispatchToProps)(GroupTree);


/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

exports.__esModule = true;
var site_def_1 = __webpack_require__(4);
__webpack_require__(1);
var react_utils_1 = __webpack_require__(2);
var site_utils_1 = __webpack_require__(5);
var shortcuts_1 = __webpack_require__(3);
site_def_1["default"].ReactUtils = react_utils_1["default"];
site_def_1["default"].Utils = site_utils_1["default"];
site_def_1["default"].Shortcuts = shortcuts_1["default"];
window.$site = site_def_1["default"];


/***/ }),
/* 8 */
/***/ (function(module, exports) {

module.exports = React;

/***/ }),
/* 9 */
/***/ (function(module, exports) {

module.exports = ReactDOM;

/***/ }),
/* 10 */
/***/ (function(module, exports) {

module.exports = ReactRedux;

/***/ }),
/* 11 */
/***/ (function(module, exports) {

module.exports = Redux;

/***/ })
/******/ ]);