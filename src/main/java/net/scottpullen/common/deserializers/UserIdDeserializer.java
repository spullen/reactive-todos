package net.scottpullen.common.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import net.scottpullen.users.entities.UserId;

import java.io.IOException;
import java.util.UUID;

public class UserIdDeserializer extends JsonDeserializer<UserId> {

    public static final UserIdDeserializer INSTANCE = new UserIdDeserializer();

    public UserId deserialize(final JsonParser parser, final DeserializationContext context) throws IOException, JsonProcessingException {
        return new UserId(UUID.fromString(parser.getText()));
    }
}
