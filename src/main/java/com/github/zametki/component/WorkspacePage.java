package com.github.zametki.component;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.ajax.AjaxApiUtils;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.user.BaseUserPage;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jetbrains.annotations.NotNull;

@MountPath("/my")
public class WorkspacePage extends BaseUserPage {

    private final ContainerWithId navbarView = new ContainerWithId("navbar");
    private final ContainerWithId groupsView = new ContainerWithId("groups");
    private final ContainerWithId notesView = new ContainerWithId("notes");

    public WorkspacePage(PageParameters pp) {
        super(pp);

        add(navbarView);
        add(groupsView);
        add(notesView);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript(getInitScript()));
    }

    @NotNull
    public String getInitScript() {
        JSONArray groups = AjaxApiUtils.getGroups(WebUtils.getUserIdOrRedirectHome());

        JSONObject initContext = new JSONObject();
        initContext.put("groups", groups);
        initContext.put("notesViewId", notesView.getMarkupId());
        initContext.put("navbarViewId", navbarView.getMarkupId());
        initContext.put("groupsViewId", groupsView.getMarkupId());
        return "$site.Server2Client.init(" + initContext.toString() + ");";
    }

    static {
        AjaxApiUtils.ROOT_GROUP.name = "root";
    }

}
