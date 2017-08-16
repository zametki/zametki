package com.github.zametki.component.group;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Constants;
import com.github.zametki.Context;
import com.github.zametki.component.WorkspacePageState;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.provider.LentaProvider;
import com.github.zametki.util.ZDateFormat;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NotesViewPanel extends Panel {

    @NotNull
    private final LentaProvider lentaProvider;

    @NotNull
    private final ContainerWithId panel = new ContainerWithId("panel");

    public NotesViewPanel(@Nullable String id, @NotNull WorkspacePageState state) {
        super(id);
        lentaProvider = new LentaProvider(state);
        add(panel);
    }

    @OnModelUpdate
    public void onModelUpdate(@NotNull ModelUpdateAjaxEvent e) {
        WorkspacePageState state = lentaProvider.state;
        if (e.model == state.activeGroupModel) { // active group changed -> update list
            update(e.target);
        }
    }

    @OnPayload(GroupUpdateEvent.class)
    public void onGroupUpdated(GroupUpdateEvent e) {
        update(e.target);
    }

    private void update(@NotNull AjaxRequestTarget target) {
        lentaProvider.detach();
        target.appendJavaScript(getUpdateScript());
    }

    @OnPayload(ZametkaUpdateEvent.class)
    public void onZametkaUpdated(ZametkaUpdateEvent e) {
        update(e.target);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript(getUpdateScript() + ";$site.Server2Client.renderNotesView('" + panel.getMarkupId() + "')"));
    }

    @NotNull
    public String getUpdateScript() {
        JSONArray notes = new JSONArray();
        for (ZametkaId id : lentaProvider.getList()) {
            Zametka note = Context.getZametkaDbi().getById(id);
            if (note == null) {
                continue;
            }
            notes.put(toJSON(note));
        }
        return "$site.Server2Client.dispatchUpdateNotesListAction(" + notes.toString() + ");";
    }

    private static final ZDateFormat DF = ZDateFormat.getInstance("dd MMMM yyyy", Constants.MOSCOW_TZ);

    @NotNull
    private JSONObject toJSON(@NotNull Zametka z) {
        JSONObject json = new JSONObject();
        json.put("id", z.id.intValue);
        json.put("group", z.groupId.intValue);
        json.put("body", z.content);
        json.put("dateText", DF.format(z.creationDate));
        return json;
    }
}
