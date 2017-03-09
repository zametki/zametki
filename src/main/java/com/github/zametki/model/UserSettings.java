package com.github.zametki.model;

import com.github.mjdbc.type.DbString;
import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.util.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/**
 * Set of user settings serializable to JSON
 */
@SuppressWarnings("SpellCheckingInspection")
public class UserSettings implements DbString {

    @Nullable
    public GroupId lastShownGroup;

    public UserSettings(@NotNull String json) {
        if (json.isEmpty()) {
            return;
        }
        JSONObject obj = new JSONObject(json);
        int lsg = obj.optInt("lsg", 0);
        lastShownGroup = lsg <= 0 ? null : new GroupId(lsg);
    }

    @Override
    public String getDbValue() {
        JSONObject obj = new JSONObject();
        JsonUtils.putIfNotNull(obj, "lsg", lastShownGroup);
        return obj.toString();
    }

    @NotNull
    public static UserSettings get() {
        User user = UserSession.get().getUser();
        if (user == null) {
            return new UserSettings("");
        }
        return user.settings;
    }

    public static void set(@NotNull UserSettings us) {
        User user = UserSession.get().getUser();
        if (user == null) {
            return;
        }
        user.settings = us;
        Context.getUsersDbi().updateSettings(user);
    }
}
