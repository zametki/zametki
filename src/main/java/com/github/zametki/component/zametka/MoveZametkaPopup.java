package com.github.zametki.component.zametka;

import com.github.zametki.Context;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.bootstrap.BootstrapModalCloseLink;
import com.github.zametki.component.form.GroupSelector;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.jetbrains.annotations.NotNull;

public class MoveZametkaPopup extends Panel {

    public MoveZametkaPopup(@NotNull String id, @NotNull ZametkaId zametkaId) {
        super(id);

        Form form = new Form("form");
        add(form);

        Zametka zametka = Context.getZametkaDbi().getById(zametkaId);
        if (zametka == null) {
            //todo: ??
            setVisible(false);
            return;

        }
        UserId userId = WebUtils.getUserIdOrRedirectHome();
        GroupId groupId = zametka.groupId;
        GroupSelector groupSelector = new GroupSelector("group_selector", userId, groupId);
        form.add(groupSelector);

        form.add(new BootstrapModalCloseLink("cancel_link"));
        form.add(new AjaxSubmitLink("move_link") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                GroupId newGroupId = groupSelector.getConvertedInput();
                String hideJs = BootstrapModal.hideJs(form.getMarkupId());
                if (newGroupId == null || newGroupId.equals(groupId)) {
                    target.prependJavaScript(hideJs);
                    return;
                }
                Zametka zametka = Context.getZametkaDbi().getById(zametkaId);
                if (zametka == null) {
                    target.prependJavaScript(hideJs);
                    return;
                }
                zametka.groupId = newGroupId;
                Context.getZametkaDbi().update(zametka);
                target.prependJavaScript(hideJs);
                send(getPage(), Broadcast.BREADTH, new ZametkaUpdateEvent(target, zametkaId, ZametkaUpdateType.GROUP_CHANGED));
            }
        });
    }
}
