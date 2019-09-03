package net.drs.myapp.api.impl;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.app.exception.RoleException;
import net.drs.myapp.dao.IUserDAO;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.model.Wed;

@Repository("userDetails")
@Transactional
public class UserDetailsImpl implements IUserDetails {

    @Value("${upload.image}")
    private String uploadimage;

    @Value("${upload.image.location}")
    private String uploadImageLocation;

    @Value("${wed.images.directory}")
    private String wedDirectory;

    @Autowired
    private IUserDAO userDAO;

    private ModelMapper modelMapper = new ModelMapper();
    
    
	@Override
	public boolean changePassword(ResetPasswordDTO resetPasswordDTO)
			throws Exception {
		return userDAO.changePassword(resetPasswordDTO);
	}

    @Override
    public List<UserServiceDTO> getAllUsers(int numberofUsers) {

        List<UserServiceDTO> userDTO = new ArrayList<UserServiceDTO>();
        System.out.println("uploadImageLocation ====> " + uploadImageLocation);

        File directory = new File(uploadImageLocation);

        List<User> users = userDAO.getAllUsers(numberofUsers);
        users.forEach(user -> {
            File[] files = null;
            UserServiceDTO udto = new UserServiceDTO();
            files = directory.listFiles((FileFilter) new PrefixFileFilter(user.getId().toString() + "--", IOCase.SENSITIVE));
            if (files != null && files.length > 0) {
                udto.setImage(files[0]);
            }
            modelMapper.map(user, udto);
            userDTO.add(udto);
        });
        return userDTO;
    }

    // this returns user object no matter user is active or inactive
    @Override
    public User getUserById(Long userId) {
        return userDAO.getUser(userId);
    }

    @Override
    public List<UserServiceDTO> getAllActiveUsers(int numberofUsers) {
        List<UserServiceDTO> userDTO = new ArrayList<UserServiceDTO>();

        List<User> users = userDAO.getAllActiveUsers(numberofUsers);
        users.forEach(user -> {
            UserServiceDTO udto = new UserServiceDTO();
            modelMapper.map(user, udto);
            userDTO.add(udto);

        });
        // userDAO.getAllUsers();
        return userDTO;
    }

    @Override
    public boolean isUserActive(Long userId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean activeteUser(Long userId) {
        userDAO.activateUser(userId);
        return false;
    }

    @Override
    public boolean deactiveUser(UserServiceDTO userServiceDTO) {

        User user = new User();
        modelMapper.map(userServiceDTO, user);
        return userDAO.deactivateUser(user);
    }

    @Override
    public boolean updateUserDetails(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public List<UserServiceDTO> getAllAdminActiveUsers(int numberofUser) {

        List<UserServiceDTO> userDTO = new ArrayList<UserServiceDTO>();
        List<User> users = userDAO.getAllAdminActiveUsers(numberofUser);
        users.forEach(user -> {
            UserServiceDTO udto = new UserServiceDTO();
            modelMapper.map(user, udto);
            userDTO.add(udto);

        });
        // userDAO.getAllUsers();
        return userDTO;
    }

    @Override
    public boolean changeUserRole(UserServiceDTO userServiceDTO) throws RoleException {

        Users udto = null;
        try {
            udto = new Users();
            userDAO.changeUserRole(userServiceDTO);
            return false;
        } catch (RoleException e) {
            throw e;
        } catch (Exception e) {
            throw new RoleException("Something  wrong. Check later");
        }
    }

    @Override
    public WedDTO createWedProfile(WedDTO wedDTO, Long addedBy) throws Exception {

        Wed wed = new Wed();
        modelMapper.map(wedDTO, wed);
        Wed storedWed = userDAO.createWedProfile(wed);

        // deal with images:
        Long wedId = storedWed.getId();

        final String wedUploadImagePath = "imageuploadfolder" + File.separator + addedBy + File.separator + wedId + File.separator + "images";
        final String wedUploadDocumentsPath = wedDirectory + File.separator + addedBy + File.separator + wedId + File.separator + "documents";
        boolean wedImagedir = new File(wedUploadImagePath).exists() ? true : new File(wedUploadImagePath).mkdirs();
        boolean wedDocdir = new File(wedUploadDocumentsPath).exists() ? true : new File(wedUploadDocumentsPath).mkdirs();
        new File(wedUploadImagePath).setWritable(true);
        new File(wedUploadDocumentsPath).setWritable(true);

        if (!wedDocdir || !wedImagedir) {
            throw new Exception("Unable to create Directory to store uploaded files. Please contact Administrator");
        }

        for (File image : wedDTO.getWedImage()) {
            if (image == null) {
                break;
            }
            byte[] bytes = Files.readAllBytes(image.toPath());
            Path path = Paths.get(wedUploadImagePath + File.separator + image.getName());
            Files.write(path, bytes);
        }

        for (File doc : wedDTO.getWedJataka()) {
            if (doc == null) {
                break;
            }
            byte[] bytes = Files.readAllBytes(doc.toPath());
            Path path = Paths.get(wedUploadDocumentsPath + File.separator + doc.getName());
            Files.write(path, bytes);
        }

        return wedDTO;

    }

    @Override
    public List<WedDTO> fetchWedProfile(Long loggedinUserId) throws Exception {
        List<Wed> webList = userDAO.fetchWedProfile(loggedinUserId);
        WedDTO wedDTO = null;
        List<WedDTO> webDTOList = new ArrayList<WedDTO>();

        for (Wed wed : webList) {
            wedDTO = new WedDTO();
            modelMapper.map(wed, wedDTO);
            webDTOList.add(wedDTO);
        }
        return webDTOList;
    }

    @Override
    public WedDTO updateWedProfile(WedDTO wedDTO, Long addedBy) throws Exception {

        Wed wed = new Wed();
        modelMapper.map(wedDTO, wed);
        wed.setAddedBy(addedBy);
        Wed storedWed = userDAO.updateWedProfile(wed, wedDTO.getId());
        modelMapper.map(storedWed, wedDTO);
        return wedDTO;
    }

	@Override
	public User getUserById(String emailId) {
		 return userDAO.getUser(emailId);
	}

}
