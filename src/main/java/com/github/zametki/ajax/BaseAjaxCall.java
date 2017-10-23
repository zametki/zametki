package com.github.zametki.ajax;


import com.github.openjson.JSONObject;
import com.github.zametki.annotation.Post;
import com.github.zametki.util.UserSessionUtils;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.util.string.StringValue;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public abstract class BaseAjaxCall extends AbstractResource {

    private static final Logger log = LoggerFactory.getLogger(BaseAjaxCall.class);
    private boolean isPost;
    private Attributes attributes;

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {
        UserSessionUtils.initializeSession();
        isPost = getClass().isAnnotationPresent(Post.class);
        this.attributes = attributes;
        ResourceResponse response = new ResourceResponse();
        response.setContentType(getResponseContentType());
        response.setTextEncoding("utf-8");
        response.disableCaching();
        response.setWriteCallback(new WriteCallback() {
            @Override
            public void writeData(Attributes attributes) throws IOException {
                OutputStream outputStream = attributes.getResponse().getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream);
                try {
                    writer.write(getResponseText());
                } catch (Exception e) {
                    log.error("Error processing request:" + getClass().getName(), e);
                    writer.write(error("internal error"));
                }
                writer.flush();
            }
        });
        return response;
    }

    @NotNull
    protected String getResponseContentType() {
        return "application/json;charset=utf-8";
    }

    @NotNull
    protected abstract String getResponseText() throws Exception;

    @NotNull
    public String error(@NotNull String message) {
        return new JSONObject().put("error", message).toString();
    }

    @NotNull
    public String permissionError() {
        return error("Permission denied");
    }

    @NotNull
    public String success() {
        return new JSONObject().put("success", true).toString();
    }

    public StringValue getParameter(@NotNull String name) {
        return isPost ? attributes.getRequest().getPostParameters().getParameterValue(name) : attributes.getParameters().get(name);
    }
}
