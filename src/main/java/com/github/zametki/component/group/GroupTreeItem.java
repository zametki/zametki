package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.behavior.ClassAppender;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.github.zametki.util.WicketUtils.reactiveUpdate;

public class GroupTreeItem extends Panel {

    public GroupTreeItem(@NotNull String id, @NotNull GroupId groupId, @NotNull IModel<GroupId> groupModel) {
        super(id);
        Group group = Context.getGroupsDbi().getById(groupId);
        String name = group == null ? "???" : group.name;
        AjaxLink<Void> link = new AjaxLink<Void>("link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                reactiveUpdate(groupModel, groupId, target);
            }
        };
        if (Objects.equals(groupId, groupModel.getObject())) {
            link.add(new ClassAppender("active"));
        }
        add(link);

        UserId userId = UserSession.get().getUserId();
        int count = userId == null ? 0 : Context.getZametkaDbi().countByCategory(userId, groupId);
        link.add(new Label("count", "" + count).setVisible(count > 0));
        link.add(new Label("name", name));
    }

}
