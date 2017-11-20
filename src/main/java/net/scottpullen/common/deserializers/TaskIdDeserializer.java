package net.scottpullen.common.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import net.scottpullen.tasks.entities.TaskId;

import java.io.IOException;
import java.util.UUID;

public class TaskIdDeserializer extends JsonDeserializer<TaskId> {
    public static final TaskIdDeserializer INSTANCE = new TaskIdDeserializer();

    public TaskId deserialize(final JsonParser parser, final DeserializationContext context) throws IOException, JsonProcessingException {
        return new TaskId(UUID.fromString(parser.getText()));
    }
}
