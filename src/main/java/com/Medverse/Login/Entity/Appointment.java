package com.Medverse.Login.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Appointment")


public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;

    @ManyToOne
    @JoinColumn(name="userId" , referencedColumnName = "userId", nullable = false)
//    @Column(nullable = false)
    private RegistrationEntity userId;

    @Column(nullable = false)
    private Integer doctorId;

    @Column(nullable = false)
    private Integer hospitalId;

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SCHEDULED;

    @Lob
    private String reasonForVisit;

    @Lob
    private String notes;

    public enum Status {
        SCHEDULED, COMPLETED, CANCELLED
    }

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public RegistrationEntity getUserId() {
		return userId;
	}

	public void setUserId(RegistrationEntity userId) {
		this.userId = userId;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Integer hospitalId) {
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

	public Appointment(Integer appointmentId, RegistrationEntity userId, Integer doctorId, Integer hospitalId,
			LocalDateTime appointmentDateTime, Status status, String reasonForVisit, String notes) {
		super();
		this.appointmentId = appointmentId;
		this.userId = userId;
		this.doctorId = doctorId;
		this.hospitalId = hospitalId;
		this.appointmentDateTime = appointmentDateTime;
		this.status = status;
		this.reasonForVisit = reasonForVisit;
		this.notes = notes;
	}

	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}

    
    
}
