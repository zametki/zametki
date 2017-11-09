package com.github.zametki;

import com.github.zametki.model.User;
import com.github.zametki.model.UserId;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;

public class UserSession extends WebSession {
    private static final Logger log = LoggerFactory.getLogger(UserSession.class);

    @Nullable
    private UserId userId;

    private boolean initialized;

    @Nullable
    public String ip;

    public UserSession(Request request) {
        super(request);
        setLocale(Constants.RUSSIAN_LOCALE);
    }

    public boolean isSignedIn() {
        return userId != null;
    }

    public static UserSession get() {
        return (UserSession) WebSession.get();
    }

    @Nullable
    public User getUser() {
        return userId == null ? null : Context.getUsersDbi().getUserById(userId);
    }

    public void setUser(@NotNull User user) {
        this.userId = user.getId();
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void cleanOnLogout() {
        userId = null;
        setInitialized(false);
        getAttributeNames().forEach(this::removeAttribute);
        clear();
    }

    @Nullable
    public UserId getUserId() {
        return userId;
    }

    public String toString() {
        String res = "session:" + hashCode();
        User user = getUser();
        if (user != null) {
            res += "|user:" + user.getLogin();
        }
        res += "|ip:" + ip;
        return res;
    }


    public void touch() {
        HttpServletRequest request = ((HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest());
        HttpSession httpSession = request.getSession();
        touch(httpSession);
    }

    @SuppressWarnings("JavaReflectionMemberAccess")
    private static void touch(HttpSession session) {
        try {
            Field f = session.getClass().getDeclaredField("session");
            f.setAccessible(true);
            HttpSession realSession = (HttpSession) f.get(session);
            realSession.getClass().getMethod("access").invoke(realSession);
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
