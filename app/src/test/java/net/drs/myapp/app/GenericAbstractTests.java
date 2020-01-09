package net.drs.myapp.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.drs.myapp.dto.LoginRequest;
import net.drs.myapp.dto.LoginResponse;
import net.drs.myapp.dto.UserDTO;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class GenericAbstractTests {

    @LocalServerPort
    private int port = 8085;

    protected static LoginResponse adminLoginResponse;

    protected static LoginResponse userLoginResponse;

    private String emailid;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();
    
    
    protected String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    public String generateUniqueEmailid() {
        this.emailid = System.nanoTime() + "@email.com";
        return this.emailid;
    }

    GenericAbstractTests() {
        acreateAdminUser();
        acreateUser();
    }

    public void acreateAdminUser() {
        
        String userEmailid = generateUniqueEmailid();
        
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(userEmailid);
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        // http://localhost:8085/guest/addUser
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<UserDTO> response1 = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response1.getStatusCode(), HttpStatus.BAD_REQUEST);

        // Activate User:

        UserDTO userDTOtoactivateUser = new UserDTO();
        userDTOtoactivateUser.setEmailAddress(userEmailid);
        userDTOtoactivateUser.setTemporaryActivationString("zoom123");
        HttpEntity<UserDTO> entityToactivateUser = new HttpEntity<UserDTO>(userDTOtoactivateUser, headers);

        ResponseEntity<UserDTO> response2 = restTemplate.exchange(createURLWithPort("/guest/activateUser"), HttpMethod.POST, entityToactivateUser, UserDTO.class);
        assertEquals(response2.getStatusCode(), HttpStatus.CREATED);

        // http://localhost:8085/api/auth/signin

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail(emailid);
        loginRequest.setPassword("password");

        HttpEntity<LoginRequest> loginEntity = new HttpEntity<LoginRequest>(loginRequest, headers);
        ResponseEntity<LoginResponse> signinResponse = restTemplate.exchange(createURLWithPort("/api/auth/signin"), HttpMethod.POST, loginEntity, LoginResponse.class);
        adminLoginResponse = signinResponse.getBody();
        assertNotNull(signinResponse.getBody().getAccessToken());
        assertNotNull(signinResponse.getBody().getTokenType());

    }

    public void acreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(generateUniqueEmailid());
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

 
}
