package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.component.group.GroupTreeModel;
import com.github.zametki.component.group.GroupTreeNode;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

public class ParentGroupSelector extends DropDownChoice<GroupTreeNode> {

    public ParentGroupSelector(@NotNull String id, @NotNull Group group, @NotNull GroupTreeModel treeModel) {
        super(id);

        GroupTreeNode currentParentNode = treeModel.nodeByGroup.get(group.parentId);
        setDefaultModel(Model.of(currentParentNode));
        setEscapeModelStrings(false);

        GroupTreeNode node = treeModel.nodeByGroup.get(group.id);
        setChoices(treeModel.flatListWithoutBranch(node));
        setEnabled(!getChoices().isEmpty());
        setNullValid(false);
        GroupId rootId = ((GroupTreeNode) treeModel.getRoot()).getGroupId();
        setChoiceRenderer(new ChoiceRenderer<GroupTreeNode>() {
            @Override
            public Object getDisplayValue(GroupTreeNode node) {
                GroupId id = node.getGroupId();
                if (id.equals(rootId)) {
                    return "Нет (верхний уровень)";
                }
                Group group = Context.getGroupsDbi().getById(id);
                String name = group == null ? "???" : group.name;
                String safeName = StringEscapeUtils.escapeHtml4(name);
                return StringUtils.repeat("&nbsp;", 4 * (node.getLevel() - 1)) + safeName;
            }
        });
    }


}
