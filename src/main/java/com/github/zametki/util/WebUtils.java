package com.github.zametki.util;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.ZApplication;
import com.github.zametki.component.BasePage;
import com.github.zametki.component.HomePage;
import com.github.zametki.model.User;
import com.github.zametki.model.UserId;
import org.apache.commons.io.IOUtils;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.core.request.handler.BookmarkablePageRequestHandler;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils {

    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static void focus(@NotNull AjaxRequestTarget target, @NotNull Component component) {
        String markupId = component.getMarkupId();
        if (TextUtils.isEmpty(markupId)) {
            log.error("Component has no markup id: " + component);
            return;
        }
        focus(target, markupId);
    }

    public static void focus(@NotNull AjaxRequestTarget target, @NotNull String id) {
        target.appendJavaScript("$('#" + id + "').focus();");
    }

    public static void focusOnEnter(@NotNull Component keypressComponent, @NotNull Component component) {
        String markupId = component.getMarkupId();
        if (TextUtils.isEmpty(markupId)) {
            log.error("Component has no markup id: " + component);
            return;
        }
        focusOnEnter(keypressComponent, markupId);
    }

    public static void focusOnEnter(@NotNull Component keypressComponent, @NotNull String focusedId) {
        keypressComponent.add(new AttributeAppender("onkeypress", "$site.Utils.focusOnEnter(event, '#" + focusedId + "');"));
    }

    public static void clickOnEnter(@NotNull Component keypressComponent, @NotNull Component component) {
        String markupId = component.getMarkupId();
        if (TextUtils.isEmpty(markupId)) {
            log.error("Component has no markup id: " + component);
            return;
        }
        clickOnEnter(keypressComponent, markupId);
    }

    public static void clickOnEnter(@NotNull Component keypressComponent, @NotNull String clickedId) {
        keypressComponent.add(new AttributeAppender("onkeypress", "$site.Utils.clickOnEnter(event, '#" + clickedId + "');"));
    }

    public static void clickOnCtrlEnter(@NotNull Component keypressComponent, @NotNull Component component) {
        String markupId = component.getMarkupId();
        if (TextUtils.isEmpty(markupId)) {
            log.error("Component has no markup id: " + component);
            return;
        }
        clickOnCtrlEnter(keypressComponent, markupId);
    }

    public static void clickOnCtrlEnter(@NotNull Component keypressComponent, @NotNull String clickedId) {
        keypressComponent.add(new AttributeAppender("onkeypress", "$site.Utils.clickOnCtrlEnter(event, '#" + clickedId + "');"));
    }

    @NotNull
    public static byte[] getWebInfResource(@NotNull String path) throws Exception {
        return IOUtils.toByteArray(WebApplication.get().getServletContext().getResource(path));
    }

    public static void scrollTo(@NotNull Component c, @NotNull AjaxRequestTarget target) {
        target.appendJavaScript("$site.Utils.scrollToBlock('#" + c.getMarkupId() + "');");
    }

    @NotNull
    public static String getFullPageUrl(@NotNull Class<? extends Page> cls, @Nullable PageParameters pp) {
        return Context.getBaseUrl() + getPageMount(cls, pp);
    }

    @NotNull
    public static String getPageMount(@NotNull Class<? extends Page> cls, @Nullable PageParameters pp) {
        IRequestHandler handler = new BookmarkablePageRequestHandler(new PageProvider(cls, pp));
        String url = ZApplication.get().getRootRequestMapper().mapHandler(handler).toString();
        url = url.startsWith(".") ? url.substring(1) : url;
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        if (!url.isEmpty() && !url.startsWith("/")) {
            url = "/" + url;
        }
        return url;
    }

    @NotNull
    public static UserId getUserIdOrRedirectHome() {
        return getUserOrRedirectHome().id;
    }

    public static User getUserOrRedirectHome() {
        return nonNullOrRedirect(UserSession.get().getUser(), HomePage.class);
    }

    @NotNull
    private static <T> T nonNullOrRedirect(@Nullable T t, Class<? extends BasePage> page) {
        if (t == null) {
            throw new RestartResponseException(page);
        }
        return t;
    }
}
