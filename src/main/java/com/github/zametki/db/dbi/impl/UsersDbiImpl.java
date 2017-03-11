package com.github.zametki.db.dbi.impl;

import com.github.mjdbc.Db;
import com.github.zametki.Context;
import com.github.zametki.db.dbi.AbstractDbi;
import com.github.zametki.db.dbi.UsersDbi;
import com.github.zametki.db.sql.UsersSql;
import com.github.zametki.db.sql.VerificationRecordSql;
import com.github.zametki.model.Group;
import com.github.zametki.model.User;
import com.github.zametki.model.UserId;
import com.github.zametki.model.VerificationRecord;
import com.github.zametki.model.VerificationRecordId;
import com.github.zametki.model.VerificationRecordType;
import com.github.zametki.util.TextUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Database access interface for user data
 */
public final class UsersDbiImpl extends AbstractDbi implements UsersDbi {

    @NotNull
    private final UsersSql usersSql;

    @NotNull
    private final VerificationRecordSql vrSql;

    public UsersDbiImpl(@NotNull Db db) {
        super(db);
        usersSql = db.attachSql(UsersSql.class);
        vrSql = db.attachSql(VerificationRecordSql.class);
    }

    @Override
    @Nullable
    public User getUserById(@Nullable UserId userId) {
        return userId == null ? null : usersSql.selectUserById(userId);
    }


    /**
     * Creates user, assigns uid and id. Repacks personal info and settings.
     */
    @Override
    public void createUser(@NotNull User user) {
        user.uid = UUID.randomUUID().toString();
        user.id = usersSql.insertUser(user);

        Group g = new Group();
        g.userId = user.id;
        g.name = Group.WITHOUT_GROUP_TITLE;
        Context.getGroupsDbi().create(g);

        user.rootGroupId = g.id;
        usersSql.updateRootGroup(user);
    }

    @Override
    public void updateLastLoginDate(@NotNull User user) {
        user.lastLoginDate = Instant.now();
        usersSql.updateLastLoginDate(user);
    }

    @Override
    @Nullable
    public User getUserByLogin(@Nullable String login) {
        return TextUtils.isEmpty(login) ? null : usersSql.selectUserByLogin(login);
    }

    @Override
    @Nullable
    public User getUserByEmail(@Nullable String email) {
        return TextUtils.isEmpty(email) ? null : usersSql.selectUserByEmail(email);
    }

    @Override
    public void updatePassword(@NotNull User user, @Nullable VerificationRecord r) {
        assertTrue(r == null || (r.type == VerificationRecordType.PasswordReset && user.id.equals(r.userId)),
                () -> "Некорректная запись: " + r + " user: " + user);
        usersSql.updatePasswordHash(user.id, user.passwordHash);
        if (r != null) {
            vrSql.updateVerificationDate(r.id, new Date());
        }
    }

    @Override
    public void updateSettings(@NotNull User user) {
        usersSql.updateSettings(user);
    }

    /**
     * Creates verification record. Assigns hash to it.
     */
    @Override
    public void createVerificationRecord(@NotNull VerificationRecord r) {
        r.hash = UUID.randomUUID().toString();
        r.id = vrSql.insertVerificationRecord(r);
    }

    @Override
    public VerificationRecord getVerificationRecordById(@NotNull VerificationRecordId id) {
        return vrSql.selectVerificationRecordById(id);
    }

    public VerificationRecord getVerificationRecordByHashAndType(@NotNull String hash, @NotNull VerificationRecordType type) {
        return vrSql.selectVerificationRecordByHashAndType(hash, type);
    }
}
