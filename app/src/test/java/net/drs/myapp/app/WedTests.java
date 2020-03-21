package net.drs.myapp.app;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.drs.myapp.dto.LoginResponse;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.utils.MaritalStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WedTests extends GenericAbstractTests {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void acreateWedUserWithAuthorizedUser() {

        LoginResponse loginresponse = userLoginResponse;
        WedDTO wed = new WedDTO();

        String timeString = "9:05:30";
        wed.setWedAge(30);
        wed.setWedGender("Male");
        wed.setWedMobilenumber("123456789");
        wed.setWedOccupation("Engineer");
        wed.setWedPlace("Bangalore");
        wed.setWedNakshtra("Rohini");
        wed.setWedRaashi("Vrishaba");
        wed.setMakePublic(true);
        wed.setMaritalStatus(MaritalStatus.SINGLE);
        wed.setPlaceOfBirth("placeofbirth");
        wed.setDateOfBirth(new Date(System.currentTimeMillis()));

        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        Time time = Time.valueOf(timeString);
        wed.setTimeOfBirth(time);

        File image1 = new File("imagesourcefolder/male.png");
        File image2 = new File("imagesourcefolder/woman.png");

        File[] imageList = new File[10];
        File[] jatakaList = new File[10];

        imageList[0] = image1;
        imageList[1] = image2;
        wed.setWedImage(imageList);
        File jataka1 = new File("imagesourcefolder/doc1.txt");
        File jataka2 = new File("imagesourcefolder/doc2.txt");

        jatakaList[0] = jataka1;
        jatakaList[1] = jataka2;
        wed.setWedJataka(jatakaList);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", loginresponse.getTokenType() + " " + loginresponse.getAccessToken());

        HttpEntity<WedDTO> entity = new HttpEntity<WedDTO>(wed, headers);
        ResponseEntity<WedDTO> response = restTemplate.exchange(createURLWithPort("/user/createWedProfile"), HttpMethod.POST, entity, WedDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        response.getBody();

    }

    @Test(expected = ResourceAccessException.class)
    public void createWedUserWithUnAuthorizedUser() {
        WedDTO wed = new WedDTO();
        wed.setWedAge(30);
        wed.setWedGender("Male");
        wed.setWedMobilenumber("00000000000");
        wed.setWedOccupation("Engineer");
        wed.setWedPlace("Bangalore");
        wed.setWedNakshtra("Rohini");
        wed.setWedRaashi("Vrishaba");
        wed.setMakePublic(true);

        File image1 = new File("classpath:/abc");
        File image2 = new File("C:\\Users\\srajanna\\Desktop\\DeploymentManager\\166male-upload-md.png");

        File[] imageList = new File[10];
        File[] jatakaList = new File[10];

        imageList[0] = image1;
        imageList[1] = image2;
        wed.setWedImage(imageList);
        File jataka1 = new File("C:\\Users\\srajanna\\Desktop\\DeploymentManager\\Example_DeploymentUnit.doc");
        File jataka2 = new File("C:\\Users\\srajanna\\Desktop\\DeploymentManager\\Example_DeploymentUnit.doc");

        jatakaList[0] = jataka1;
        jatakaList[1] = jataka2;
        wed.setWedJataka(jatakaList);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer wrongkey");

        HttpEntity<WedDTO> entity = new HttpEntity<WedDTO>(wed, headers);
        try {
            ResponseEntity<WedDTO> response = restTemplate.exchange(createURLWithPort("/user/createWedProfile"), HttpMethod.POST, entity, WedDTO.class);
        } catch (Exception re) {
            throw re;
        }

    }

    @Test
    public void fetchWedUserWithAuthorizedUser() {

        LoginResponse loginresponse = userLoginResponse;

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", loginresponse.getTokenType() + " " + loginresponse.getAccessToken());
        HttpEntity<WedDTO> entity = new HttpEntity<WedDTO>(null, headers);
        ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort("/user/fetchWedProfile"), HttpMethod.GET, entity, Object.class);

        List<WedDTO> listOfWed = (List<WedDTO>) response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        response.getBody();

    }

    @Test
    public void updateWedUserWithAuthorizedUser() {

        LoginResponse loginresponse = userLoginResponse;

        ObjectMapper objectMapper = new ObjectMapper();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", loginresponse.getTokenType() + " " + loginresponse.getAccessToken());
        HttpEntity<WedDTO> entity = new HttpEntity<WedDTO>(null, headers);
        ResponseEntity<List> response = restTemplate.exchange(createURLWithPort("/user/fetchWedProfile"), HttpMethod.GET, entity, List.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        List<WedDTO> listOfWed = (List<WedDTO>) response.getBody();

        Object item = listOfWed.size() > 0 ? listOfWed.get(0) : null;
        WedDTO wedDTO = objectMapper.convertValue(item, WedDTO.class);

        // TODO : Relook below code
        // wedDTO.setWedFullName("UpdatedFirstName123");
        //
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.add("Authorization",
        // "Bearer
        // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNzkiLCJpYXQiOjE1NjMyNjgxMjAsImV4cCI6MTU2Mzg3MjkyMH0._FL_9a8b8-0UxH3ilorBg9qMoQLgXZbcbtUZI9zpWvHQXIfyb2xe4SZgSa8gLKETIbXCP_F7gOCUNno2reP52g");
        //
        // HttpEntity<WedDTO> entity1 = new HttpEntity<WedDTO>(wedDTO, headers);
        // ResponseEntity<Object> response1 =
        // restTemplate.exchange(createURLWithPort("/user/updateWedProfile"),
        // HttpMethod.POST, entity1, Object.class);
        // assertEquals(response1.getStatusCode(), HttpStatus.OK);

    }

}
