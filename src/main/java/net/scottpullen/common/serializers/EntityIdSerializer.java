package net.scottpullen.common.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.scottpullen.common.entities.EntityId;

import java.io.IOException;

public class EntityIdSerializer extends JsonSerializer<EntityId> {

    public static final EntityIdSerializer INSTANCE = new EntityIdSerializer();

    @Override
    public void serialize(final EntityId entityId, final JsonGenerator generator, final SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeString(entityId.toString());
    }
}
