package com.github.zametki.component;

import com.github.zametki.annotation.MountPath;
import com.github.zametki.util.UserSessionUtils;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@MountPath("/logout")
public class LogoutPage extends BasePage {

    public LogoutPage(PageParameters pp) {
        super(pp);
        UserSessionUtils.logout();
    }

}
