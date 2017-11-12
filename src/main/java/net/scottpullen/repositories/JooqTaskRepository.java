package net.scottpullen.repositories;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

public class JooqTaskRepository implements TaskRepository {

    private final DSLContext jooq;

    public JooqTaskRepository(DataSource dataSource) {
        jooq = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    // create
    // update
    // findAllIncompleteByUserId

}
