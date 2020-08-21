package net.drs.myapp.dao;

import net.drs.myapp.model.PaymentDTO;

public interface IPaymentDAO {
    
    PaymentDTO savePayment(PaymentDTO paymentDto);
    
    PaymentDTO getPayment(Long  paymentId);
    
    
    PaymentDTO updatePayment(PaymentDTO paymentDto);

}
