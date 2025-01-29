package com.Medverse.Login.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Medverse.Login.Entity.Document;

public interface DocumentService {
	Document uploadDocument(Long userId, MultipartFile file) throws IOException;
    Document getDocumentById(Long id);
    List<Document> getDocumentsByUserId(Long userId);
    void deleteDocument(Long id);


}







