package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.gmail.petrikov05.repository.ConnectionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectionRepositoryImpl implements ConnectionRepository {

    private final javax.sql.DataSource dataSource;

    public ConnectionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
