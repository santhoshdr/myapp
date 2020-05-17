package net.drs.myapp.api;

import java.util.List;

import net.drs.myapp.exceptions.MatrimonialException;

public interface IMatrimonyService {
    
    List viewProfiles () throws MatrimonialException;
    
    
}
