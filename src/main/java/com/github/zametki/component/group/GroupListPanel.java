package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.behavior.ClassAppender;
import com.github.zametki.behavior.StyleAppender;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.event.GroupTreeChangeEvent;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.util.AbstractListProvider;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static com.github.zametki.util.WicketUtils.reactiveUpdate;

public class GroupListPanel extends Panel {

    private final GroupsProvider provider = new GroupsProvider();

    private final ContainerWithId panel = new ContainerWithId("panel");

    @NotNull
    private final IModel<GroupId> groupModel;

    public GroupListPanel(String id, @NotNull IModel<GroupId> activeGroupModel) {
        super(id);
        this.groupModel = activeGroupModel;

        add(panel);

        panel.add(new DataView<GroupTreeNode>("group", provider) {
            @Override
            protected void populateItem(Item<GroupTreeNode> item) {
                GroupTreeNode node = item.getModelObject();
                GroupId groupId = node.getGroupId();
                AjaxLink<Void> link = new AjaxLink<Void>("link") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        reactiveUpdate(activeGroupModel, groupId, target);
                    }
                };
                if (Objects.equals(groupId, activeGroupModel.getObject())) {
                    link.add(new ClassAppender("active"));
                }
                item.add(link);

                UserId userId = WebUtils.getUserIdOrRedirectHome();
                int count = Context.getZametkaDbi().countByGroup(userId, groupId);
                link.add(new Label("count", "" + count).setVisible(count > 0));

                Group group = Context.getGroupsDbi().getById(groupId);
                Label nameLabel = new Label("name", group == null ? "???" : group.name);
                if (node.getLevel() > 1) {
                    nameLabel.add(new StyleAppender("padding-left:" + (10 * (node.getLevel() - 1) + "px;")));
                }
                link.add(nameLabel);
            }
        });
    }

    @OnPayload(GroupTreeChangeEvent.class)
    public void onGroupTreeChanged(GroupTreeChangeEvent e) {
        provider.detach();
        e.target.add(panel);
    }

    @OnPayload(GroupUpdateEvent.class)
    public void onGroupUpdated(GroupUpdateEvent e) {
        provider.detach();
        e.target.add(panel);
    }


    @OnModelUpdate
    public void onModelUpdateAjaxEvent(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == groupModel) {
            provider.detach();
            e.target.add(panel);
        }
    }

    private static class GroupsProvider extends AbstractListProvider<GroupTreeNode> {

        @Override
        public List<GroupTreeNode> getList() {
            return GroupTreeModel.build(WebUtils.getUserOrRedirectHome()).flatList();
        }

        @Override
        public IModel<GroupTreeNode> model(GroupTreeNode o) {
            return Model.of(o);
        }
    }
}
