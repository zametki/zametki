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

    private final ContainerWithId workspaceView = new ContainerWithId("workspace");

    public WorkspacePage(PageParameters pp) {
        super(pp);
        add(workspaceView);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript(getInitScript()));
    }

    @NotNull
    public String getInitScript() {
        JSONArray groups = AjaxApiUtils.INSTANCE.getGroups(WebUtils.getUserIdOrRedirectHome());

        JSONObject initContext = new JSONObject();
        initContext.put("groups", groups);
        initContext.put("workspaceElementId", workspaceView.getMarkupId());
        return "$site.Server2Client.init(" + initContext.toString() + ");";
    }

    static {
        AjaxApiUtils.INSTANCE.getROOT_GROUP().name = "root";
    }

}
