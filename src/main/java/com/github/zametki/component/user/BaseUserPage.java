package com.github.zametki.component.user;

import com.github.zametki.UserSession;
import com.github.zametki.component.BasePage;
import com.github.zametki.component.HomePage;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * This page is visible only for signed in users;
 */
public abstract class BaseUserPage extends BasePage {

    public BaseUserPage(PageParameters pp) {
        this(pp, false);
    }

    public BaseUserPage(PageParameters pp, boolean skipRedirect) {
        super(pp);
        if (!UserSession.get().isSignedIn() && !skipRedirect) {
            throw new RestartResponseException(HomePage.class);
        }
    }

}
