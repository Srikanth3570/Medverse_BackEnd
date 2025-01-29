package com.Medverse.Login.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Medverse.Login.Entity.BillingAndPayment;

public interface BillingAndPaymentRepository extends JpaRepository<BillingAndPayment, Integer> {

}
