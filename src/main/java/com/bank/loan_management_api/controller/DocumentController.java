package com.bank.loan_management_api.controller;

import com.bank.loan_management_api.dto.response.DocumentResponse;
import com.bank.loan_management_api.enums.DocumentType;
import com.bank.loan_management_api.service.DocumentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@PreAuthorize("hasRole('CUSTOMER')")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DocumentResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") DocumentType documentType,
            Principal principal) {

        return ResponseEntity.ok(
                documentService.uploadDocument(
                        principal.getName(),
                        file,
                        documentType
                )
        );
    }

    @GetMapping("/my-documents")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<List<DocumentResponse>> getMyDocuments(
            Principal principal) {

        return ResponseEntity.ok(
                documentService.getMyDocuments(principal.getName())
        );
    }
}