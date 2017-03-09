package com.github.zametki.model;

import com.github.mjdbc.type.DbString;
import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.util.JsonUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Set of user settings serializable to JSON
 */
@SuppressWarnings("SpellCheckingInspection")
public class UserSettings implements DbString {

    @NotNull
    public GroupId lastShownGroup = GroupId.INVALID_ID;

    public UserSettings(@NotNull String json) {
        if (json.isEmpty()) {
            return;
        }
        JSONObject obj = new JSONObject(json);
        int lsg = obj.optInt("lsg", 0);
        lastShownGroup = lsg <= 0 ? GroupId.INVALID_ID : new GroupId(lsg);
    }

    @Override
    public String getDbValue() {
        JSONObject obj = new JSONObject();
        JsonUtils.putOpt(obj, !lastShownGroup.equals(GroupId.INVALID_ID), "lsg", lastShownGroup.value);
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
