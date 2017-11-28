package net.scottpullen.users.jooq.converters;

import net.scottpullen.common.jooq.converters.EntityIdConverter;
import net.scottpullen.users.entities.UserId;

public class UserIdConverter extends EntityIdConverter<UserId> {
    public UserIdConverter() {
        super(UserId.class);
    }
}
