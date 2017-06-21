package com.github.zametki.component;

import com.github.zametki.model.GroupId;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IClusterable;
import org.jetbrains.annotations.NotNull;

public class WorkspacePageState implements IClusterable {

    @NotNull
    public final IModel<GroupId> activeGroupModel = Model.of();

}