package com.github.zametki.component.signin;


import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.component.HomePage;
import com.github.zametki.component.form.InputField;
import com.github.zametki.component.form.PasswordField;
import com.github.zametki.component.parsley.EmailJsValidator;
import com.github.zametki.component.parsley.LoginJsValidator;
import com.github.zametki.component.parsley.ParsleyUtils;
import com.github.zametki.component.parsley.PasswordJsValidator;
import com.github.zametki.component.parsley.RequiredFieldJsValidator;
import com.github.zametki.component.parsley.ValidatingJsAjaxSubmitLink;
import com.github.zametki.model.User;
import com.github.zametki.util.JsUtils;
import com.github.zametki.util.MailUtils;
import com.github.zametki.util.TextUtils;
import com.github.zametki.util.UserSessionUtils;
import com.github.zametki.util.ValidatorUtils;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class RegistrationPanel extends Panel {

    private static final Logger log = LoggerFactory.getLogger(RegistrationPanel.class);

    public RegistrationPanel(String id) {
        super(id);

        if (UserSession.get().isSignedIn()) {
            throw new RestartResponseException(HomePage.class);
        }

        Form form = new Form("register_form");
        form.setOutputMarkupId(true);
        add(form);

        WebMarkupContainer emailError = new WebMarkupContainer("email_error");
        form.add(emailError);

        InputField emailField = new InputField("email_field");
        emailField.add(new EmailJsValidator(emailError));
        form.add(emailField);

        WebMarkupContainer loginError = new WebMarkupContainer("login_error");
        form.add(loginError);

        InputField loginField = new InputField("login_field");
        loginField.add(new LoginJsValidator(loginError));
        form.add(loginField);

        WebMarkupContainer password1Error = new WebMarkupContainer("password1_error");
        form.add(password1Error);

        WebMarkupContainer password2Error = new WebMarkupContainer("password2_error");
        form.add(password2Error);

        PasswordField password1Field = new PasswordField("password1_field", Model.of(""));
        password1Field.add(new PasswordJsValidator(password1Error));
        form.add(password1Field);

        PasswordField password2Field = new PasswordField("password2_field", Model.of(""));
        password2Field.add(new RequiredFieldJsValidator(password2Error));
        form.add(password2Field);

        ValidatingJsAjaxSubmitLink registerButton = new ValidatingJsAjaxSubmitLink("submit", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                if (UserSession.get().isSignedIn()) {
                    throw new RestartResponseException(HomePage.class);
                }

                String email = emailField.getInputString();
                if (TextUtils.isEmpty(email)) {
                    ParsleyUtils.addParsleyError(target, emailError, "Необходимо указать email");
                    JsUtils.focus(target, emailField);
                    return;
                }
                if (!ValidatorUtils.INSTANCE.isValidEmail(email)) {
                    ParsleyUtils.addParsleyError(target, emailError, "Некорректный формат email");
                    JsUtils.focus(target, emailField);
                    return;
                }
                User user = Context.getUsersDbi().getUserByEmail(email);
                if (user != null) {
                    ParsleyUtils.addParsleyError(target, emailError, "Пользователь с таким email уже зарегистрирован");
                    JsUtils.focus(target, emailField);
                    return;
                }
                String login = loginField.getInputString();
                if (!ValidatorUtils.INSTANCE.isValidLogin(login)) {
                    ParsleyUtils.addParsleyError(target, loginError, "Недопустимый псевдоним");
                    JsUtils.focus(target, loginField);
                    return;
                }
                user = Context.getUsersDbi().getUserByLogin(login);
                if (user != null) {
                    ParsleyUtils.addParsleyError(target, loginError, "Пользователь с таким именем уже зарегистрирован");
                    JsUtils.focus(target, loginField);
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

                user = new User();
                user.setLogin(login);
                user.setRegistrationDate(Instant.now());
                user.setLastLoginDate(user.getRegistrationDate());
                user.setEmail(email);
                user.setPasswordHash(UserSessionUtils.password2Hash(password1));
                Context.getUsersDbi().createUser(user);

                target.add(form);
                UserSession.get().setUser(user);
                setResponsePage(HomePage.class);
                try {
                    MailUtils.sendWelcomeEmail(user, password1);
                } catch (Exception e) {
                    log.error("Error during user registration!", e);
                }
            }
        };
        form.add(registerButton);

        JsUtils.focusOnEnter(emailField, loginField);
        JsUtils.focusOnEnter(loginField, password1Field);
        JsUtils.focusOnEnter(password1Field, password2Field);
        JsUtils.clickOnEnter(password2Field, registerButton);

    }
}
