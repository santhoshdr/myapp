package net.drs.myapp.api;

import java.util.List;

import net.drs.myapp.model.PaymentDTO;

public interface IPaymentService {

    
    PaymentDTO  savePaymentDetails(PaymentDTO paymentDTO);
    
    
    PaymentDTO  getPaymentDetails(Long   paymentId);
    
    List<PaymentDTO>  getPaymentByMemberId(Long   memberId);
    
    
    PaymentDTO  updatePaymentDetails(PaymentDTO  paymentDTO);
    

    PaymentDTO checkStatusOfPayment(PaymentDTO paymentDTO);

}
