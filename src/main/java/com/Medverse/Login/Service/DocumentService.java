package com.Medverse.Login.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.Medverse.Login.Entity.DoctorDocument;
import com.Medverse.Login.Entity.DoctorEntity;
import com.Medverse.Login.Entity.PatientDocument;
import com.Medverse.Login.Entity.PatientEntity;
import com.Medverse.Login.Repo.DoctorDocumentRepo;
import com.Medverse.Login.Repo.PatientDocumentRepo;

@Service
public class DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    private final PatientDocumentRepo patientDocumentRepo;
    private final DoctorDocumentRepo doctorDocumentRepo;

    public DocumentService(PatientDocumentRepo patientDocumentRepo, DoctorDocumentRepo doctorDocumentRepo) {
        this.patientDocumentRepo = patientDocumentRepo;
        this.doctorDocumentRepo = doctorDocumentRepo;
    }
    

    // ✅ Upload Patient Document
    @Transactional
    public void uploadPatientDocument(PatientEntity patient, MultipartFile file) throws IOException {
        if (patient == null || file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: Patient and file cannot be null or empty!");
        }

        try {
            PatientDocument patientDocument = new PatientDocument(patient, file.getBytes(), file.getOriginalFilename());
            patientDocumentRepo.save(patientDocument);
            logger.info("Patient document uploaded successfully for patientId: {}", patient.getUserId());
        } catch (Exception e) {
            logger.error("Error uploading patient document for patientId: {}", patient.getUserId(), e);
            throw new RuntimeException("Error uploading document, please try again later.");
        }
    }

    // ✅ Upload Doctor Document
    @Transactional
    public void uploadDoctorDocument(PatientEntity patient, DoctorEntity doctor, MultipartFile file) throws IOException {
        if (patient == null || doctor == null || file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: Patient, doctor, and file cannot be null or empty!");
        }

        try {
            DoctorDocument doctorDocument = new DoctorDocument(patient, doctor, file.getBytes(), file.getOriginalFilename());
            doctorDocumentRepo.save(doctorDocument);
            logger.info("Doctor document uploaded successfully for patientId: {} by doctorId: {}", patient.getUserId(), doctor.getDoctorId());
        } catch (Exception e) {
            logger.error("Error uploading doctor document for patientId: {} by doctorId: {}", patient.getUserId(), doctor.getDoctorId(), e);
            throw new RuntimeException("Error uploading document, please try again later.");
        }
    }

    // ✅ Retrieve all Patient Documents
    public List<PatientDocument> getPatientDocuments(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null!");
        }

        try {
            return patientDocumentRepo.findByPatient_UserId(userId);
        } catch (Exception e) {
            logger.error("Error retrieving patient documents for patientId: {}", userId, e);
            return Collections.emptyList();
        }
    }

    // ✅ Retrieve all Doctor Documents for a Patient
    public List<DoctorDocument> getDoctorDocuments(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null!");
        }

        try {
            return doctorDocumentRepo.findByPatientUserId(userId);
        } catch (Exception e) {
            logger.error("Error retrieving doctor documents for patientId: {}", userId, e);
            return Collections.emptyList();
        }
    }

    // ✅ Retrieve Specific Doctor's Documents for a Patient
    public List<DoctorDocument> getDoctorSpecificDocuments(Long userId, Long doctorId) {
        if (userId == null || doctorId == null) {
            throw new IllegalArgumentException("User ID and Doctor ID cannot be null!");
        }

        try {
            return doctorDocumentRepo.findByPatientUserIdAndDoctorDoctorId(userId, doctorId);
        } catch (Exception e) {
            logger.error("Error retrieving doctor-specific documents for patientId: {} and doctorId: {}", userId, doctorId, e);
            return Collections.emptyList();
        }
    }

    // ✅ Update Patient Document
    @Transactional
    public void updatePatientDocument(Long documentId, MultipartFile file) throws IOException {
        if (documentId == null || file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: Document ID and file cannot be null or empty!");
        }

        try {
            PatientDocument existingDocument = patientDocumentRepo.findById(documentId)
                    .orElseThrow(() -> new RuntimeException("Document not found with ID: " + documentId));

            existingDocument.setFileName(file.getOriginalFilename());
            existingDocument.setDocument(file.getBytes());

            patientDocumentRepo.save(existingDocument);
            logger.info("Patient document updated successfully for documentId: {}", documentId);
        } catch (Exception e) {
            logger.error("Error updating patient document with documentId: {}", documentId, e);
            throw new RuntimeException("Error updating document, please try again later.");
        }
    }

    // ✅ Update Doctor Document
    @Transactional
    public void updateDoctorDocument(Long documentId, MultipartFile file) throws IOException {
        if (documentId == null || file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: Document ID and file cannot be null or empty!");
        }

        try {
            DoctorDocument doctorDocument = doctorDocumentRepo.findById(documentId)
                    .orElseThrow(() -> new RuntimeException("Doctor document not found with ID: " + documentId));

            doctorDocument.setFileName(file.getOriginalFilename());
            doctorDocument.setDocument(file.getBytes());

            doctorDocumentRepo.save(doctorDocument);
            logger.info("Doctor document updated successfully for documentId: {}", documentId);
        } catch (Exception e) {
            logger.error("Error updating doctor document with documentId: {}", documentId, e);
            throw new RuntimeException("Error updating document, please try again later.");
        }
    }

    // ✅ Delete Patient Document
    @Transactional
    public void deletePatientDocument(Long documentId) {
        if (documentId == null) {
            throw new IllegalArgumentException("Document ID cannot be null!");
        }

        try {
            patientDocumentRepo.deleteById(documentId);
            logger.info("Patient document deleted successfully for documentId: {}", documentId);
        } catch (Exception e) {
            logger.error("Error deleting patient document with documentId: {}", documentId, e);
            throw new RuntimeException("Error deleting document, please try again later.");
        }
    }

    // ✅ Delete Doctor Document
    @Transactional
    public void deleteDoctorDocument(Long documentId) {
        if (documentId == null) {
            throw new IllegalArgumentException("Document ID cannot be null!");
        }

        try {
            doctorDocumentRepo.deleteById(documentId);
            logger.info("Doctor document deleted successfully for documentId: {}", documentId);
        } catch (Exception e) {
            logger.error("Error deleting doctor document with documentId: {}", documentId, e);
            throw new RuntimeException("Error deleting document, please try again later.");
        }
    }
}
