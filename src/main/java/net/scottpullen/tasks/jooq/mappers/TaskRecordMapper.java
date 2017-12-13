package net.scottpullen.tasks.jooq.mappers;

import net.scottpullen.tasks.entities.Task;
import net.scottpullen.tasks.tables.Tasks;
import org.jooq.Record;
import org.jooq.RecordMapper;

public class TaskRecordMapper implements RecordMapper<Record, Task> {
    @Override
    public Task map(Record record) {
        return Task.builder()
            .withId(record.get(Tasks.ID))
            .withUserId(record.get(Tasks.USER_ID))
            .withContent(record.get(Tasks.CONTENT))
            .withNotes(record.get(Tasks.NOTES))
            .withStatus(record.get(Tasks.STATUS))
            .withPriority(record.get(Tasks.PRIORITY))
            .withDueDate(record.get(Tasks.DUE_DATE))
            .withCompletedAt(record.get(Tasks.COMPLETED_AT))
            .withCreatedAt(record.get(Tasks.CREATED_AT))
            .withUpdatedAt(record.get(Tasks.UPDATED_AT))
            .build();
    }
}
