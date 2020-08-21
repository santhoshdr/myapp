package net.drs.myapp.dao;

import java.util.List;

import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.exceptions.MatrimonialException;
import net.drs.myapp.model.Wed;

public interface IMatrimonialServiceDAO {
    
    List viewActiveProfiles() throws MatrimonialException;
    
    
    List<Wed>getAllWedProfiles() throws MatrimonialException;
    
    Wed activatedeactivateWed(WedDTO wedDTO) throws MatrimonialException;

}
