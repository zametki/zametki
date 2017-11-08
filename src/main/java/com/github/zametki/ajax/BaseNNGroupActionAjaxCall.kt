package com.github.zametki.ajax

import com.github.zametki.model.Group

abstract class BaseNNGroupActionAjaxCall : BaseGroupActionAjaxCall() {

    @Throws(Exception::class)
    override fun getResponseText(group: Group?) = if (group == null) error("Group not found") else getResponseTextNN(group)

    @Throws(Exception::class)
    protected abstract fun getResponseTextNN(group: Group): String
}
