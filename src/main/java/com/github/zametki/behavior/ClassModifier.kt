package com.github.zametki.behavior

import org.apache.wicket.AttributeModifier
import org.apache.wicket.model.IModel

class ClassModifier : AttributeModifier {

    constructor(t: String) : super("class", t)

    constructor(m: IModel<String>) : super("class", m)
}

