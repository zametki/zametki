package com.github.zametki.component.form

import com.github.zametki.util.TextUtils
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.Model

class InputField @JvmOverloads constructor(id: String, t: String? = "") : TextField<String>(id, Model.of(t ?: "")) {

    val inputString: String
        get() = TextUtils.notNull(modelObject, "")

    init {
        outputMarkupId = true
    }

    fun clear() {
        defaultModelObject = ""
    }
}
