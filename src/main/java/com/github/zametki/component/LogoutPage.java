package com.github.zametki.component;

import com.github.zametki.annotation.MountPath;
import com.github.zametki.util.UserSessionUtils;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.markup.html.WebPage;

@MountPath("/logout")
public class LogoutPage extends WebPage {

    public LogoutPage() {
        UserSessionUtils.logout();
        throw new RestartResponseException(new PageProvider(HomePage.class), RenderPageRequestHandler.RedirectPolicy.NEVER_REDIRECT);
    }

}
