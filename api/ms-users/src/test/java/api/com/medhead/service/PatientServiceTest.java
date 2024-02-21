package api.com.medhead.service;

import api.com.medhead.model.Patient;
import api.com.medhead.model.User;
import api.com.medhead.payload.request.RegisterInfoRequest;
import api.com.medhead.repository.PatientRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private String date1 = "1982-03-28";
    private String date2 = "1984-12-24";
    private LocalDate birthdate1 = LocalDate.parse(date1, formatter);
    private LocalDate birthdate2 = LocalDate.parse(date2, formatter);

    private User u1 = new User(2,  "password", "jayden.reid@test.com");
    private Patient p1 = new Patient (5,  "Jayden", "Reid", "23 Smith St", "London", "SW3 4EW",51.488954, -0.163229,"020 7730 9182","jayden.reid@test.com", birthdate1, "ZZ204368T", u1);

    private User u2 = new User(4, "password","test@test.com");
    private Patient p2 = new Patient (5,  "The", "Doctor", "Blue box st", "London", "SJ5 8UW",51.488953, -0.163228,"097379200348","bluebox@tardis.com", birthdate1, "ZZZZ873", u2);

    private User u3 = new User(7, "password","mandy.lloyd@test.com");
    private Patient p3 = new Patient("mandy.lloyd@test.com");

    private RegisterInfoRequest registerInfoRequest = new RegisterInfoRequest();

    @BeforeEach
    void setup_test() {
        p3.setUser(u3);
        p3.setId(15);
        u3.setPatient(p3);
        registerInfoRequest.setAddress("Crown Passage, St James's");
        registerInfoRequest.setCity("London");
        registerInfoRequest.setEmail("mandy.lloyd@test.com");
        registerInfoRequest.setFirstName("Mandy");
        registerInfoRequest.setLastName("Lloyd");
        registerInfoRequest.setPostCode("SW1Y 6QY");
        registerInfoRequest.setPhone("020 7839 8831");
        registerInfoRequest.setNhsNumber("ZZ57VJS7T");
        registerInfoRequest.setBirthdate(date1);
    }

    @Test
    void getPatient() {
        when(patientRepository.findByUserId(4)).thenReturn(p2);
        Patient p1 = patientService.getPatient(4);
        assertEquals("The", p1.getFirstName());
    }

    @Test
    void findPatientByEmail() {
        when(patientRepository.findByEmail("bluebox@tardis.com")).thenReturn(p2);
        Patient p1 = patientService.findPatientByEmail("bluebox@tardis.com");
        assertEquals("The", p1.getFirstName());
    }

    @Test
    void registerPatientInfo() throws JSONException, IOException, InterruptedException {
        when(patientRepository.findByEmail(u3.getEmail())).thenReturn(p3);
        when(patientRepository.save(any())).thenReturn(p3);
        Patient patient = patientService.registerPatientInfo(registerInfoRequest);
        assertEquals(51.503129, patient.getLatitude());
        assertEquals(-0.133165, patient.getLongitude());
    }

    @Test
    void setUpPatient() {
        when(patientRepository.findByEmail(u3.getEmail())).thenReturn(p3);
        Patient patient = patientService.setUpPatient(registerInfoRequest);
        assertEquals(birthdate1, patient.getBirthdate());
    }

    @Test
    void addressWithNumber() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("street","jump St");
        json.put("housenumber", "21");

        String patientAddress2 = patientService.addressWithNumber(json, "jump St");
        assertEquals("21 jump Street", patientAddress2);

   }

    @Test
    void addressWithoutNumber() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("street","Crown Passage");
        json.put("name","St. James's Park");
        String patientAddress = patientService.addressWithoutNumber(json, registerInfoRequest.getAddress());
        assertEquals("Crown Passage, St James's", patientAddress);

        JSONObject json2 = new JSONObject();
        json.put("street","Bad Wolf St");
        String patientAddress2 = patientService.addressWithoutNumber(json2, "Bad Wolf St");
        assertEquals("Bad Wolf Street", patientAddress2);
    }
}