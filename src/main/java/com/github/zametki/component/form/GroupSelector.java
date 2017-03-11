package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

public class GroupSelector extends DropDownChoice<GroupId> {

    public GroupSelector(@NotNull String id, @NotNull UserId userId, @NotNull GroupId selectedId) {
        super(id, Model.of(selectedId), Context.getGroupsDbi().getByUser(userId));

        setNullValid(false);

        setChoiceRenderer(new ChoiceRenderer<GroupId>() {
            @Override
            public Object getDisplayValue(GroupId id) {
                if (!id.isValid()) {
                    return "Без группы";
                }
                Group group = Context.getGroupsDbi().getById(id);
                return group == null ? "???" : group.name;
            }
        });
    }


}
