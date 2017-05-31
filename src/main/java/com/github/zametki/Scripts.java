package com.github.zametki;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem;

import static org.apache.wicket.markup.head.JavaScriptHeaderItem.forUrl;

public class Scripts {

    public static final HeaderItem SITE_JS = forUrl("/js/site.js?" + System.currentTimeMillis(), "site.js");

    public static final HeaderItem TETHER_JS = forUrl("https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js", "tether.js");

    public static final HeaderItem BOOTSTRAP_JS = forUrl("https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js", "bootstrap.js");

    public static final HeaderItem PARSLEY_JS = forUrl("https://cdnjs.cloudflare.com/ajax/libs/parsley.js/2.7.2/parsley.min.js", "parsley.js");

    public static final JavaScriptUrlReferenceHeaderItem AUTOLINKER_JS = forUrl("https://cdnjs.cloudflare.com/ajax/libs/autolinker/1.4.3/Autolinker.js", "autolinker.js");

    public static final JavaScriptUrlReferenceHeaderItem AUTOSIZE_JS = forUrl("https://cdnjs.cloudflare.com/ajax/libs/autosize.js/3.0.17/autosize.min.js", "autosize.js");

    public static final JavaScriptUrlReferenceHeaderItem LETTERING_JS = forUrl("https://cdnjs.cloudflare.com/ajax/libs/lettering.js/0.7.0/jquery.lettering.min.js", "lettering.js");

    public static final JavaScriptUrlReferenceHeaderItem FIT_TEXT_JS = forUrl("https://cdnjs.cloudflare.com/ajax/libs/FitText.js/1.2.0/jquery.fittext.min.js", "fittext.js");

    public static final JavaScriptUrlReferenceHeaderItem TEXTILLATE_JS = forUrl("https://cdnjs.cloudflare.com/ajax/libs/textillate/0.4.0/jquery.textillate.min.js", "textillate.js");
}
