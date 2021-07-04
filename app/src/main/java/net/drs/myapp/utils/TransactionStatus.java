package net.drs.myapp.utils;

public enum TransactionStatus {
    INITIATED("INITIATED"),
    SUCCESS("SUCCESS"), 
    FAILED("FAILED"),
    PENDING("PENDING");

    private String status;

    TransactionStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return status;
    }
}
