package com.github.zametki.component.user;

import com.github.zametki.UserSession;
import com.github.zametki.component.BasePage;
import com.github.zametki.component.HomePage;
import org.apache.wicket.RestartResponseException;

/**
 * This page is visible only for signed in users;
 */
public abstract class BaseUserPage extends BasePage {

    public BaseUserPage() {
        this(false);
    }

    public BaseUserPage(boolean skipRedirect) {
        if (!UserSession.get().isSignedIn() && !skipRedirect) {
            throw new RestartResponseException(HomePage.class);
        }
    }

}
