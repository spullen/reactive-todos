package net.scottpullen.common.converters;

import net.scottpullen.users.entities.UserId;
import org.jooq.Converter;

import java.util.UUID;

public class UserIdConverter implements Converter<Object, UserId> {
    @Override
    public UserId from(Object t) {
        if(t == null) {
            return null;
        }

        return new UserId(t + "");
    }

    @Override
    public UUID to(UserId u) {
        return u == null ? null : u.getValue();
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public Class<UserId> toType() {
        return UserId.class;
    }
}
