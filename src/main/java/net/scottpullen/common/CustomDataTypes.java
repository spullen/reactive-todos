package net.scottpullen.common;

import net.scottpullen.users.entities.UserId;
import net.scottpullen.common.bindings.LocalDateTimeBinding;
import net.scottpullen.common.bindings.UserIdBinding;
import org.jooq.DataType;
import org.jooq.util.postgres.PostgresDataType;

import java.time.LocalDateTime;

public class CustomDataTypes {
    public static final DataType<LocalDateTime> LOCAL_DATE_TIME = PostgresDataType.TIMESTAMP.asConvertedDataType(new LocalDateTimeBinding());
    public static final DataType<UserId> USER_ID  = PostgresDataType.UUID.asConvertedDataType(new UserIdBinding());
}
