package net.drs.myapp.api;

import java.util.List;

import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.exceptions.MatrimonialException;
import net.drs.myapp.model.Wed;

public interface IMatrimonyService {
    
    List viewActiveProfiles () throws MatrimonialException;
    
    
    
    List getAllWedProfiles () throws MatrimonialException;
    
    
    Wed activatedeactivateWed(WedDTO weddto ) throws MatrimonialException;
    
    
}
