package com.gmail.petrikov05.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.gmail.petrikov05.repository.ConnectionRepository;
import com.gmail.petrikov05.repository.DocumentRepository;
import com.gmail.petrikov05.repository.model.Document;
import com.gmail.petrikov05.service.DocumentService;
import com.gmail.petrikov05.service.model.DocumentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LogManager.getLogger(DocumentServiceImpl.class);
    private ConnectionRepository connectionRepository;
    private DocumentRepository documentRepository;

    public DocumentServiceImpl(ConnectionRepository connectionRepository, DocumentRepository documentRepository) {
        this.connectionRepository = connectionRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public boolean add(DocumentDTO documentDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                Document document = convertDTOToObject(documentDTO);
                documentRepository.add(connection, document);
                connection.commit();
                return true;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<DocumentDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);

            try {
                List<Document> documents = documentRepository.findAll(connection);
                List<DocumentDTO> documentDTOList = convertDocumentListToDocumentDTOList(documents);
                connection.commit();
                return documentDTOList;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Boolean deleteById(Long id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                documentRepository.deleteById(connection, id);
                connection.commit();
                return true;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public DocumentDTO showById(Long id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Document document = documentRepository.showById(connection, id);
                DocumentDTO documentDTO = converterObjectToDTO(document);
                connection.commit();
                return documentDTO;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private List<DocumentDTO> convertDocumentListToDocumentDTOList(List<Document> documents) {
        List<DocumentDTO> documentDTOList = new ArrayList<>();
        for (Document document : documents) {
            documentDTOList.add(converterObjectToDTO(document));
        }
        return documentDTOList;
    }

    private DocumentDTO converterObjectToDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(document.getId());
        documentDTO.setUuid(document.getUuid());
        documentDTO.setDescription(document.getDescription());
        return documentDTO;
    }

    private Document convertDTOToObject(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setDescription(documentDTO.getDescription());
        String uuid = String.valueOf(UUID.randomUUID());
        document.setUuid(uuid);
        return document;
    }

}
