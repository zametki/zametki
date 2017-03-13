package com.github.zametki.model;

import com.github.mjdbc.DbMapper;
import com.github.mjdbc.Mapper;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class Zametka extends Identifiable<ZametkaId> {

    public static final int MIN_CONTENT_LEN = 1;
    public static final int MAX_CONTENT_LEN = 50_000;

    @NotNull
    public UserId userId = UserId.INVALID_ID;

    @NotNull
    public Instant creationDate = Instant.MIN;

    @NotNull
    public String content = "";

    @NotNull
    public GroupId groupId = GroupId.UNDEFINED;

    @Mapper
    public static final DbMapper<Zametka> MAPPER = r -> {
        Zametka res = new Zametka();
        res.id = new ZametkaId(r.getInt("id"));
        res.userId = new UserId(r.getInt("user_id"));
        res.creationDate = r.getTimestamp("creation_date").toInstant();
        res.content = r.getString("content");
        res.groupId = new GroupId(r.getInt("group_id"));
        return res;
    };

    @Override
    public String toString() {
        return "Z[" + (id == null ? "?" : "" + id.value) + "]";
    }

}
