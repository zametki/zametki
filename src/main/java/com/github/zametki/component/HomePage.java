package com.github.zametki.component;

import com.github.zametki.UserSession;
import com.github.zametki.behavior.StyleAppender;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.bootstrap.BootstrapStaticModalLink;
import com.github.zametki.component.signin.LoginPanel;
import com.github.zametki.component.signin.RegistrationPanel;
import com.github.zametki.model.User;
import com.github.zametki.util.UserSessionUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;

import static com.github.zametki.component.bootstrap.BootstrapModal.BodyMode;
import static com.github.zametki.component.bootstrap.BootstrapModal.FooterMode;

public class HomePage extends BasePage {

    public HomePage() {
        User user = UserSession.get().getUser();

        WebMarkupContainer notLoggedInBlock = new WebMarkupContainer("not_logged_in_block");
        notLoggedInBlock.setVisible(user == null);
        add(notLoggedInBlock);

        if (notLoggedInBlock.isVisible()) {
            BootstrapModal loginModal = new BootstrapModal("login_modal", null, LoginPanel::new, BodyMode.Static, FooterMode.Hide);
            loginModal.dialog.add(new StyleAppender("padding-left: 30px; padding-right: 30px; width:270px;"));
            add(loginModal);

            BootstrapModal registrationModal = new BootstrapModal("registration_modal", null, RegistrationPanel::new, BodyMode.Static, FooterMode.Hide);
            registrationModal.dialog.add(new StyleAppender("padding-left: 30px; padding-right: 30px; width:350px;"));
            add(registrationModal);

            notLoggedInBlock.add(new BootstrapStaticModalLink("login_link", loginModal));
            notLoggedInBlock.add(new BootstrapStaticModalLink("registration_link", registrationModal));
        } else {
            add(new EmptyPanel("login_modal"));
            add(new EmptyPanel("registration_modal"));
        }

        WebMarkupContainer loggedInBlock = new WebMarkupContainer("logged_in_block");
        loggedInBlock.setVisible(user != null);
        add(loggedInBlock);
        if (loggedInBlock.isVisible()) {
            loggedInBlock.add(new BookmarkablePageLink("lenta_link", LentaPage.class));
            loggedInBlock.add(new AjaxLink<Void>("logout_link") {
                @Override
                public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                    UserSessionUtils.logout();
                    setResponsePage(HomePage.class);
                }
            });
        }
    }
}
