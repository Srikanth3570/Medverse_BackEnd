package com.Medverse.Login.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Billing_and_Payment")


public class BillingAndPayment {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer paymentId;

	    private Integer userId;
	    private Integer appointmentId;

	    @Column(precision = 10, scale = 2)
	    private BigDecimal amount; // Changed from Double to BigDecimal

	    @Enumerated(EnumType.STRING)
	    private PaymentMethod paymentMethod;

	    private LocalDateTime paymentDate;

	    @Enumerated(EnumType.STRING)
	    private PaymentStatus paymentStatus;

	    // Enums
	    public enum PaymentMethod {
	        CREDIT_CARD, PAYPAL, CASH
	    }

	    public enum PaymentStatus {
	        PENDING, COMPLETED
	    }

		public Integer getPaymentId() {
			return paymentId;
		}

		public void setPaymentId(Integer paymentId) {
			this.paymentId = paymentId;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public Integer getAppointmentId() {
			return appointmentId;
		}

		public void setAppointmentId(Integer appointmentId) {
			this.appointmentId = appointmentId;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public PaymentMethod getPaymentMethod() {
			return paymentMethod;
		}

		public void setPaymentMethod(PaymentMethod paymentMethod) {
			this.paymentMethod = paymentMethod;
		}

		public LocalDateTime getPaymentDate() {
			return paymentDate;
		}

		public void setPaymentDate(LocalDateTime paymentDate) {
			this.paymentDate = paymentDate;
		}

		public PaymentStatus getPaymentStatus() {
			return paymentStatus;
		}

		public void setPaymentStatus(PaymentStatus paymentStatus) {
			this.paymentStatus = paymentStatus;
		}

		@Override
		public int hashCode() {
			return Objects.hash(appointmentId, paymentDate, paymentId, paymentMethod, paymentStatus, userId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BillingAndPayment other = (BillingAndPayment) obj;
			return Objects.equals(appointmentId, other.appointmentId) && Objects.equals(paymentDate, other.paymentDate)
					&& Objects.equals(paymentId, other.paymentId) && paymentMethod == other.paymentMethod
					&& paymentStatus == other.paymentStatus && Objects.equals(userId, other.userId);
		}

		public BillingAndPayment(Integer paymentId, Integer userId, Integer appointmentId, BigDecimal amount,
				PaymentMethod paymentMethod, LocalDateTime paymentDate, PaymentStatus paymentStatus) {
			super();
			this.paymentId = paymentId;
			this.userId = userId;
			this.appointmentId = appointmentId;
			this.amount = amount;
			this.paymentMethod = paymentMethod;
			this.paymentDate = paymentDate;
			this.paymentStatus = paymentStatus;
		}

		public BillingAndPayment() {
			super();
			// TODO Auto-generated constructor stub
		}

		

	    // Getters and Setters
	    // toString, hashCode, equals (optional)
	
}


	
	    