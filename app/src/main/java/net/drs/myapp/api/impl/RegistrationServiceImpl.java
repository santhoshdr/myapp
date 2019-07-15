package net.drs.myapp.api.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.dao.IRegistrationDAO;
import net.drs.myapp.dto.CompleteRegistrationDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.CompleteUserDetails;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("registrationService")
@Transactional
public class RegistrationServiceImpl implements IRegistrationService {

    @Autowired
    private IRegistrationDAO registrationDAO;

    private ModelMapper modelMapper = new ModelMapper();

    @Value("${upload.image}")
    private String uploadimage;
    
    @Value("${upload.image.location}")
    private String uploadImageLocation;
    
    @Override
    public boolean adduser(UserDTO userDTO, Set<Role> roles) throws Exception {

        try {
            User user = new User();
            modelMapper.map(userDTO, user);
            boolean result = registrationDAO.checkIfUserExistbyEmailId(user);
            if (!result) {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                return registrationDAO.addUser(user, roles);
            } else {
                throw new Exception("Some problem.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        }
    }
    
    @Override
    public boolean addFotographer(Fotographer fotographer) {
        registrationDAO.addFotographer(fotographer);
        return true;
    }

    @Override
    public boolean completeRegistration(CompleteRegistrationDTO completeRegistrationDTO) throws Exception {
        User user = null;
        CompleteUserDetails completeUserDetails = new CompleteUserDetails();
        modelMapper.map(completeRegistrationDTO, completeUserDetails);
        user = registrationDAO.checkIfUserEmailisPresentandVerified(completeRegistrationDTO.getUserIdorEmailAddress());
        if (null != user) {
            completeUserDetails.setUserId(user.getUserId());
            registrationDAO.completeRegistration(completeUserDetails);
        } else {
            user = registrationDAO.checkIfUserPhoneisPresentandVerified(completeRegistrationDTO.getUserIdorEmailAddress());
        }
        if (user == null) {
            throw new Exception("Email or Mobile is not registered. Please register with you email id or phone number");
        }
        return false;
    }

    @Override
    public Long adduserandGetId(UserDTO userDTO, Set<Role> roles) throws Exception {
        Long userId = 0L;
        try {
            User user = new User();
            modelMapper.map(userDTO, user);
            boolean result = registrationDAO.checkIfUserExistbyEmailId(user);
            if (!result) {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                userId = registrationDAO.addUserandGetUserId(user, roles);
                if(uploadimage!=null && uploadimage.equals("folder")){
                    File filetobeUploaded = userDTO.getImage();
                    byte[] bytes = Files.readAllBytes(filetobeUploaded.toPath());
                    Path path = Paths.get(uploadImageLocation +File.separator+ userId+"--"+filetobeUploaded.getName());
                    Files.write(path, bytes);
                }
            } else {
                throw new Exception("Some problem.");
            }
        } catch (Exception e) {
            throw e;
        }
        return  userId;
    }

    /*
     * @Override public boolean addAdministrator(UserDTO userDTO) throws
     * Exception { try {
     * 
     * User user = new User();
     * 
     * modelMapper.map(userDTO, user);
     * 
     * boolean result = registrationDAO.checkIfUserExistbyName(user);
     * user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()))
     * ; if(!result){ return registrationDAO.addUser(user); }else{ throw new
     * Exception("Some problem."); }
     * 
     * 
     * } catch (Exception e) { // TODO Auto-generated catch block throw e; }
     */

}
