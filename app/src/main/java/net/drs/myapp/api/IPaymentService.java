package net.drs.myapp.api;

import net.drs.myapp.model.PaymentDTO;

public interface IPaymentService {

    
    PaymentDTO  savePaymentDetails(PaymentDTO paymentDTO);
    
    
    PaymentDTO  getPaymentDetails(Long   paymentId);
    
    PaymentDTO  updatePaymentDetails(PaymentDTO  paymentDTO);
    

    PaymentDTO checkStatusOfPayment(PaymentDTO paymentDTO);

}
