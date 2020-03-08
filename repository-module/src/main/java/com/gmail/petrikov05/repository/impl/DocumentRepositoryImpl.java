package com.gmail.petrikov05.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.petrikov05.repository.DocumentRepository;
import com.gmail.petrikov05.repository.model.Document;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {

    private static final String ID = "id";
    private static final String UNIQUE_NUMBER = "unique_number";
    private static final String DESCRIPTION = "description";

    @Override
    public void add(Connection connection, Document document) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO document(unique_number, description) VALUES (?,?);"
        )) {
            statement.setString(1, String.valueOf(document.getUuid()));
            statement.setString(2, document.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating document failed, no rows affected.");
            }
        }

    }

    @Override
    public List<Document> findAll(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, unique_number FROM document;"
        )) {
            ResultSet resultSet = statement.executeQuery();
            List<Document> documents = new ArrayList<>();
            while (resultSet.next()) {
                documents.add(getDocumentWithoutDescription(resultSet));
            }
            return documents;
        }
    }

    @Override
    public void deleteById(Connection connection, Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM document WHERE id = (?);"
        )) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting document failed, no rows affected.");
            }
        }
    }

    @Override
    public Document showById(Connection connection, Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, unique_number, description FROM document WHERE id = (?);"
        )) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getDocument(resultSet);
            }
            return null;
        }
    }

    private Document getDocument(ResultSet resultSet) throws SQLException {
        Document document = new Document();
        document.setId(resultSet.getLong(ID));
        document.setDescription(resultSet.getString(DESCRIPTION));
        document.setUuid(resultSet.getString(UNIQUE_NUMBER));
        return document;
    }

    private Document getDocumentWithoutDescription(ResultSet resultSet) throws SQLException {
        Document document = new Document();
        document.setId(resultSet.getLong(ID));
        document.setUuid(resultSet.getString(UNIQUE_NUMBER));
        return document;
    }

}
