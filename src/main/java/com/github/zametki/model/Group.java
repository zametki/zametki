package com.github.zametki.model;

import com.github.mjdbc.DbMapper;
import com.github.mjdbc.Mapper;
import org.jetbrains.annotations.NotNull;

public class Group extends Identifiable<GroupId> {

    public static final String DEFAULT_GROUP_TITLE = "Без группы";
    public static final int MIN_NAME_LEN = 1;
    public static final int MAX_NAME_LEN = 48;

    @NotNull
    public GroupId parentId = GroupId.ROOT;

    @NotNull
    public UserId userId = UserId.INVALID_ID;

    @NotNull
    public String name = "";

    @Mapper
    public static final DbMapper<Group> MAPPER = r -> {
        Group res = new Group();
        res.id = new GroupId(r.getInt("id"));
        res.parentId = new GroupId(r.getInt("parent_id"));
        res.userId = new UserId(r.getInt("user_id"));
        res.name = r.getString("name");
        return res;
    };


    @Override
    public String toString() {
        return "Group[" + (id == null ? "?" : "" + id.intValue) + "|" + name + "]";
    }

}
