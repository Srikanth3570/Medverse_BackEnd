package com.Medverse.Login.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {

    public Appointment() { }

    public Appointment(Long appointmentId, PatientEntity userId, Long doctorId, Long hospitalId,
                       LocalDateTime appointmentDateTime, Status status, String reasonForVisit, String notes) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.doctorId = doctorId;
        this.hospitalId = hospitalId;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
        this.reasonForVisit = reasonForVisit;
        this.notes = notes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private PatientEntity userId;

    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false)
    private Long hospitalId;

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 1000)
    private String reasonForVisit;

    @Column(length = 2000)
    private String notes;

    public enum Status {
        SCHEDULED, COMPLETED, CANCELLED
    }

    // Getters and Setters
    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public PatientEntity getUserId() {
        return userId;
    }

    public void setUserId(PatientEntity userId) {
        this.userId = userId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Appointment [appointmentId=" + appointmentId + ", userId=" + userId + ", doctorId=" + doctorId
                + ", hospitalId=" + hospitalId + ", appointmentDateTime=" + appointmentDateTime + ", status=" + status
                + ", reasonForVisit=" + reasonForVisit + ", notes=" + notes + "]";
    }
}
