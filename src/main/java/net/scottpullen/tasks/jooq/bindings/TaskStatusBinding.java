package net.scottpullen.tasks.jooq.bindings;

import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.tasks.jooq.converters.TaskStatusConverter;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

public class TaskStatusBinding implements Binding<String, TaskStatus> {
    private static final TaskStatusConverter CONVERTER = new TaskStatusConverter();

    @Override
    public TaskStatusConverter converter() {
        return CONVERTER;
    }

    @Override
    public void sql(BindingSQLContext<TaskStatus> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(converter()).value()));
    }

    @Override
    public void register(BindingRegisterContext<TaskStatus> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<TaskStatus> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void get(BindingGetResultSetContext<TaskStatus> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<TaskStatus> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
    }

    @Override
    public void set(BindingSetSQLOutputContext<TaskStatus> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<TaskStatus> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
