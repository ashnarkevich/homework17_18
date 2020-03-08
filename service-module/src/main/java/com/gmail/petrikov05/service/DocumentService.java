package com.gmail.petrikov05.service;

import java.util.List;

import com.gmail.petrikov05.service.model.DocumentDTO;

public interface DocumentService {

    boolean add(DocumentDTO documentDTO);

    List<DocumentDTO> findAll();

    Boolean deleteById(Long id);

    DocumentDTO showById(Long id);

}
