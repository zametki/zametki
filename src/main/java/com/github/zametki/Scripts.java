package com.github.zametki;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem;
import org.jetbrains.annotations.NotNull;

import static org.apache.wicket.markup.head.JavaScriptHeaderItem.forUrl;

public class Scripts {

    public static final HeaderItem SITE_JS = forUrl("/js/site.js?" + System.currentTimeMillis(), "site.js");

    public static final HeaderItem TETHER_JS = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"), "tether.js");

    public static final HeaderItem BOOTSTRAP_JS = forUrl(min("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"), "bootstrap.js");

    public static final HeaderItem PARSLEY_JS = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/parsley.js/2.7.1/parsley.min.js"), "parsley.js");

    public static final JavaScriptUrlReferenceHeaderItem AUTOSIZE_JS = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/autosize.js/3.0.17/autosize.min.js"), "autosize.js");

    public static final JavaScriptUrlReferenceHeaderItem LETTERING_JS = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/lettering.js/0.7.0/jquery.lettering.min.js"), "lettering.js");

    public static final JavaScriptUrlReferenceHeaderItem FIT_TEXT_JS = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/FitText.js/1.2.0/jquery.fittext.min.js"), "fittext.js");

    public static final JavaScriptUrlReferenceHeaderItem TEXTILLATE_JS = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/textillate/0.4.0/jquery.textillate.min.js"), "textillate.js");

    public static final JavaScriptUrlReferenceHeaderItem REACT_JS = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/react/15.6.1/react.min.js"), "react.js");

    public static final JavaScriptUrlReferenceHeaderItem REACT_DOM_JS = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/react/15.6.1/react-dom.min.js"), "react-dom.js");

    public static final JavaScriptUrlReferenceHeaderItem REDUX = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/redux/3.7.0/redux.min.js"), "redux.js");

    public static final JavaScriptUrlReferenceHeaderItem REACT_REDUX = forUrl(min("https://cdnjs.cloudflare.com/ajax/libs/react-redux/5.0.5/react-redux.min.js"), "react-redux.js");

    @NotNull
    private static String min(@NotNull String url) {
        return Context.isProduction() ? url : url.replace(".min", "");
    }

}
