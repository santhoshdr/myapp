package net.drs.myapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.dao.IPaymentDAO;
import net.drs.myapp.model.PaymentDTO;

@Repository("paymentDAO")
@Transactional
public class PaymentDAOImpl implements IPaymentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaymentDTO savePayment(PaymentDTO paymentDto) {
        return entityManager.merge(paymentDto);
    }

    @Override
    public PaymentDTO updatePayment(PaymentDTO paymentDto) {
        
        PaymentDTO storedPayment =  (PaymentDTO) entityManager.createQuery("from PaymentDTO p  where p.orderId=:orderId ").
                setParameter("orderId",paymentDto.getOrderId()).getSingleResult();
        storedPayment.setTransactionResult(paymentDto.isTransactionResult());
        storedPayment.setTransactionStatus(paymentDto.getTransactionStatus());
        storedPayment.setResponse(paymentDto.getResponse());
        return entityManager.merge(storedPayment);
    }

    @Override
    public PaymentDTO getPayment(Long  paymentId) {
        return entityManager.find(PaymentDTO.class, paymentId);
    }

	@Override
	public List<PaymentDTO> getPaymentByMemberId(Long memberId) {
		return entityManager.createQuery("from PaymentDTO p  where p.memberId=:memberId ").
				setParameter("memberId", memberId).getResultList();
}

}
