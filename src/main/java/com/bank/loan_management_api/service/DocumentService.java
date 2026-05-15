package com.bank.loan_management_api.service;

import com.bank.loan_management_api.dto.response.DocumentResponse;
import com.bank.loan_management_api.entity.Customer;
import com.bank.loan_management_api.entity.Document;
import com.bank.loan_management_api.entity.User;
import com.bank.loan_management_api.enums.DocumentType;
import com.bank.loan_management_api.exception.BadRequestException;
import com.bank.loan_management_api.repository.CustomerRepository;
import com.bank.loan_management_api.repository.DocumentRepository;
import com.bank.loan_management_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.util.UUID;
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    @Value("${file.upload-dir}")
    private String uploadDir;
    public DocumentService(DocumentRepository documentRepository,
                           CustomerRepository customerRepository,
                           UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public DocumentResponse uploadDocument(
            String email,
            MultipartFile file,
            DocumentType documentType) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException(
                        "Customer profile must be completed before uploading documents"
                ));

        try {

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();

            String uniqueFileName =
                    UUID.randomUUID() + "_" + originalFileName;

            Path filePath = uploadPath.resolve(uniqueFileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Document document = new Document();

            document.setFileName(uniqueFileName);

            document.setFileUrl(filePath.toString());

            document.setDocumentType(documentType);

            document.setUploadedAt(LocalDateTime.now());

            document.setCustomer(customer);

            Document saved = documentRepository.save(document);

            return mapToResponse(saved);

        } catch (IOException ex) {

            throw new BadRequestException(
                    "Failed to upload file: " + ex.getMessage()
            );
        }
    }

    public List<DocumentResponse> getMyDocuments(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException("Customer profile not found"));

        return documentRepository.findByCustomer(customer)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private DocumentResponse mapToResponse(Document document) {

        DocumentResponse response = new DocumentResponse();
        response.setId(document.getId());
        response.setFileName(document.getFileName());
        response.setFileUrl(document.getFileUrl());
        response.setDocumentType(document.getDocumentType());
        response.setUploadedAt(document.getUploadedAt());

        return response;
    }



}