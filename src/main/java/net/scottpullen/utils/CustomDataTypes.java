package net.scottpullen.utils;

import net.scottpullen.entities.UserId;
import net.scottpullen.utils.bindings.LocalDateTimeBinding;
import net.scottpullen.utils.bindings.UserIdBinding;
import org.jooq.DataType;
import org.jooq.util.postgres.PostgresDataType;

import java.time.LocalDateTime;

public class CustomDataTypes {
    public static final DataType<LocalDateTime> LOCAL_DATE_TIME = PostgresDataType.TIMESTAMP.asConvertedDataType(new LocalDateTimeBinding());
    public static final DataType<UserId> USER_ID  = PostgresDataType.UUID.asConvertedDataType(new UserIdBinding());
}
