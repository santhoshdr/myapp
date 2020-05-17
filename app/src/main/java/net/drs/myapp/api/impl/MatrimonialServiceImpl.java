package net.drs.myapp.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.api.IMatrimonyService;
import net.drs.myapp.dao.IMatrimonialServiceDAO;
import net.drs.myapp.exceptions.MatrimonialException;

@Repository("matrimonialService")
@Transactional
public class MatrimonialServiceImpl implements IMatrimonyService {

    
    @Autowired
    private IMatrimonialServiceDAO  matrimonialDAOImpl;
    
    
    @Override
    public List viewProfiles() throws MatrimonialException {
        return matrimonialDAOImpl.viewProfiles();
    }

}
