package net.scottpullen.users.jooq;

import net.scottpullen.users.entities.UserId;
import net.scottpullen.users.jooq.bindings.UserIdBinding;
import org.jooq.DataType;
import org.jooq.util.postgres.PostgresDataType;

public class UsersDataTypes {
    public static final DataType<UserId> USER_ID  = PostgresDataType.UUID.asConvertedDataType(new UserIdBinding());
}
