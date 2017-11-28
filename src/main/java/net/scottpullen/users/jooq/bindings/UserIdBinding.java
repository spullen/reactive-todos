package net.scottpullen.users.jooq.bindings;

import net.scottpullen.common.jooq.bindings.EntityIdBinding;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.users.jooq.converters.UserIdConverter;

public class UserIdBinding extends EntityIdBinding<UserIdConverter, UserId> {
    private static final UserIdConverter CONVERTER = new UserIdConverter();

    @Override
    public UserIdConverter converter() {return CONVERTER; }
}
