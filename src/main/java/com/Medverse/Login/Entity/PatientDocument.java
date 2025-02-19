package com.Medverse.Login.Entity;

import jakarta.persistence.*;

@Entity
public class PatientDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false) // Reference `userId` from `PatientEntity`
    private PatientEntity patient; // Renamed from 'userId' to 'patient' for clarity

    @Lob
    @Column(name = "document", columnDefinition = "LONGBLOB")
    private byte[] document;

    private String fileName;

    public PatientDocument() {
    }

    public PatientDocument(PatientEntity patient, byte[] document, String fileName) {
        this.patient = patient;
        this.document = document;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
