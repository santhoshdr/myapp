package net.drs.myapp.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.api.IMatrimonyService;
import net.drs.myapp.dao.IMatrimonialServiceDAO;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.exceptions.MatrimonialException;
import net.drs.myapp.model.Wed;

@Repository("matrimonialService")
@Transactional
public class MatrimonialServiceImpl implements IMatrimonyService {

    
    @Autowired
    private IMatrimonialServiceDAO  matrimonialDAOImpl;
    
    
    @Override
    public List viewActiveProfiles() throws MatrimonialException {
        return matrimonialDAOImpl.viewActiveProfiles();
    }

    @Override
    public List<Wed> getAllWedProfiles() throws MatrimonialException {
        return matrimonialDAOImpl.getAllWedProfiles();
    }

    @Override
    public Wed activatedeactivateWed(WedDTO weddto) throws MatrimonialException {
       return  matrimonialDAOImpl.activatedeactivateWed(weddto);
    }

}
