package net.drs.myapp.api;

import java.util.Set;

import net.drs.myapp.dto.CompleteRegistrationDTO;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.Role;

public interface IRegistrationService {

	boolean adduser(UserDTO userDTO, Set<Role> roles) throws Exception;

	String forgotPassword(String emailId) throws Exception;

	boolean resetPassword(UserDTO userDTO) throws Exception;

	boolean changePasswordCheckUserAvailable(String emailID) throws Exception;

	Long adduserandGetId(UserDTO userDTO, Set<Role> roles) throws Exception;

	boolean completeRegistration(CompleteRegistrationDTO completeRegistrationDTO)
			throws Exception;

	// boolean addAdministrator(UserDTO userDTO) throws Exception;

	boolean addFotographer(Fotographer fotographer);
}
