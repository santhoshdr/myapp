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

    @Test
    public void getMyProfile() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(120L);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMTIiLCJpYXQiOjE1NjMyNTQ4NjEsImV4cCI6MTU2Mzg1OTY2MX0.I-4LZ5mU7cQ7zFqpJ1QG3s8zuV40Kb0fama2Fk4s5X9lcgrGyEY1rGNah1dOZwbw_vACcuDNDnMoczEZOGpv-Q");

        HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/user/getMyProfile"), HttpMethod.POST, entity, UserDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateMyProfile() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(120L);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",
                "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMTIiLCJpYXQiOjE1NjMyNTQ4NjEsImV4cCI6MTU2Mzg1OTY2MX0.I-4LZ5mU7cQ7zFqpJ1QG3s8zuV40Kb0fama2Fk4s5X9lcgrGyEY1rGNah1dOZwbw_vACcuDNDnMoczEZOGpv-Q");

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
