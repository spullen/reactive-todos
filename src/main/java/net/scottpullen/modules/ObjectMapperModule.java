package net.scottpullen.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import net.scottpullen.common.entities.EntityId;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.common.deserializers.TaskIdDeserializer;
import net.scottpullen.common.deserializers.UserIdDeserializer;
import net.scottpullen.common.serializers.EntityIdSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ObjectMapperModule {

    private final ObjectMapper objectMapper;

    public ObjectMapperModule() {
        this.objectMapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new SimpleModule()
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE)
                .addSerializer(EntityId.class, EntityIdSerializer.INSTANCE)
                .addDeserializer(UserId.class, UserIdDeserializer.INSTANCE)
                .addDeserializer(TaskId.class, TaskIdDeserializer.INSTANCE)
            );
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
}
