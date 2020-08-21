package net.drs.myapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.dao.IMatrimonialServiceDAO;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.exceptions.MatrimonialException;
import net.drs.myapp.model.Wed;
import net.drs.myapp.utils.AppUtils;

@Repository("matrimonialDAOImpl")
@Transactional
public class IMatrimonialDAOImpl implements IMatrimonialServiceDAO{

    
    @PersistenceContext
    private EntityManager entityManager;
    
    
    @Override
    public List viewActiveProfiles() throws MatrimonialException {
        javax.persistence.Query query = entityManager.createQuery("from Wed w  where w.makePublic=1  and w.isProfileActive=1 ");
        return query.getResultList();
}


    @Override
    public List<Wed> getAllWedProfiles() throws MatrimonialException {
        return entityManager.createQuery("SELECT e FROM Wed e").getResultList();
    }


    @Override
    public Wed activatedeactivateWed(WedDTO wedDTO) throws MatrimonialException {
        Wed wed = entityManager.find(Wed.class, wedDTO.getId());
        wed.setIsProfileActive(wedDTO.getIsProfileActive());
        wed.setUpdatedDate(AppUtils.getCurrentDate());
        wed.setUpdatedBy(wedDTO.getUpdatedBy());
        return entityManager.merge(wed);
    }
}    

