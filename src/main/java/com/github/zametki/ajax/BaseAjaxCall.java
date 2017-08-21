package com.github.zametki.ajax;


import com.github.openjson.JSONObject;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.http.WebResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseAjaxCall extends WebPage {

    private static final Logger log = LoggerFactory.getLogger(BaseAjaxCall.class);

    public BaseAjaxCall() {

        getRequestCycle().scheduleRequestHandlerAfterCurrent(new IRequestHandler() {

            public void detach(IRequestCycle requestCycle) {
                // Nothing to do here.
            }

            public void respond(IRequestCycle requestCycle) {
                // Add JSON-encoded string to the response.
                WebResponse response = (WebResponse) requestCycle.getResponse();
                response.setContentType(getResponseContentType());
                try {
                    response.write(getResponseText());
                } catch (Exception e) {
                    log.error("Error processing request:" + getPage(), e);
                    response.write(error("internal error"));
                }
            }
        });
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
    public String success() {
        return new JSONObject().put("success", true).toString();
    }
}