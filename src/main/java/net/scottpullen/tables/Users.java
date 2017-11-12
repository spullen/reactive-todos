package net.scottpullen.tables;

import net.scottpullen.utils.CustomDataTypes;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.util.postgres.PostgresDataType;

import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class Users {
    public static final Table TABLE = table("users");

    public static final Field<UUID> ID = field("id", PostgresDataType.UUID);
    public static final Field<String> EMAIL = field("email", PostgresDataType.TEXT);
    public static final Field<String> FULL_NAME = field("full_name", PostgresDataType.TEXT);
    public static final Field<String> PASSWORD_DIGEST = field("password_digest", PostgresDataType.TEXT);
    public static final Field CREATED_AT = field("created_at", CustomDataTypes.LOCAL_DATE_TIME);
    public static final Field UPDATED_AT = field("updated_at", CustomDataTypes.LOCAL_DATE_TIME);
}
