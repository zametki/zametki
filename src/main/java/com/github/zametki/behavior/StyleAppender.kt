package com.github.zametki.behavior

import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.Model

class StyleAppender(t: String) : AttributeAppender("style", Model.of(t), " ")
