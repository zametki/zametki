package com.github.zametki.component.user;

import com.github.zametki.Context;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.HomePage;
import com.github.zametki.component.LogoPanel;
import com.github.zametki.component.LogoutPage;
import com.github.zametki.component.WorkspacePage;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.form.CaptchaField;
import com.github.zametki.component.form.Feedback;
import com.github.zametki.component.form.PasswordField;
import com.github.zametki.component.form.RefreshCaptchaLink;
import com.github.zametki.component.parsley.ParsleyUtils;
import com.github.zametki.component.parsley.PasswordJsValidator;
import com.github.zametki.component.parsley.RequiredFieldJsValidator;
import com.github.zametki.component.parsley.ValidatingJsAjaxSubmitLink;
import com.github.zametki.model.User;
import com.github.zametki.util.JsUtils;
import com.github.zametki.util.UserSessionUtils;
import com.github.zametki.util.ValidatorUtils;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@MountPath("/my/profile")
public class UserProfileSettingsPage extends BaseUserPage {

    public UserProfileSettingsPage(PageParameters pp) {
        super(pp);

        setTitleAndDesc("Профиль пользователя", "Редактирование персонального профиля пользователя");

        add(new LogoPanel("brand_logo"));
        add(new BookmarkablePageLink("ws_link", WorkspacePage.class));
        add(new BookmarkablePageLink("logout_link", LogoutPage.class));

        WebMarkupContainer panel = new ContainerWithId("panel");
        add(panel);

        Feedback feedback = new Feedback("feedback");
        panel.add(feedback);

        BookmarkablePageLink homePageLink = new BookmarkablePageLink("home_link", HomePage.class);
        homePageLink.setVisible(false);
        panel.add(homePageLink);

        Form form = new Form("form");
        form.setOutputMarkupId(true);
        panel.add(form);

        WebMarkupContainer oldPasswordError = new WebMarkupContainer("old_password_error");
        form.add(oldPasswordError);

        WebMarkupContainer password1Error = new WebMarkupContainer("password1_error");
        form.add(password1Error);

        WebMarkupContainer password2Error = new WebMarkupContainer("password2_error");
        form.add(password2Error);

        PasswordField oldPasswordField = new PasswordField("old_password_field", Model.of(""));
        oldPasswordField.add(new RequiredFieldJsValidator(oldPasswordError));
        form.add(oldPasswordField);

        PasswordField password1Field = new PasswordField("password1_field", Model.of(""));
        password1Field.add(new PasswordJsValidator(password1Error));
        form.add(password1Field);

        PasswordField password2Field = new PasswordField("password2_field", Model.of(""));
        password2Field.add(new PasswordJsValidator(password2Error));
        form.add(password2Field);

        WebMarkupContainer captchaError = new WebMarkupContainer("captcha_error");
        form.add(captchaError);
        CaptchaField captchaField = new CaptchaField("captcha_value");
        captchaField.add(new RequiredFieldJsValidator(captchaError));
        form.add(captchaField);

        Image captchaImage = new NonCachingImage("captcha_image", captchaField.getCaptchaImageResource());
        captchaImage.setOutputMarkupId(true);
        form.add(captchaImage);
        form.add(new RefreshCaptchaLink("change_captcha", captchaField, captchaImage));

        form.add(new BookmarkablePageLink<>("ws_link2", WorkspacePage.class));

        form.add(new ValidatingJsAjaxSubmitLink("update_link", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                feedback.reset(target);
                target.add(panel);

                String captcha = captchaField.getUserText();
                if (!captchaField.getOriginalText().equals(captcha)) {
                    ParsleyUtils.addParsleyError(target, captchaError, "Некорректный код!");
                    JsUtils.focus(target, captchaField);
                    return;
                }

                User user = WebUtils.getUserOrRedirectHome();
                String oldPassword = oldPasswordField.getModelObject();
                String oldPasswordHash = UserSessionUtils.password2Hash(oldPassword);
                if (!user.getPasswordHash().equals(oldPasswordHash)) {
                    ParsleyUtils.addParsleyError(target, oldPasswordError, "Неверный пароль");
                    JsUtils.focus(target, oldPasswordField);
                    return;
                }
                String password1 = password1Field.getModelObject();
                String password2 = password2Field.getModelObject();
                String err = ValidatorUtils.INSTANCE.validatePassword(password1, password2);
                if (err != null) {
                    ParsleyUtils.addParsleyError(target, password1Error, err);
                    JsUtils.focus(target, password1Field);
                    return;
                }
                user.setPasswordHash(UserSessionUtils.password2Hash(password1));
                Context.getUsersDbi().updatePassword(user, null);
                feedback.success("Пароль изменен");
                homePageLink.setVisible(true);
                JsUtils.scrollTo(feedback, target);
                target.add(panel);
            }
        });
    }
}
