package net.drs.myapp.utils;

public enum PaymentStatus {
    INITIATED("INITIATED"),
    SUCCESS("SUCCESS"), 
    FAILED("FAILED"),
    PENDING("PENDING");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return status;
    }
}
