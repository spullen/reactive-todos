package net.scottpullen.tasks.jooq.bindings;

import net.scottpullen.tasks.entities.TaskPriority;
import net.scottpullen.tasks.jooq.converters.TaskPriorityConverter;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

public class TaskPriorityBinding implements Binding<Integer, TaskPriority> {
    private final static TaskPriorityConverter CONVERTER = new TaskPriorityConverter();

    @Override
    public TaskPriorityConverter converter() {
        return CONVERTER;
    }

    @Override
    public void sql(BindingSQLContext<TaskPriority> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(converter()).value()));
    }

    @Override
    public void register(BindingRegisterContext<TaskPriority> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.INTEGER);
    }

    @Override
    public void set(BindingSetStatementContext<TaskPriority> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void get(BindingGetResultSetContext<TaskPriority> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getInt(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<TaskPriority> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getInt(ctx.index()));
    }

    @Override
    public void set(BindingSetSQLOutputContext<TaskPriority> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<TaskPriority> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
