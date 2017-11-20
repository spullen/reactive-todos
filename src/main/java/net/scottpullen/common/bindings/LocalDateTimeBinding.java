package net.scottpullen.common.bindings;

import net.scottpullen.common.converters.LocalDateTimeConverter;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Objects;

public class LocalDateTimeBinding implements Binding<Timestamp, LocalDateTime> {
    private static final LocalDateTimeConverter CONVERTER = new LocalDateTimeConverter();

    @Override
    public LocalDateTimeConverter converter() {return CONVERTER; }

    @Override
    public void sql(BindingSQLContext<LocalDateTime> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(converter()).value()));
    }

    @Override
    public void register(BindingRegisterContext<LocalDateTime> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.TIMESTAMP);
    }

    @Override
    public void set(BindingSetStatementContext<LocalDateTime> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void get(BindingGetResultSetContext<LocalDateTime> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getTimestamp(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<LocalDateTime> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getTimestamp(ctx.index()));
    }

    @Override
    public void set(BindingSetSQLOutputContext<LocalDateTime> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<LocalDateTime> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
