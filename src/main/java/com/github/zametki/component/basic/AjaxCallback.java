package com.github.zametki.component.basic;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;
import org.jetbrains.annotations.NotNull;

public interface AjaxCallback extends IClusterable {

    void callback(@NotNull AjaxRequestTarget target);

}
