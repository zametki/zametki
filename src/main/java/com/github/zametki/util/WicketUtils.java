package com.github.zametki.util;

import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WicketUtils {

    public static <V> void reactiveUpdate(@NotNull IModel<V> m, @Nullable V v, @NotNull AjaxRequestTarget target) {
        m.setObject(v);
        target.getPage().send(target.getPage(), Broadcast.BREADTH, new ModelUpdateAjaxEvent(target, m));
    }
}
