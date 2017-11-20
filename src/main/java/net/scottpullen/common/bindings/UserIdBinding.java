package net.scottpullen.common.bindings;

import net.scottpullen.users.entities.UserId;
import net.scottpullen.common.converters.UserIdConverter;
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
import java.sql.Types;
import java.util.Objects;

public class UserIdBinding implements Binding<Object, UserId> {
    private static final UserIdConverter CONVERTER = new UserIdConverter();

    @Override
    public UserIdConverter converter() {return CONVERTER; }

    @Override
    public void sql(BindingSQLContext<UserId> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(converter()).value()));
    }

    @Override
    public void register(BindingRegisterContext<UserId> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.JAVA_OBJECT);
    }

    @Override
    public void set(BindingSetStatementContext<UserId> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void get(BindingGetResultSetContext<UserId> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<UserId> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
    }

    @Override
    public void set(BindingSetSQLOutputContext<UserId> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<UserId> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
