package com.Medverse.Login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medverse.Login.Entity.Appointment;
import com.Medverse.Login.Service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    
	

	    @Autowired
	    private AppointmentService service;

	    @GetMapping
	    public List<Appointment> getAllAppointments() {
	        return service.getAllAppointments();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
	        return service.getAppointmentById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public Appointment createAppointment(@RequestBody Appointment appointment) {
	        return service.createAppointment(appointment);
	    }

	   
	    
	}


