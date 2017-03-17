package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.event.GroupTreeChangeEvent;
import com.github.zametki.event.GroupTreeChangeType;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FollowingGroupsSelector extends DropDownChoice<GroupId> {

    @NotNull
    private final UserId userId;

    /**
     * Selector follows this model, but do not update it.
     */
    @NotNull
    private final IModel<GroupId> modelToFollow;

    public FollowingGroupsSelector(@NotNull String id, @NotNull UserId userId, @NotNull IModel<GroupId> modelToFollow) {
        super(id);
        this.userId = userId;
        this.modelToFollow = modelToFollow;
        setOutputMarkupId(true);
        setDefaultModel(Model.of(modelToFollow.getObject()));

        setChoices(new LoadableDetachableModel<List<? extends GroupId>>() {
            @Override
            protected List<? extends GroupId> load() {
                return Context.getGroupsDbi().getByUser(userId);
            }
        });

        setChoiceRenderer(new ChoiceRenderer<GroupId>() {
            @Override
            public Object getDisplayValue(GroupId id) {
                Group c = Context.getGroupsDbi().getById(id);
                return c == null ? "???" : c.name;
            }
        });
    }

    @OnModelUpdate
    public void onModelUpdated(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == modelToFollow) {
            setModelObject(modelToFollow.getObject());
            e.target.add(this);
        }
    }

    @OnPayload(GroupUpdateEvent.class)
    public void onGroupUpdate(@NotNull GroupUpdateEvent e) {
        if (e.userId.equals(userId)) {
            e.target.add(this);
        }
    }

    @OnPayload(GroupTreeChangeEvent.class)
    public void onGroupTreeChangeEvent(@NotNull GroupTreeChangeEvent e) {
        if (e.userId.equals(userId)) {
            e.target.add(this);
            if (e.changeType == GroupTreeChangeType.Created) {
                setModelObject(e.groupId);
            }
        }
    }
}
