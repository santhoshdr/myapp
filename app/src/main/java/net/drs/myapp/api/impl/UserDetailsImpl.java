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
import org.springframework.web.multipart.MultipartFile;

import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.app.exception.RoleException;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dao.IUserDAO;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.exceptions.UserException;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.model.Wed;
import net.drs.myapp.utils.AppUtils;

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
    public boolean changePassword(ResetPasswordDTO resetPasswordDTO) throws Exception {
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

            user.getRoles().forEach(v -> {
                if (v.getRole().equalsIgnoreCase(ApplicationConstants.ROLE_ADMIN)) {
                    udto.setAdmin(true);
                }
            });

            // udto.setAdmin(user.getRoles().contains(ApplicationConstants.ROLE_ADMIN)?true:false);
            modelMapper.map(user, udto);
            userDTO.add(udto);
        });
        return userDTO;
    }

    // this returns user object no matter user is active or inactive
    @Override
    public User getMemberById(Long userId) {
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
    public boolean activeteUser(UserDTO userDTO) {
        User user = new User();
        modelMapper.map(userDTO, user);

        userDAO.activateUser(user);
        return false;
    }

    @Override
    public boolean deactiveUser(UserDTO userDTO) {

        User user = new User();
        modelMapper.map(userDTO, user);
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

    private static String getFolderName(Long userId, String folderId) {
        return Long.toString(userId) + "-" + folderId;
    }

    @Override
    public WedDTO createWedProfile(WedDTO wedDTO, Long addedBy) throws Exception {

        Wed wed = new Wed();
        modelMapper.map(wedDTO, wed);
        wed.setAddedBy(addedBy);
        wed.setFolderId(AppUtils.generateRandomString());
        Wed storedWed = userDAO.createWedProfile(wed);

        // deal with images:
        String folderId = getFolderName(storedWed.getId(), storedWed.getFolderId());

        final String wedUploadImagePath = "imageuploadfolder" + File.separator + folderId + File.separator + "images";
        final String wedUploadDocumentsPath = wedDirectory + File.separator + folderId + File.separator + "documents";
        boolean wedImagedir = new File(wedUploadImagePath).exists() ? true : new File(wedUploadImagePath).mkdirs();
        boolean wedDocdir = new File(wedUploadDocumentsPath).exists() ? true : new File(wedUploadDocumentsPath).mkdirs();
        new File(wedUploadImagePath).setWritable(true);
        new File(wedUploadDocumentsPath).setWritable(true);

        if (!wedDocdir || !wedImagedir) {
            throw new Exception("Unable to create Directory to store uploaded files. Please contact Administrator");
        }

        for (MultipartFile image : wedDTO.getWedImage()) {
            if (image == null) {
                break;
            }
            byte[] bytes = image.getBytes();
            Path path = Paths.get(wedUploadImagePath + File.separator + image.getOriginalFilename());
            Files.write(path, bytes);
        }

        for (MultipartFile doc : wedDTO.getWedJataka()) {
            if (doc == null) {
                break;
            }
            byte[] bytes = doc.getBytes();
            Path path = Paths.get(wedUploadDocumentsPath + File.separator + doc.getOriginalFilename());
            Files.write(path, bytes);
        }

        return wedDTO;

    }

    @Override
    public List<WedDTO> fetchMyWedProfiles(Long loggedinUserId) throws Exception {
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
        try {
            List<WedDTO> webList = fetchMyWedProfiles(addedBy);
            boolean authorized = false;
            for (WedDTO wed : webList) {
                if (wed.getId() == wedDTO.getId()) {
                    authorized = true;
                    break;
                }
            }
            if (!authorized) {
                throw new UserException("UE", "You are not authorized to update this profile.");
            }
            Wed wed = new Wed();
            modelMapper.map(wedDTO, wed);
            wed.setAddedBy(addedBy);
            Wed storedWed = userDAO.updateWedProfile(wed);
            modelMapper.map(storedWed, wedDTO);
            upload(storedWed,wedDTO,"images");
            upload(storedWed,wedDTO,"documents");
            
        return wedDTO;
    }catch(Exception e) {
        
    }
        return wedDTO;

        
    }

    private void upload(Wed storedWed, WedDTO wedDTO,String type) throws Exception {

        String folderId = getFolderName(storedWed.getId(), storedWed.getFolderId());

        final String wedUploadPath = "imageuploadfolder" + File.separator + folderId + File.separator + type;
        boolean wedUploadDir = new File(wedUploadPath).exists() ? true : new File(wedUploadPath).mkdirs();
        new File(wedUploadPath).setWritable(true);

        if (!wedUploadDir) {
            throw new Exception("Unable to create Directory to store uploaded files. Please contact Administrator");
        }
        
        if(type.equalsIgnoreCase("images")) {
        
        for (MultipartFile image : wedDTO.getWedImage()) {
            if (image == null || image.isEmpty()) {
                break;
            }
            byte[] bytes = image.getBytes();
            Path path = Paths.get(wedUploadPath + File.separator + image.getOriginalFilename());
            Files.write(path, bytes);
        }
        } else if (type.equalsIgnoreCase("documents")) {
        
            for (MultipartFile file : wedDTO.getWedJataka()) {
                if (file == null || file.isEmpty()) {
                    break;
                }
                byte[] bytes = file.getBytes();
                Path path = Paths.get(wedUploadPath + File.separator + file.getOriginalFilename());
                Files.write(path, bytes);
            }
            
        }
        
        
        
    }

    @Override
    public User getUserById(String emailId) {
        return userDAO.getUser(emailId);
    }

    @Override
    public UserDTO addMember(UserDTO userDTO) throws Exception {
        User user = new User();
        modelMapper.map(userDTO, user);
        user.setAccountValidTill(AppUtils.getAccountValidFor100Years());
        userDAO.addMember(user);

        modelMapper.map(user, userDTO);

        return userDTO;
    }

    @Override
    public List<User> getAllActiveMembers() {
        return userDAO.getAllActiveMembers();
    }

    @Override
    public List<User> getAllMembers(int numberofUser) {
        return userDAO.getAllMembers();
    }

    @Override
    public boolean makeorremoveAdmin(UserDTO userDTO) {
        User user = new User();
        modelMapper.map(userDTO, user);
        user.setAccountValidTill(AppUtils.getAccountValidFor100Years());
        userDAO.makeorremoveAdmin(user);
        modelMapper.map(user, userDTO);

        return false;
    }

    @Override
    public List<WedDTO> fetchWedProfile(Long loggedInUser, Long profileId) throws Exception {

        List<WedDTO> viewWedDTO = new ArrayList<>();
        ;
        List<WedDTO> wedDto = fetchMyWedProfiles(loggedInUser);

        boolean canview = false;
        for (WedDTO weddto : wedDto) {
            if (weddto.getId() == profileId) {
                canview = true;
                String folderId = getFolderName(weddto.getId(), weddto.getFolderId());
                Path pathImages = Paths.get("imageuploadfolder" + File.separator + folderId + File.separator + "images");
                Path pathJatakams = Paths.get("imageuploadfolder" + File.separator + folderId + File.separator + "documents");
                File folderImages = new File(pathImages.toString());
                File folderDocs = new File(pathJatakams.toString());
                File[] listofImages = folderImages.listFiles();
                File[] listofJatakams = folderDocs.listFiles();
                
                if(listofImages!=null) {
                    String[] imageString  = new String[listofImages.length];
                    for(int i = 0; i<listofImages.length;i++) {
                        imageString[i]=listofImages[i].getPath();
                    }
                    weddto.setWedImageFilePath(imageString);
                }
                if(listofJatakams!=null) {
                    String[] docString  = new String[listofJatakams.length];
                    for(int i = 0; i<listofJatakams.length;i++) {
                        docString[i]=listofJatakams[i].getName();
                    }
                    weddto.setWedJatakaFilePath(docString);
                }
                viewWedDTO.add(weddto);
                break;
            }

        }
        if (!canview) {
            throw new UserException("UE", "You dont have access to view this profile");
        }

        return viewWedDTO;

    }

    @Override
    public WedDTO  deletePhoto(String photoName, String folderName, Long addedBy) throws Exception {

        List<WedDTO> webList = fetchMyWedProfiles(addedBy);
        boolean authorized = false;
        for (WedDTO wed : webList) {
            String folderId = getFolderName(wed.getId(), wed.getFolderId());
            if(!folderId.equalsIgnoreCase(folderName)) {
                continue;
            }else {
            Path pathImages = Paths.get("imageuploadfolder" + File.separator + folderId + File.separator + "images");
            Path pathJatakams = Paths.get("imageuploadfolder" + File.separator + folderId + File.separator + "documents");
            File folderImages = new File(pathImages.toString());
            File folderDocs = new File(pathJatakams.toString());
            File[] listofImages = folderImages.listFiles();
            File[] listofJatakams = folderDocs.listFiles();
            for (File image : listofImages) {
            if(image.getName().equalsIgnoreCase(photoName)) {
                authorized = true;
                if(image.delete()) {
                    System.out.println("File "+ image + " Deleted Successfully by  "+ addedBy);
                    return wed;
                }else {
                    throw new Exception ("Unable to delete file . Please try later");
                }
            }else {
                continue;
            }
            }
            }
        }
        if (!authorized) {
            throw new UserException("UE", "You are not authorized to update this profile.");
        }
        return null;
    }

    @Override
    public WedDTO downloadFile(String fileName, Long loggedInUserId) throws Exception {
        
        List<WedDTO> webList = fetchMyWedProfiles(loggedInUserId);      
        
        for (WedDTO wed : webList) {
            String folderId = getFolderName(wed.getId(), wed.getFolderId());
            Path pathDocs= Paths.get("imageuploadfolder" + File.separator + folderId + File.separator + "documents");
            File folderdocs= new File(pathDocs.toString());
            File[] listofDocs = folderdocs.listFiles();
            String[] docString  = new String[listofDocs.length];
            for (File list : listofDocs) {
                if(list.getName().equalsIgnoreCase(fileName)) {
                    for(int i = 0; i<listofDocs.length;i++) {
                        docString[i]=listofDocs[0].getPath();
                        break;
                    }
                    wed.setWedJatakaFilePath(docString);
                    return wed;
                }
            }
                
            }
        return null;
    }

    @Override
    public boolean  deleteFile(String fileName, long wedId, Long loggedInUserId) throws Exception {
        List<WedDTO> webList = fetchMyWedProfiles(loggedInUserId);  
        for (WedDTO wed : webList) {
            String folderId = getFolderName(wed.getId(), wed.getFolderId());
            
            if(!(wed.getId() == wedId)) {
                continue;
            }
            
            Path pathDocs= Paths.get("imageuploadfolder" + File.separator + folderId + File.separator + "documents");
            File folderdocs= new File(pathDocs.toString());
            File[] listofDocs = folderdocs.listFiles();
       
            for (File file : listofDocs) {
                if(file.getName().equalsIgnoreCase(fileName)) {
                    System.out.println("Deleting the file  "+ fileName +  " by " + loggedInUserId);
                    file.delete();
                    return true;
                }
            }
            }
        return false;
    }

}
