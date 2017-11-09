package com.github.zametki.component.form

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.html.image.Image

class RefreshCaptchaLink(id: String, private val captchaField: CaptchaField, private val captchaImage: Image) : AjaxLink<Void>(id) {

    override fun onClick(target: AjaxRequestTarget) {
        captchaField.change()
        captchaField.invalidate()
        target.add(captchaImage)
        target.add(captchaField)
    }
}
