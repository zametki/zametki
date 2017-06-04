package com.github.zametki.component.react;

import com.github.zametki.component.basic.ContainerWithId;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;

public class ReactGroupTreePanel extends Panel {

    private final ContainerWithId panel = new ContainerWithId("panel");

    public ReactGroupTreePanel(String id) {
        super(id);
        add(panel);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript("$site.ReactUtils.renderGroupTree('" + panel.getMarkupId() + "')"));
    }
}
