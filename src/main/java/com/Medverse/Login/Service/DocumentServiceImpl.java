package com.Medverse.Login.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Medverse.Login.Entity.Document;
import com.Medverse.Login.Repo.DocumentRepository;

@Service

public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Document uploadDocument(Long userId, MultipartFile file) throws IOException {
    	if (file == null || file.isEmpty()) {
    	    throw new IllegalArgumentException("File is missing or empty.");
    	}
    	Document document = new Document();
        document.setUserId(userId);
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setFileData(file.getBytes());
        document.setFileSize(file.getSize());
        
        try {
        document.setFileData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file data", e);
        }

        return documentRepository.save(document);
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with ID: " + id));
    }

    @Override
    public List<Document> getDocumentsByUserId(Long userId) {
        return documentRepository.findByUserId(userId);
    }

    @Override
    public void deleteDocument(Long id) {
        Document document = getDocumentById(id);
        documentRepository.delete(document);
    }
}

	

