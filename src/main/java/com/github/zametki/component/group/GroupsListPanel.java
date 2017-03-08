package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.behavior.ClassAppender;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.event.UserGroupUpdatedEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.util.AbstractListProvider;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IClusterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.zametki.util.WicketUtils.reactiveUpdate;

public class GroupsListPanel extends Panel {

    private final ContainerWithId panel = new ContainerWithId("panel");

    private final GroupsProvider provider = new GroupsProvider();

    @NotNull
    private final IModel<GroupId> groupModel;

    public GroupsListPanel(String id, @NotNull IModel<GroupId> groupModel) {
        super(id);
        this.groupModel = groupModel;

        add(panel);

        panel.add(new DataView<NavbarOption>("cat", provider) {
            @Override
            protected void populateItem(Item<NavbarOption> item) {
                NavbarOption o = item.getModelObject();
                AjaxLink<Void> link = new AjaxLink<Void>("link") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        reactiveUpdate(groupModel, o.groupId, target);
                    }
                };
                if (Objects.equals(o.groupId, groupModel.getObject())) {
                    link.add(new ClassAppender("active"));
                }
                item.add(link);

                UserId userId = UserSession.get().getUserId();
                int count = userId == null || o.groupId == null ? 0 : Context.getZametkaDbi().countByCategory(userId, o.groupId);
                link.add(new Label("count", "" + count).setVisible(count > 0));
                link.add(new Label("title", o.title));
            }
        });
    }

    @OnPayload(UserGroupUpdatedEvent.class)
    public void onCategoriesUpdated(UserGroupUpdatedEvent e) {
        provider.detach();
        e.target.add(panel);
    }

    private static class NavbarOption implements IClusterable {
        @Nullable
        private final GroupId groupId;
        @NotNull
        private final String title;

        public NavbarOption(@Nullable GroupId groupId, @NotNull String title) {
            this.groupId = groupId;
            this.title = title;
        }
    }

    @OnModelUpdate
    public void onModelUpdateAjaxEvent(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == groupModel) {
            provider.detach();
            e.target.add(panel);
        }
    }

    @OnPayload(UserGroupUpdatedEvent.class)
    public void onUserCategoriesUpdatedEvent(UserGroupUpdatedEvent e) {
        provider.detach();
        e.target.add(panel);
    }

    private static class GroupsProvider extends AbstractListProvider<NavbarOption> {

        @Override
        public List<NavbarOption> getList() {
            List<NavbarOption> list = new ArrayList<>();
            list.add(new NavbarOption(null, "Все"));
            List<GroupId> userCategories = Context.getGroupsDbi().getByUser(UserSession.get().getUserId());
            userCategories.stream()
                    .map(id -> Context.getGroupsDbi().getById(id))
                    .filter(Objects::nonNull)
                    .forEach(c -> list.add(new NavbarOption(c.id, c.title)));
            return list;
        }

        @Override
        public IModel<NavbarOption> model(NavbarOption o) {
            return Model.of(o);
        }
    }
}
