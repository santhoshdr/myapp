package net.drs.myapp.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import net.drs.myapp.dto.LoginRequest;
import net.drs.myapp.dto.LoginResponse;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.dto.UserServiceDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppApplicationTests {

    private String emailid;

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    private static LoginResponse adminLoginResponse;

    private static String emailIdUsedForTestCases;

    @Before
    public void generateUniqueEmailid() {
        emailid = System.nanoTime() + "@email.com";
    }

    @Test
    public void forgotPassword() {
        String forgottenEmailid = emailIdUsedForTestCases;
        HttpEntity<String> entity = new HttpEntity<String>(forgottenEmailid, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/forgotPassword"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void resetPassword() {

        UserDTO userDTO = new UserDTO();

        userDTO.setEmailAddress(emailIdUsedForTestCases);
        userDTO.setMobileNumber("9999999999");
        userDTO.setTemperoryPassword("rR5jd8mD");
        // current password
        userDTO.setPassword("newpassword");
        userDTO.setConfirmPassword("newpassword");
        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/resetPassword"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void createUserWithImage() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(emailid);
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");
        userDTO.setImage(new File("imagesourcefolder/male.png"));

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<UserDTO> response1 = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response1.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void acreateAndLoginAdminWithGuestusingGuestTestApi() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(emailid);
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");

        userDTO.setEmailAddress("one" + emailid);
        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        // http://localhost:8085/guest/addUser
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/addAdmin"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        LoginRequest loginReq = new LoginRequest();
        loginReq.setUsernameOrEmail(userDTO.getEmailAddress());
        loginReq.setPassword(userDTO.getPassword());
        HttpEntity<LoginRequest> loginEntity = new HttpEntity<LoginRequest>(loginReq, headers);
        ResponseEntity<LoginResponse> loginResponse = restTemplate.exchange(createURLWithPort("/api/auth/signin"), HttpMethod.POST, loginEntity, LoginResponse.class);
        adminLoginResponse = loginResponse.getBody();
        assertEquals(loginResponse.getStatusCode(), HttpStatus.OK);

        emailIdUsedForTestCases = userDTO.getEmailAddress();

        HttpHeaders badTokenHeader = new HttpHeaders();
        badTokenHeader.add("Authorization", "Bearer badtoken");
        userDTO.setEmailAddress("badtoken" + emailid);
        entity = new HttpEntity<UserDTO>(userDTO, badTokenHeader);

        // throws exception . Response is not sent back.. Need to check this.
        // ResponseEntity<UserDTO> response2 =
        // restTemplate.exchange(createURLWithPort("/admin/addAdmin"),
        // HttpMethod.POST, entity, UserDTO.class);
        // assertEquals(response2.getStatusCode(), HttpStatus.BAD_REQUEST);

        // Empty Token
        // headers.add("Authorization","" );
        // userDTO.setEmailAddress("two"+emailid);
        // entity = new HttpEntity<UserDTO>(userDTO, headers);
        // ResponseEntity<UserDTO> response3 =
        // restTemplate.exchange(createURLWithPort("/admin/addAdmin"),
        // HttpMethod.POST, entity, UserDTO.class);
        // assertEquals(response3.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @Ignore
    public void createAndLoginAdmin() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(emailid);
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyODciLCJpYXQiOjE1NjQxMjAxNDEsImV4cCI6MTU2NDcyNDk0MX0.78eRnWgsjBeXRVlabvzi_58BV9iBbua33OP8xSlgtMWOsgOH8NkNu41EQrqHlV5p07tznJ4kB2CiqNiIndFGfQ");
        userDTO.setEmailAddress("one" + emailid);
        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        // http://localhost:8085/guest/addUser
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/admin/addAdmin"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<UserDTO> response1 = restTemplate.exchange(createURLWithPort("/admin/addAdmin"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response1.getStatusCode(), HttpStatus.BAD_REQUEST);

        LoginRequest loginReq = new LoginRequest();
        loginReq.setUsernameOrEmail(userDTO.getEmailAddress());
        loginReq.setPassword(userDTO.getPassword());
        HttpEntity<LoginRequest> loginEntity = new HttpEntity<LoginRequest>(loginReq, headers);
        ResponseEntity<LoginResponse> loginResponse = restTemplate.exchange(createURLWithPort("/api/auth/signin"), HttpMethod.POST, loginEntity, LoginResponse.class);
        adminLoginResponse = loginResponse.getBody();
        assertEquals(loginResponse.getStatusCode(), HttpStatus.OK);

        HttpHeaders badTokenHeader = new HttpHeaders();
        badTokenHeader.add("Authorization", "Bearer badtoken");
        userDTO.setEmailAddress("badtoken" + emailid);
        entity = new HttpEntity<UserDTO>(userDTO, badTokenHeader);

        // throws exception . Response is not sent back.. Need to check this.
        // ResponseEntity<UserDTO> response2 =
        // restTemplate.exchange(createURLWithPort("/admin/addAdmin"),
        // HttpMethod.POST, entity, UserDTO.class);
        // assertEquals(response2.getStatusCode(), HttpStatus.BAD_REQUEST);

        // Empty Token
        // headers.add("Authorization","" );
        // userDTO.setEmailAddress("two"+emailid);
        // entity = new HttpEntity<UserDTO>(userDTO, headers);
        // ResponseEntity<UserDTO> response3 =
        // restTemplate.exchange(createURLWithPort("/admin/addAdmin"),
        // HttpMethod.POST, entity, UserDTO.class);
        // assertEquals(response3.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getAllUsers() {
        ObjectMapper objectMapper = new ObjectMapper();

        UserDTO userDTO = new UserDTO();
        headers.add("Authorization", adminLoginResponse.getTokenType() + " " + adminLoginResponse.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        ResponseEntity<?> response = restTemplate.exchange(createURLWithPort("/admin/getAllUsers"), HttpMethod.GET, entity, Object.class);
        List<?> list = (List<?>) response.getBody();
        for (Object item : list) {
            UserServiceDTO user = objectMapper.convertValue(item, UserServiceDTO.class);
        }
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(list.size() > 0);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
