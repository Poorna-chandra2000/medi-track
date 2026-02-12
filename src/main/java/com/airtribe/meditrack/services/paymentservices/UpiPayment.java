package com.airtribe.meditrack.services.paymentservices;

import org.springframework.stereotype.Component;

@Component("upi")
public class UpiPayment implements PaymentStrategy {

    @Override
    public Boolean processPayment(double amount) {
        // Implement UPI payment processing logic here
        System.out.println("Processing UPI payment of amount: " + amount);
        return true;
    }

    @Override
    public Boolean verifyPayment(String paymentId, String customerId) {
        // Implement UPI payment verification logic here
        System.out.println("Verifying UPI payment with ID: " + paymentId + " for customer: " + customerId);
        return true; // Assuming verification is successful for demonstration
    }
}
