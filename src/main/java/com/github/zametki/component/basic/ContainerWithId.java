package com.github.zametki.component.basic;

import org.apache.wicket.markup.html.WebMarkupContainer;

public class ContainerWithId extends WebMarkupContainer {
    public ContainerWithId(String id) {
        super(id);
        setOutputMarkupId(true);
    }
}
