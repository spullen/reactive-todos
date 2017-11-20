package net.scottpullen.common.converters;

import org.jooq.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LocalDateTimeConverter implements Converter<Timestamp, LocalDateTime> {
    @Override
    public LocalDateTime from(Timestamp t) {
        if(t == null) {
            return null;
        }

        return t.toLocalDateTime();
    }

    @Override
    public Timestamp to(LocalDateTime u) {
        return u == null ? null : Timestamp.valueOf(u);
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<LocalDateTime> toType() {
        return LocalDateTime.class;
    }
}
