package net.drs.myapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



import net.drs.myapp.utils.TransactionStatus;

@Entity
@Table(name = "payment")
public class PaymentDTO {
    
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer receiptNumber;
    
    double amount;
    
  //  @Length(max = 500)
    private String response;
    
    private String transactionStatus = TransactionStatus.INITIATED.name();
    
     private boolean transactionResult=false;
     
     private String orderId;
    
    private  Long  loggedInUserId;
    
    // for which payment is made
    private Long memberId;
    
    public Long getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(Long loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public void setCustomerMobileNumber(String customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
    }

    public String getCustomerEmailId() {
        return customerEmailId;
    }

    public void setCustomerEmailId(String customerEmailId) {
        this.customerEmailId = customerEmailId;
    }

    public boolean isTransactionResult() {
        return transactionResult;
    }

    public void setTransactionResult(boolean transactionResult) {
        this.transactionResult = transactionResult;
    }

 
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(Integer receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}


	String transactionId;
    
    String customerMobileNumber;
    
    String customerEmailId;
    
    
    
    

    
    
    

}
