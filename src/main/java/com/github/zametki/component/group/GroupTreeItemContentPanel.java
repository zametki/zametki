package com.github.zametki.component.group;

import com.github.zametki.model.GroupId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;

import static com.github.zametki.util.WicketUtils.reactiveUpdate;

public class GroupTreeItemContentPanel extends Panel {

    public GroupTreeItemContentPanel(@NotNull String id, @NotNull IModel<GroupTreeNode> model, @NotNull IModel<GroupId> activeGroupModel) {
        super(id);

        GroupTreeNode node = model.getObject();
        GroupId groupId = node.getGroupId();

        AjaxLink<Void> link = new AjaxLink<Void>("link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                reactiveUpdate(activeGroupModel, groupId, target);
            }
        };
        add(link);

        link.add(new GroupEntriesCountBadge("count", groupId));
        link.add(new Label("name", new GroupNameModel(groupId)));

    }

}
