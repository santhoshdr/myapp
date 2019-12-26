package net.drs.myapp.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.drs.myapp.dto.LoginRequest;
import net.drs.myapp.dto.LoginResponse;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserDTO;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTests {

    private String emailid;

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Before
    public void generateUniqueEmailid() {
        emailid = System.nanoTime() + "@email.com";
    }

    protected static LoginResponse userLoginResponse;

    protected static LoginResponse adminLoginResponse;

    protected static String staticEmailid;

    @Test
    public void acreateOneUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(emailid);
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        // http://localhost:8085/guest/addUser
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    // @Test
    public void acreateAdminUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress("sdfsdfdfsddddddddddd");
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        // http://localhost:8085/guest/addUser
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<UserDTO> response1 = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response1.getStatusCode(), HttpStatus.BAD_REQUEST);

        // http://localhost:8085/api/auth/signin

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail(emailid);
        loginRequest.setPassword("password");

        HttpEntity<LoginRequest> loginEntity = new HttpEntity<LoginRequest>(loginRequest, headers);
        ResponseEntity<LoginResponse> signinResponse = restTemplate.exchange(createURLWithPort("/api/auth/signin"), HttpMethod.POST, loginEntity, LoginResponse.class);
        userLoginResponse = signinResponse.getBody();
        assertNotNull(signinResponse.getBody());

    }

    @Test
    public void acreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(emailid);
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        // http://localhost:8085/guest/addUser
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<UserDTO> response1 = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response1.getStatusCode(), HttpStatus.BAD_REQUEST);

        // http://localhost:8085/api/auth/signin

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail(emailid);
        loginRequest.setPassword("password");

        HttpEntity<LoginRequest> loginEntity = new HttpEntity<LoginRequest>(loginRequest, headers);
        ResponseEntity<LoginResponse> signinResponse = restTemplate.exchange(createURLWithPort("/api/auth/signin"), HttpMethod.POST, loginEntity, LoginResponse.class);
        userLoginResponse = signinResponse.getBody();
        assertNotNull(signinResponse.getBody());

    }

    @Test
    public void changePassword() {

        String accessToken = "";
        String accessType = "";

        // Registration
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(emailid);
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        // http://localhost:8085/guest/addUser
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        // Login

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail(emailid);
        loginRequest.setPassword("password");

        HttpEntity<LoginRequest> loginEntity = new HttpEntity<LoginRequest>(loginRequest, headers);
        ResponseEntity<LoginResponse> signinResponse = restTemplate.exchange(createURLWithPort("/api/auth/signin"), HttpMethod.POST, loginEntity, LoginResponse.class);
        assertEquals(signinResponse.getStatusCode(), HttpStatus.OK);
        LoginResponse resp = signinResponse.getBody();
        accessToken = resp.getAccessToken();
        accessType = resp.getTokenType();
        assertNotNull(signinResponse.getBody());

        // Reset Password
        ResetPasswordDTO passwordDTO = new ResetPasswordDTO();
        passwordDTO.setEmailid(emailid);
        passwordDTO.setCurrentPassword("password");
        passwordDTO.setNewPassword("password123");
        passwordDTO.setConfirmNewPassword("password123");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", accessType + " " + accessToken);

        HttpEntity<ResetPasswordDTO> resetPasswordEntity = new HttpEntity<ResetPasswordDTO>(passwordDTO, headers);
        ResponseEntity<Object> resetPasswordResponse = restTemplate.exchange(createURLWithPort("/user/changePassword"), HttpMethod.POST, resetPasswordEntity, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        LoginRequest loginRequestAgain = new LoginRequest();
        loginRequestAgain.setUsernameOrEmail(emailid);
        loginRequestAgain.setPassword("password123");

        HttpEntity<LoginRequest> loginEntityAgain = new HttpEntity<LoginRequest>(loginRequestAgain, headers);
        ResponseEntity<LoginResponse> signinResponseagain = restTemplate.exchange(createURLWithPort("/api/auth/signin"), HttpMethod.POST, loginEntityAgain, LoginResponse.class);
        LoginResponse respAgain = signinResponseagain.getBody();
        assertEquals(signinResponseagain.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void getMyProfile() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", this.userLoginResponse.getTokenType() + " " + this.userLoginResponse.getAccessToken());

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/user/getMyProfile"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateMyProfile() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", this.userLoginResponse.getTokenType() + " " + this.userLoginResponse.getAccessToken());
        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/user/getMyProfile"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        UserDTO userDTOFromResponse = response.getBody();
        userDTOFromResponse.setFirstName("UpdatedFirstName123");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMTIiLCJpYXQiOjE1NjMyNTQ4NjEsImV4cCI6MTU2Mzg1OTY2MX0.I-4LZ5mU7cQ7zFqpJ1QG3s8zuV40Kb0fama2Fk4s5X9lcgrGyEY1rGNah1dOZwbw_vACcuDNDnMoczEZOGpv-Q");

        HttpEntity<UserDTO> entity1 = new HttpEntity<UserDTO>(userDTOFromResponse, headers);
        ResponseEntity<Boolean> response1 = restTemplate.exchange(createURLWithPort("/user/updateMyProfile"), HttpMethod.POST, entity1, Boolean.class);
        assertEquals(response1.getStatusCode(), HttpStatus.OK);

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
