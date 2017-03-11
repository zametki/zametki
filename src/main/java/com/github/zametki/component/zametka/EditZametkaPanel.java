package com.github.zametki.component.zametka;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.form.InputArea;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.JsUtils;
import com.github.zametki.util.TextUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.jetbrains.annotations.NotNull;

public class EditZametkaPanel extends Panel {

    @NotNull
    public final InputArea contentField;

    public EditZametkaPanel(@NotNull String id, @NotNull ZametkaId zametkaId, @NotNull AjaxCallback closeCallback) {
        super(id);

        Form form = new Form("form");
        add(form);

        contentField = new InputArea("content_field", new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                Zametka z = Context.getZametkaDbi().getById(zametkaId);
                return z == null ? "???" : z.content;
            }
        });
        form.add(contentField);

        form.add(new AjaxLink<Void>("cancel_link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                closeCallback.callback(target);
            }
        });

        AjaxSubmitLink saveLink = new AjaxSubmitLink("save_link") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                String content = contentField.getInputString();
                if (TextUtils.isEmpty(content)) {
                    return; //todo: add validation
                }
                Zametka z = Context.getZametkaDbi().getById(zametkaId);
                if (z == null) { // todo: report error
                    return;
                }
                z.content = content;
                Context.getZametkaDbi().update(z);
                send(getPage(), Broadcast.BREADTH, new ZametkaUpdateEvent(target, zametkaId, ZametkaUpdateType.CONTENT_CHANGED));
                closeCallback.callback(target);
            }
        };
        form.add(saveLink);

        JsUtils.clickOnCtrlEnter(contentField, saveLink);
    }
}
