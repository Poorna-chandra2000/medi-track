package com.airtribe.meditrack.services.paymentservices;

public interface PaymentStrategy {

        Boolean processPayment(double amount);
        Boolean verifyPayment(String paymentId,String customerId);
}
