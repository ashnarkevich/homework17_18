package com.gmail.petrikov05.controller;

import java.util.List;
import javax.validation.Valid;

import com.gmail.petrikov05.service.DocumentService;
import com.gmail.petrikov05.service.model.DocumentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DocumentsController {

    private DocumentService documentService;

    public DocumentsController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents")
    public String getDocuments(Model model) {
        List<DocumentDTO> documentDTOList = documentService.findAll();
        model.addAttribute("documents", documentDTOList);
        return "documents";
    }

    @GetMapping("/documents/add")
    public String getDocumentAdd(Model model) {
        model.addAttribute("document", new DocumentDTO());
        return "documents_add";
    }

    @PostMapping("/documents/add")
    public String postDocumentAdd(@Valid @ModelAttribute DocumentDTO documentDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/documents_add";
        }
        documentService.add(documentDTO);
        return "redirect:/documents";
    }

    @PostMapping("/documents/read")
    public String postDocumentShow(@RequestParam("id") Long id, Model model) {
        DocumentDTO documentDTO = documentService.showById(id);
        model.addAttribute("document", documentDTO);
        return "document_read";
    }

    @PostMapping("/documents/delete")
    public String postDocumentDelete(@RequestParam("id") Long id) {
        documentService.deleteById(id);
        return "redirect:/documents";
    }

}
