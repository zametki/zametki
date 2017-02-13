package com.github.zametki.model;

import com.github.mjdbc.DbMapper;
import com.github.mjdbc.Mapper;
import com.github.zametki.util.UDate;
import org.jetbrains.annotations.NotNull;

public class Zametka extends Identifiable<ZametkaId> {

    @NotNull
    public UserId userId = UserId.INVALID_ID;

    @NotNull
    public UDate creationDate = UDate.UNDEFINED;

    @NotNull
    public String content = "";

    @NotNull
    public CategoryId categoryId = CategoryId.INVALID_ID;

    @Mapper
    public static final DbMapper<Zametka> MAPPER = r -> {
        Zametka res = new Zametka();
        res.id = new ZametkaId(r.getInt("id"));
        res.userId = new UserId(r.getInt("user_id"));
        res.creationDate = UDate.fromDate(r.getTimestamp("creation_date"));
        res.content = r.getString("content");
        res.categoryId = new CategoryId(r.getInt("category"));
        return res;
    };

    @Override
    public String toString() {
        return "Z[" + (id == null ? "?" : "" + id.value) + "]";
    }

}
