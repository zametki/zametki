package com.github.zametki.event.dispatcher;

import com.github.zametki.ZApplication;
import org.apache.wicket.Component;
import org.apache.wicket.IEventDispatcher;
import org.apache.wicket.event.IEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class ModelUpdatesDispatcher implements IEventDispatcher {

    // Optimization -> deliver events only to app components.
    private static final String APP_PACKAGE = ZApplication.class.getPackage().getName();

    @Override
    public void dispatchEvent(@NotNull Object sink, @NotNull IEvent event, @Nullable Component component) {
        Object payload = event.getPayload();
        if (payload == null || !(payload instanceof ModelUpdateAjaxEvent) || !sink.getClass().getName().startsWith(APP_PACKAGE)) {
            return;
        }
        for (Method method : sink.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(OnModelUpdate.class)) {
                try {
                    Class[] parTypes = method.getParameterTypes();
                    //noinspection unchecked
                    if (parTypes.length == 1 && parTypes[0].isAssignableFrom(ModelUpdateAjaxEvent.class)) {
                        method.invoke(sink, payload);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Exception when delivering model update to component " + sink.getClass() + " and method " + method.getName(), e);
                }
            }
        }
    }
}
