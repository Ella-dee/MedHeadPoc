package api.com.medhead.controller;

import api.com.medhead.model.ERole;
import api.com.medhead.model.Patient;
import api.com.medhead.model.Role;
import api.com.medhead.model.User;
import api.com.medhead.payload.request.RegisterInfoRequest;
import api.com.medhead.repository.PatientRepository;
import api.com.medhead.repository.UserRepository;
import api.com.medhead.service.PatientService;
import api.com.medhead.service.UserService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    UserController userController;
    @Autowired
    UserService userService;
    @Autowired
    PatientService patientService;

    @MockBean
    UserRepository userRepository;
    @MockBean
    PatientRepository patientRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private String date1 = "1984-12-24";
    private LocalDate birthdate1 = LocalDate.parse(date1, formatter);

    private User u1 = new User(2,  "password", "bluebox@badwolfbay.com");
    private User u2 = new User(4, "password","test@test.com");
    private Patient p1 = new Patient (5,  "The", "Doctor", "Blue box st, Granville Road", "London", "SJ5 8UW",-0.163228,51.488953, "097379200348","bluebox@badwolfbay.com", birthdate1, "ZZZZ873", u1);

    private Role role = new Role(ERole.ROLE_USER);
    private Set<Role> roles  = new HashSet<>();
    private List<User> userList=new ArrayList<>();
    private RegisterInfoRequest registerInfoRequest = new RegisterInfoRequest();

    @BeforeEach
    void setup_test(){
        roles.add(role);
        u1.setRoles(roles);
        userList.add(u1);
        userList.add(u2);

        registerInfoRequest.setAddress(p1.getAddress());
        registerInfoRequest.setCity(p1.getCity());
        registerInfoRequest.setEmail(p1.getEmail());
        registerInfoRequest.setFirstName(p1.getFirstName());
        registerInfoRequest.setLastName(p1.getLastName());
        registerInfoRequest.setPostCode(p1.getPostCode());
        registerInfoRequest.setPhone(p1.getPhone());
        registerInfoRequest.setNhsNumber(p1.getSocialSecurityNumber());
        registerInfoRequest.setBirthdate(date1);
    }

    @Test
    void getUsersList() {
        when(userRepository.findAll()).thenReturn(userList);
        List<User> users = userController.getUsersList();
        assertEquals(2, users.size());
    }

    @Test
    void getPatient() {
        when(patientRepository.findByUserId(p1.getUser().getId())).thenReturn(p1);
        Patient p = userController.getPatient(u1.getId());
        assertEquals("Doctor", p.getLastName());
    }

    @Test
    void getRegisteredUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(u1));
        Optional<User> user = userService.getRegisteredUser(u1.getEmail());
        assertEquals("bluebox@badwolfbay.com", user.get().getEmail());
    }

    @Test
    void registerPatientInfo() throws JSONException, IOException, InterruptedException {
        when(patientRepository.findByEmail(u1.getEmail())).thenReturn(p1);
        when(patientRepository.save(any())).thenReturn(p1);
        Patient patient = patientService.registerPatientInfo(registerInfoRequest);
        assertEquals(51.532844, patient.getLatitude());
        assertEquals(-0.194594, patient.getLongitude());
    }
}