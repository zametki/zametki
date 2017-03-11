package com.github.zametki.component.bootstrap;

import com.github.zametki.component.basic.ComponentFactory;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BootstrapModal extends Panel {

    public enum BodyMode {
        Lazy,
        Static
    }

    public enum FooterMode {
        Show,
        Hide
    }

    public final WebMarkupContainer modal = new WebMarkupContainer("modal");
    public final WebMarkupContainer body = new WebMarkupContainer("body");
    public final WebMarkupContainer dialog = new WebMarkupContainer("dialog");
    public final WebMarkupContainer header = new WebMarkupContainer("header");
    public final WebMarkupContainer footer = new WebMarkupContainer("footer");

    @NotNull
    private final ComponentFactory bodyFactory;

    @NotNull
    public final BodyMode bodyMode;

    @NotNull
    private final StringBuilder onCloseJs = new StringBuilder();

    public BootstrapModal(@NotNull String id, @Nullable String title, @NotNull ComponentFactory bodyFactory, @NotNull BodyMode bodyMode, @NotNull FooterMode footerMode) {
        super(id);
        this.bodyFactory = bodyFactory;
        this.bodyMode = bodyMode;

        modal.setOutputMarkupId(true);
        add(modal);

        modal.add(dialog);

        dialog.add(header);
        if (title != null) {
            header.add(new Label("title", title));
        } else {
            header.setVisible(false);
        }

        body.setOutputMarkupId(true);
        body.add(bodyMode == BodyMode.Lazy ? new EmptyPanel("body_content") : bodyFactory.create("body_content"));
        body.add(new WebMarkupContainer("in_body_close").setVisible(!header.isVisible()));
        dialog.add(body);

        footer.setVisible(footerMode == FooterMode.Show);
        dialog.add(footer);
    }

    public void show(AjaxRequestTarget target) {
        if (bodyMode != BodyMode.Lazy) {
            return;
        }
        body.replace(bodyFactory.create("body_content"));
        target.add(body);
    }

    public void hide(AjaxRequestTarget target) {
        target.appendJavaScript("$('#" + modal.getMarkupId() + "').modal('hide');");
    }

    @SuppressWarnings("unused")
    public void setOnCloseJavascript(AjaxRequestTarget target, String js) {
        target.appendJavaScript(wrapOnCloseJs(js));
    }

    @NotNull
    private String wrapOnCloseJs(@NotNull CharSequence js) {
        return "$('#" + modal.getMarkupId() + "').on('hidden.bs.modal', function(){" + js + "})";
    }

    public void addOnCloseJs(@NotNull String js) {
        onCloseJs.append(js);
    }

    public String getDataTargetId() {
        return modal.getMarkupId();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript(wrapOnCloseJs(onCloseJs)));
    }
}
