package net.drs.myapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.dao.IMatrimonialServiceDAO;
import net.drs.myapp.exceptions.MatrimonialException;

@Repository("matrimonialDAOImpl")
@Transactional
public class IMatrimonialDAOImpl implements IMatrimonialServiceDAO{

    
    @PersistenceContext
    private EntityManager entityManager;
    
    
    @Override
    public List viewProfiles() throws MatrimonialException {
        javax.persistence.Query query = entityManager.createQuery("from Wed w  where w.makePublic=1  and w.isProfileActive=1 ");
        return query.getResultList();
}
}    

