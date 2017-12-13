package net.scottpullen.tasks.tables;

import net.scottpullen.common.jooq.CustomDataTypes;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.entities.TaskPriority;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.tasks.jooq.TasksDataTypes;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.users.jooq.UsersDataTypes;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.util.postgres.PostgresDataType;

import java.time.LocalDateTime;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class Tasks {
    public static final Table TABLE = table("tasks");

    public static final Field<TaskId> ID = field("id", TasksDataTypes.TASK_ID);
    public static final Field<UserId> USER_ID = field("user_id", UsersDataTypes.USER_ID);
    public static final Field<String> CONTENT = field("content", PostgresDataType.TEXT);
    public static final Field<String> NOTES = field("notes", PostgresDataType.TEXT);
    public static final Field<TaskStatus> STATUS = field("status", TasksDataTypes.TASK_STATUS);
    public static final Field<TaskPriority> PRIORITY = field("priority", TasksDataTypes.TASK_PRIORITY);
    public static final Field<LocalDateTime> DUE_DATE = field("due_date", CustomDataTypes.LOCAL_DATE_TIME);
    public static final Field<LocalDateTime> COMPLETED_AT = field("completed_at", CustomDataTypes.LOCAL_DATE_TIME);
    public static final Field<LocalDateTime> CREATED_AT = field("created_at", CustomDataTypes.LOCAL_DATE_TIME);
    public static final Field<LocalDateTime> UPDATED_AT = field("updated_at", CustomDataTypes.LOCAL_DATE_TIME);
    public static final Field<LocalDateTime> DELETED_AT = field("deleted_at", CustomDataTypes.LOCAL_DATE_TIME);
}
