package com.github.zametki.component.form

import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.model.IModel

/**
 * Password field without default wicket validation.
 */
class PasswordField(id: String, model: IModel<String>) : PasswordTextField(id, model) {

    init {
        isRequired = false
    }
}
