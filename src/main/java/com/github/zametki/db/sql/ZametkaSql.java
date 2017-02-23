package com.github.zametki.db.sql;

import com.github.mjdbc.Bind;
import com.github.mjdbc.BindBean;
import com.github.mjdbc.Sql;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Set of 'zametka' table queries
 */
public interface ZametkaSql {

    @NotNull
    @Sql("INSERT INTO zametka (creation_date, user_id, content, category) " +
            "VALUES (:creationDate, :userId, :content, :categoryId)")
    ZametkaId insert(@BindBean Zametka zametka);

    @Nullable
    @Sql("SELECT * FROM zametka WHERE id = :id")
    Zametka getById(@Bind("id") ZametkaId id);

    @NotNull
    @Sql("SELECT id FROM zametka WHERE user_id = :id ORDER BY creation_date")
    List<ZametkaId> getByUser(@Bind("id") UserId userId);

    @Sql("DELETE FROM zametka WHERE id = :id")
    void delete(@Bind("id") ZametkaId id);

    @Sql("UPDATE zametka SET content = :content, category = :categoryId WHERE id = :id")
    void update(@NotNull @BindBean Zametka z);
}
