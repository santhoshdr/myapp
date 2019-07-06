package net.drs.myapp.app;

import net.drs.myapp.dto.UserDTO;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
    public void createAdmin() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setEmailAddress(emailid);
        userDTO.setMobileNumber("9999999999");
        userDTO.setPassword("password");
        userDTO.setAddress("address");

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        // http://localhost:8085/guest/addUser
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/admin/addAdmin"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        ResponseEntity<UserDTO> response1 = restTemplate.exchange(createURLWithPort("/admin/addAdmin"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response1.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
