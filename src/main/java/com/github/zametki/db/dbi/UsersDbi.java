package com.github.zametki.db.dbi;

import com.github.zametki.model.User;
import com.github.zametki.model.UserId;
import com.github.zametki.model.VerificationRecord;
import com.github.zametki.model.VerificationRecordId;
import com.github.zametki.model.VerificationRecordType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UsersDbi {

    @Nullable
    User getUserById(@Nullable UserId userId);

    void createUser(@NotNull User user);

    void updateLastLoginDate(@NotNull User user);

    @Nullable
    User getUserByLogin(@Nullable String login);

    @Nullable
    User getUserByEmail(@Nullable String email);

    void updatePassword(@NotNull User user, @Nullable VerificationRecord r);

    void createVerificationRecord(@NotNull VerificationRecord r);

    @Nullable
    VerificationRecord getVerificationRecordById(@NotNull VerificationRecordId id);

    @Nullable
    VerificationRecord getVerificationRecordByHashAndType(@NotNull String hash, @NotNull VerificationRecordType type);
}