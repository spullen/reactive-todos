package net.scottpullen.tasks.jooq;

import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.entities.TaskPriority;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.tasks.jooq.bindings.TaskIdBinding;
import net.scottpullen.tasks.jooq.bindings.TaskPriorityBinding;
import net.scottpullen.tasks.jooq.bindings.TaskStatusBinding;
import org.jooq.DataType;
import org.jooq.util.postgres.PostgresDataType;

public class TasksDataTypes {
    public static DataType<TaskId> TASK_ID = PostgresDataType.UUID.asConvertedDataType(new TaskIdBinding());
    public static DataType<TaskStatus> TASK_STATUS = PostgresDataType.TEXT.asConvertedDataType(new TaskStatusBinding());
    public static DataType<TaskPriority> TASK_PRIORITY = PostgresDataType.INT.asConvertedDataType(new TaskPriorityBinding());
}
