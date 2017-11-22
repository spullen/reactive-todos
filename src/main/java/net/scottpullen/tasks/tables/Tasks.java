package net.scottpullen.tasks.tables;

import net.scottpullen.common.jooq.CustomDataTypes;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.util.postgres.PostgresDataType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class Tasks {
    public static final Table TABLE = table("tasks");

    public static final Field<UUID> ID = field("id", PostgresDataType.UUID);
    public static final Field<UUID> USER_ID = field("user_id", PostgresDataType.UUID);
    public static final Field<String> CONTENT = field("content", PostgresDataType.TEXT);
    public static final Field<String> STATUS = field("status", PostgresDataType.TEXT); // TODO possibly use EnumConverter
    public static final Field<Integer> PRIORITY = field("priority", PostgresDataType.INT);
    public static final Field<LocalDateTime> DUE_DATE = field("due_date", CustomDataTypes.LOCAL_DATE_TIME);
    public static final Field<LocalDateTime> COMPLETED_AT = field("completed_at", CustomDataTypes.LOCAL_DATE_TIME);
    public static final Field<LocalDateTime> CREATED_AT = field("created_at", CustomDataTypes.LOCAL_DATE_TIME);
    public static final Field<LocalDateTime> UPDATED_AT = field("updated_at", CustomDataTypes.LOCAL_DATE_TIME);
}
