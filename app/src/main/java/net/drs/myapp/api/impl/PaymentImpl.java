package net.drs.myapp.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.api.IPaymentService;
import net.drs.myapp.dao.IPaymentDAO;
import net.drs.myapp.model.PaymentDTO;

@Repository("paymentService")
@Transactional
public class PaymentImpl implements IPaymentService {

    @Autowired
    private IPaymentDAO  paymentDAO;
    
    @Override
    public PaymentDTO checkStatusOfPayment(PaymentDTO paymentDTO) {
        return paymentDAO.updatePayment(paymentDTO);
    }

    @Override
    public PaymentDTO savePaymentDetails(PaymentDTO paymentDTO) {
        return paymentDAO.savePayment(paymentDTO);
    }

    @Override
    public PaymentDTO getPaymentDetails(Long  paymentId) {
        return paymentDAO.getPayment(paymentId);
    }

    @Override
    public PaymentDTO updatePaymentDetails(PaymentDTO paymentDTO) {
        return paymentDAO.updatePayment(paymentDTO);
    }

	@Override
	public List<PaymentDTO> getPaymentByMemberId(Long memberId) {
		return paymentDAO.getPaymentByMemberId(memberId);
	}



}
