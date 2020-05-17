package net.drs.myapp.dao;

import java.util.List;

import net.drs.myapp.exceptions.MatrimonialException;

public interface IMatrimonialServiceDAO {
    
    List viewProfiles() throws MatrimonialException;

}
