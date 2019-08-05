package net.drs.myapp.api.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dao.IRegistrationDAO;
import net.drs.myapp.dto.CompleteRegistrationDTO;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.CompleteUserDetails;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.mqservice.NotificationRequest;
import net.drs.myapp.mqservice.RabbitMqService;
import net.drs.myapp.utils.AppUtils;

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

	@Autowired
	RabbitMqService rabbitMqService;

	@Autowired
	IUserDetails userDetails;

	@Autowired
	INotifyByEmail notificationByEmailService;

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
				user.setPassword(new BCryptPasswordEncoder().encode(user
						.getPassword()));
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
	public boolean completeRegistration(
			CompleteRegistrationDTO completeRegistrationDTO) throws Exception {
		Users users = null;
		CompleteUserDetails completeUserDetails = new CompleteUserDetails();
		modelMapper.map(completeRegistrationDTO, completeUserDetails);
		users = registrationDAO
				.checkIfUserPhoneisPresentandVerified(completeRegistrationDTO
						.getUserIdorEmailAddress());
		if (null != users) {
			completeUserDetails.setUserId(users.getId());
			registrationDAO.completeRegistration(completeUserDetails);
		} else {
			users = registrationDAO
					.checkIfUserPhoneisPresentandVerified(completeRegistrationDTO
							.getUserIdorEmailAddress());
		}
		if (users == null) {
			throw new Exception(
					"Email or Mobile is not registered. Please register with you email id or phone number");
		}
		return false;
	}

	@Override
	public Long adduserandGetId(UserDTO userDTO, Set<Role> roles)
			throws Exception {
		Long userId = 0L;
		try {
			User user = new User();
			modelMapper.map(userDTO, user);
			boolean result = registrationDAO.checkIfUserExistbyEmailId(user);
			if (!result) {

				user.setPassword(AppUtils.encryptPassword(user.getPassword()));
				userId = registrationDAO.addUserandGetUserId(user, roles);
				File filetobeUploaded = userDTO.getImage() != null ? userDTO
						.getImage() : null;

				if (uploadimage != null && uploadimage.equals("folder")
						&& filetobeUploaded != null) {
					byte[] bytes = Files
							.readAllBytes(filetobeUploaded.toPath());
					Path path = Paths.get(uploadImageLocation + File.separator
							+ userId + "--" + filetobeUploaded.getName());
					Files.write(path, bytes);
				}
			} else {
				throw new Exception("Some problem.");
			}
		} catch (Exception e) {
			throw e;
		}
		return userId;
	}

	@Override
	public String forgotPassword(String emailId) throws Exception {

		// check if user exist and is active
		Users users = registrationDAO
				.checkIfUserPhoneisPresentandVerified(emailId);
		if (users == null) {
			throw new Exception("Email Id Not found ...");
		}
		System.out.println("Resetting password");
		String temperoryPassword = AppUtils.generateRandomString();
		users.setEmail(emailId);
		users.setPassword(temperoryPassword);
		users.setId(users.getId());
		boolean result = registrationDAO.updateUserWithTemperoryPassword(users);
		if (!result) {
			throw new Exception(
					"Unable to Reset Password. Kindly Try after some time. OR Contact Administrator.");
		}

		EmailDTO emailDto = new EmailDTO();
		java.util.Date uDate = new java.util.Date();
		emailDto.setEmailId(emailId);
		emailDto.setCreatedBy(ApplicationConstants.USER_SYSTEM);
		emailDto.setCreationDate(new java.sql.Date(uDate.getTime()));
		emailDto.setUpdatedBy(ApplicationConstants.USER_SYSTEM);
		emailDto.setUpdatedDate(new java.sql.Date(uDate.getTime()));
		emailDto.setEmailTemplateId("FORGOTPASSWORD_EMAIL");
		emailDto.setUserID(new Long(123));
		emailDto.setNeedtoSendEmail(true);
		Long notificationId = notificationByEmailService
				.insertDatatoDBforNotification(emailDto);

		rabbitMqService.publishSMSMessage(new NotificationRequest(
				notificationId, emailId, "FORGOTPASSWORD_EMAIL"));
		return "SUCCESS";
	}

	@Override
	public boolean resetPassword(UserDTO userDTO) throws Exception {

		Users users = registrationDAO
				.checkIfUserPhoneisPresentandVerified(userDTO.getEmailAddress());
		if (users == null) {
			throw new Exception("Email Id Not found ...");
		}
		users.setPassword(new BCryptPasswordEncoder().encode(userDTO
				.getPassword()));
		registrationDAO.resetPassword(users);
		return true;
	}

	@Override
	public boolean changePasswordCheckUserAvailable(String userNameOrEmailid)
			throws Exception {
		User user = userDetails.getUserById(userNameOrEmailid);
		if (user == null) {
			throw new Exception("User with this emailid is not present");
		} else if (user.isActive()) {
			throw new Exception("User is Not Active.");
		}
		return true;
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
