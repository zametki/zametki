package com.github.zametki.component.basic;

import org.apache.wicket.markup.html.WebMarkupContainer;

public class InvisibleBlock extends WebMarkupContainer {
    public InvisibleBlock(String id) {
        super(id);
        setVisible(false);
    }
}
