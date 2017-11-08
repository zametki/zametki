package com.github.zametki.ajax


import com.github.openjson.JSONObject
import com.github.zametki.annotation.Post
import com.github.zametki.util.UserSessionUtils
import org.apache.wicket.request.resource.AbstractResource
import org.apache.wicket.request.resource.IResource
import org.apache.wicket.util.string.StringValue
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.OutputStreamWriter

abstract class BaseAjaxCall : AbstractResource() {
    companion object {
        private val log = LoggerFactory.getLogger(BaseAjaxCall::class.java)
    }

    private var isPost: Boolean = false
    private var attributes: IResource.Attributes? = null

    protected open fun getResponseContentType() = "application/json;charset=utf-8"

    protected abstract fun getResponseText(): String

    override fun newResourceResponse(attributes: IResource.Attributes): AbstractResource.ResourceResponse {
        UserSessionUtils.initializeSession()
        isPost = javaClass.isAnnotationPresent(Post::class.java)
        this.attributes = attributes
        val response = AbstractResource.ResourceResponse()
        response.contentType = getResponseContentType()
        response.setTextEncoding("UTF-8")
        response.disableCaching()
        response.writeCallback = object : AbstractResource.WriteCallback() {
            @Throws(IOException::class)
            override fun writeData(attributes: IResource.Attributes) {
                val outputStream = attributes.response.outputStream
                val writer = OutputStreamWriter(outputStream)
                try {
                    writer.write(getResponseText())
                } catch (e: Exception) {
                    log.error("Error processing request:" + javaClass.name, e)
                    writer.write(error("internal error"))
                }

                writer.flush()
            }
        }
        return response
    }

    fun error(message: String) = JSONObject().put("error", message).toString()

    fun permissionError() = error("Permission denied")

    fun success() = JSONObject().put("success", true).toString()

    fun getParameter(name: String): StringValue = if (isPost) attributes!!.request.postParameters.getParameterValue(name) else attributes!!.parameters.get(name)
}
