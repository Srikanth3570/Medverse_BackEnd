package com.Medverse.Login.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medverse.Login.Entity.BillingAndPayment;
import com.Medverse.Login.Service.BillingAndPaymentService;

@RestController
@RequestMapping("/api/payments")


public class BillingAndPaymentController {

	


	
	    private final BillingAndPaymentService service;

	    public BillingAndPaymentController(BillingAndPaymentService service) {
	        this.service = service;
	    }

	    @PostMapping
	    public ResponseEntity<BillingAndPayment> createPayment(@RequestBody BillingAndPayment payment) {
	        return ResponseEntity.ok(service.createPayment(payment));
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<BillingAndPayment> getPaymentById(@PathVariable Integer id) {
	        return ResponseEntity.ok(service.getPaymentById(id));
	    }

	    @GetMapping
	    public ResponseEntity<List<BillingAndPayment>> getAllPayments() {
	        return ResponseEntity.ok(service.getAllPayments());
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
	        service.deletePayment(id);
	        return ResponseEntity.noContent().build();
	    }
	}
