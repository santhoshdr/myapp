package net.drs.myapp.dao;

import java.util.List;

import net.drs.myapp.model.PaymentDTO;

public interface IPaymentDAO {
    
    PaymentDTO savePayment(PaymentDTO paymentDto);
    
    PaymentDTO getPayment(Long  paymentId);
    
    List<PaymentDTO> getPaymentByMemberId(Long memberId);
        
    PaymentDTO updatePayment(PaymentDTO paymentDto);
}
