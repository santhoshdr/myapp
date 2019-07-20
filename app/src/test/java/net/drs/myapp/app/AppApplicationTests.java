package net.drs.myapp.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.dto.UserServiceDTO;

import org.junit.Before;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppApplicationTests {

    private String emailid;

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Before
    public void generateUniqueEmailid() {
        emailid = System.nanoTime() + "@email.com";
    }

    @Test
    public void forgotPassword() {
        String forgottenEmailid = "77686586894900@email.com";
        HttpEntity<String> entity = new HttpEntity<String>(forgottenEmailid, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/forgotPassword"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void resetPassword() {

        UserDTO userDTO = new UserDTO();

        userDTO.setEmailAddress("77686586894900@email.com");
        userDTO.setMobileNumber("9999999999");
        userDTO.setTemperoryPassword("rR5jd8mD");
        userDTO.setPassword("newpassword");
        userDTO.setConfirmPassword("newpassword");
        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/resetPassword"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void createUser() {
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
        userDTO.setImage(new File("C:\\Users\\srajanna\\Desktop\\male-upload-md.png"));

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<UserDTO> response1 = restTemplate.exchange(createURLWithPort("/guest/addUser"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response1.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createAdmin() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(emailid);
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNzkiLCJpYXQiOjE1NjMyNjgxMjAsImV4cCI6MTU2Mzg3MjkyMH0._FL_9a8b8-0UxH3ilorBg9qMoQLgXZbcbtUZI9zpWvHQXIfyb2xe4SZgSa8gLKETIbXCP_F7gOCUNno2reP52g");
        userDTO.setEmailAddress("one" + emailid);
        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        // http://localhost:8085/guest/addUser
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/admin/addAdmin"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<UserDTO> response1 = restTemplate.exchange(createURLWithPort("/admin/addAdmin"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response1.getStatusCode(), HttpStatus.BAD_REQUEST);

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
        headers.add("Authorization",
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNzkiLCJpYXQiOjE1NjMyNjgxMjAsImV4cCI6MTU2Mzg3MjkyMH0._FL_9a8b8-0UxH3ilorBg9qMoQLgXZbcbtUZI9zpWvHQXIfyb2xe4SZgSa8gLKETIbXCP_F7gOCUNno2reP52g");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        ResponseEntity<?> response = restTemplate.exchange(createURLWithPort("/admin/getAllUsers"), HttpMethod.GET, entity, Object.class);
        List<?> list = (List<?>) response.getBody();
        for (Object item : list) {
            UserServiceDTO u = objectMapper.convertValue(item, UserServiceDTO.class);
            System.out.println("FirstName" + u.getId() + " -- " + "Image" + u.getImage());
        }
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(list.size() > 0);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
