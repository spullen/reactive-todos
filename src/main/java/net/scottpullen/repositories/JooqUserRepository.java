package net.scottpullen.repositories;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

public class JooqUserRepository implements UserRepository {

    private final DSLContext jooq;

    public JooqUserRepository(final DataSource dataSource) {
        jooq = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    // create
    // update
    // findById
    // findByEmail
    // exists
}
