package com.gmail.petrikov05.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.repository.model.Document;

public interface DocumentRepository {

    void add(Connection connection, Document document) throws SQLException;

    List<Document> findAll(Connection connection) throws SQLException;

    void deleteById(Connection connection, Long id) throws SQLException;

    Document showById(Connection connection, Long id) throws SQLException;

}
