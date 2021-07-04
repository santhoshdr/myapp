package net.drs.myapp.utils;

public enum PaymentStatus {
    SUCCESS("SUCCESS"), 
    FAILED("FAILED");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return status;
    }
}
