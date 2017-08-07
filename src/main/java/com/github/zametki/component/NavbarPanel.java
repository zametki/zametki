package com.github.zametki.component;

import com.github.zametki.component.basic.ContainerWithId;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;

public class NavbarPanel extends Panel {

    private final ContainerWithId navbar = new ContainerWithId("navbar");

    public NavbarPanel(String id) {
        super(id);
        add(navbar);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript("$site.Server2Client.renderNavbarView('" + navbar.getMarkupId() + "')"));
    }

}
