package com.Medverse.Login.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medverse.Login.Entity.Document;
import com.Medverse.Login.Service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload/{userId}")

public ResponseEntity<?> getDocumentById(@PathVariable Long id) {
    Document document = documentService.getDocumentById(id);

    if (document == null || document.getFileData() == null) {
        return ResponseEntity.badRequest().body("Document not found or empty.");
    }

    // Validate content type
    String contentType = document.getFileType();
    if (contentType == null || contentType.isEmpty()) {
        contentType = "application/octet-stream";
    }

    // Validate and encode filename
    String fileName = document.getFileName();
    if (fileName == null || fileName.isEmpty()) {
        fileName = "unknown_file";
    } else {
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
        .body(document.getFileData());
}
    }

