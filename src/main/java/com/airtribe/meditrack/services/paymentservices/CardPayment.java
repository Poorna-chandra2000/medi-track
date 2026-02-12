package com.airtribe.meditrack.services.paymentservices;

import org.springframework.stereotype.Component;

@Component("card")
public class CardPayment implements PaymentStrategy {

    @Override
    public Boolean processPayment(double amount) {
        // Implement card payment processing logic here
        System.out.println("Processing card payment of amount: " + amount);
        return true;
    }

    @Override
    public Boolean verifyPayment(String paymentId, String customerId) {
        // Implement card payment verification logic here
        System.out.println("Verifying card payment with ID: " + paymentId + " for customer: " + customerId);
        return true; // Assuming verification is successful for demonstration
    }
}
