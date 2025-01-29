package com.Medverse.Login.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Medverse.Login.Entity.BillingAndPayment;
import com.Medverse.Login.Repo.BillingAndPaymentRepository;

@Service

public class BillingAndPaymentService {

	




    private final BillingAndPaymentRepository repository;

    public BillingAndPaymentService(BillingAndPaymentRepository repository) {
        this.repository = repository;
    }

    public BillingAndPayment createPayment(BillingAndPayment payment) {
        return repository.save(payment);
    }

    public BillingAndPayment getPaymentById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<BillingAndPayment> getAllPayments() {
        return repository.findAll();
    }

    public void deletePayment(Integer id) {
        repository.deleteById(id);
    }
}
