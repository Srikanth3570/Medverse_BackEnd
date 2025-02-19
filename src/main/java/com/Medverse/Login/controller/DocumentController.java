package com.Medverse.Login.controller;

import com.Medverse.Login.Entity.DoctorDocument;
import com.Medverse.Login.Entity.PatientDocument;
import com.Medverse.Login.Entity.PatientEntity;
import com.Medverse.Login.Entity.DoctorEntity;
import com.Medverse.Login.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    // ✅ Patient uploads document


    // ✅ Doctor uploads document for patient
    @PostMapping("/doctor/upload")
    public ResponseEntity<String> uploadDoctorDocument(@RequestParam("patientId") Long patientId,
                                                       @RequestParam("doctorId") Long doctorId,
                                                       @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty. Please upload a valid file.");
            }

            PatientEntity patient = new PatientEntity();
            patient.setUserId(patientId);

            DoctorEntity doctor = new DoctorEntity();
            doctor.setDoctorId(doctorId);

            documentService.uploadDoctorDocument(patient, doctor, file);

            return ResponseEntity.ok("Document uploaded successfully!");
        } catch (Exception e) {
            logger.error("Failed to upload doctor document: ", e);
            return ResponseEntity.internalServerError().body("Error uploading document: " + e.getMessage());
        }
    }

    // ✅ Patient views all their uploaded documents
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PatientDocument>> getPatientDocuments(@PathVariable Long patientId) {
        List<PatientDocument> documents = documentService.getPatientDocuments(patientId);
        if (documents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(documents);
    }

    // ✅ Doctor views all documents uploaded for a specific patient
    @GetMapping("/doctor/{patientId}")
    public ResponseEntity<List<DoctorDocument>> getDoctorDocuments(@PathVariable Long patientId) {
        List<DoctorDocument> documents = documentService.getDoctorDocuments(patientId);
        if (documents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(documents);
    }

    // ✅ Doctor views only their own uploaded documents for a patient
    @GetMapping("/doctor/{patientId}/{doctorId}")
    public ResponseEntity<List<DoctorDocument>> getDoctorSpecificDocuments(@PathVariable Long patientId,
                                                                           @PathVariable Long doctorId) {
        List<DoctorDocument> documents = documentService.getDoctorSpecificDocuments(patientId, doctorId);
        if (documents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(documents);
    }


    // ✅ Update patient document
    @PutMapping("/patient/update/{documentId}")
    public ResponseEntity<String> updatePatientDocument(@PathVariable Long documentId,
                                                        @RequestParam("file") MultipartFile file) {
        try {
            documentService.updatePatientDocument(documentId, file);
            return ResponseEntity.ok("Patient document updated successfully!");
        } catch (Exception e) {
            logger.error("Error updating patient document: ", e);
            return ResponseEntity.internalServerError().body("Error updating document: " + e.getMessage());
        }
    }


    // ✅ Update doctor document
    @PutMapping("/doctor/update/{documentId}")
    public ResponseEntity<String> updateDoctorDocument(@PathVariable Long documentId,
                                                       @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty. Please upload a valid file.");
            }

            documentService.updateDoctorDocument(documentId, file);

            return ResponseEntity.ok("Doctor document updated successfully!");
        } catch (Exception e) {
            logger.error("Error updating doctor document: ", e);
            return ResponseEntity.internalServerError().body("Error updating document: " + e.getMessage());
        }
    }
    
    // ✅ Delete patient document
    @DeleteMapping("/patient/delete/{documentId}")
    public ResponseEntity<String> deletePatientDocument(@PathVariable Long documentId) {
        try {
            documentService.deletePatientDocument(documentId);
            return ResponseEntity.ok("Patient document deleted successfully!");
        } catch (Exception e) {
            logger.error("Error deleting patient document: ", e);
            return ResponseEntity.internalServerError().body("Error deleting document: " + e.getMessage());
        }
    }


    // ✅ Delete doctor document
    @DeleteMapping("/doctor/delete/{documentId}")
    public ResponseEntity<String> deleteDoctorDocument(@PathVariable Long documentId) {
        try {
            documentService.deleteDoctorDocument(documentId);
            return ResponseEntity.ok("Doctor document deleted successfully!");
        } catch (Exception e) {
            logger.error("Error deleting doctor document: ", e);
            return ResponseEntity.internalServerError().body("Error deleting document: " + e.getMessage());
        }
    }

}
