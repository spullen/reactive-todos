package net.scottpullen.users.tables;

import net.scottpullen.users.entities.UserId;
import net.scottpullen.common.jooq.CustomDataTypes;
import net.scottpullen.users.jooq.UsersDataTypes;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.util.postgres.PostgresDataType;

import java.time.LocalDateTime;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class Users {
    public static final Table TABLE = table("users");

    public static final Field<UserId> ID = field("id", UsersDataTypes.USER_ID);
    public static final Field<String> EMAIL = field("email", PostgresDataType.TEXT);
    public static final Field<String> FULL_NAME = field("full_name", PostgresDataType.TEXT);
    public static final Field<String> PASSWORD_DIGEST = field("password_digest", PostgresDataType.TEXT);
    public static final Field<LocalDateTime> CREATED_AT = field("created_at", CustomDataTypes.LOCAL_DATE_TIME);
    public static final Field<LocalDateTime> UPDATED_AT = field("updated_at", CustomDataTypes.LOCAL_DATE_TIME);
}
