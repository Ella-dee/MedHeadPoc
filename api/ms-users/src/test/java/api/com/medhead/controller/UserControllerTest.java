package api.com.medhead.controller;


import api.com.medhead.model.ERole;
import api.com.medhead.model.Patient;
import api.com.medhead.model.Role;
import api.com.medhead.model.User;
import api.com.medhead.payload.request.LoginRequest;
import api.com.medhead.payload.request.RegisterInfoRequest;
import api.com.medhead.payload.request.SignupRequest;
import api.com.medhead.service.PatientService;
import api.com.medhead.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.constraints.AssertTrue;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthController authController;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService userService;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:15-alpine");


    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);

    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private String date1 = "1984-12-24";
    private LocalDate birthdate1 = LocalDate.parse(date1, formatter);
    private SignupRequest signupRequest;
    private RegisterInfoRequest registerInfoRequest;
    private String username;
    private String password;


    protected String getAccessToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jackson.michell@test.com");
        loginRequest.setPassword("password");
        ResultActions result =  mockMvc.perform(MockMvcRequestBuilders
                        .post(AuthController.PATH+"/signin")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("accessToken").toString();
    }

    @Test
    public void getUsersList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(UserController.PATH+"/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getPatient() throws Exception {
        //given
        MvcResult userResult =  mockMvc.perform(MockMvcRequestBuilders
                        .get(UserController.PATH+"/email/jackson.michell@test.com")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper userMapper = new ObjectMapper();
        User user = userMapper.readValue(userResult.getResponse().getContentAsString(), User.class);
        int userId = user.getId();
        //String content = result.getResponse().getContentAsString();

        MvcResult patientResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(UserController.PATH+"/"+userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        ObjectMapper patientMapper = new ObjectMapper();
        patientMapper.registerModule(new JavaTimeModule());
        Patient patient = patientMapper.readValue(patientResult.getResponse().getContentAsString(), Patient.class);

        assertEquals("Mitchell", patient.getLastName());
        assertEquals("Jackson", patient.getFirstName());
    }

    @Test
    public void getRegisteredUserTest() throws Exception {
        User user = getRegisteredUser("jackson.michell@test.com");
        assertEquals("jackson.michell@test.com", user.getEmail());
        ArrayList<Role> roles = new ArrayList();
        Role role = new Role(ERole.ROLE_USER);
        roles.add(role);
        assertEquals(roles.get(0).getName(), user.getRoles().iterator().next().getName());
    }

    private User getRegisteredUser(String email) throws Exception{
        MvcResult userResult =  mockMvc.perform(MockMvcRequestBuilders
                        .get(UserController.PATH+"/email/"+email)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        ObjectMapper userMapper = new ObjectMapper();
        User user = userMapper.readValue(userResult.getResponse().getContentAsString(), User.class);
        return user;
    }

    @Test
    public void registerPatientInfoWithoutNumber() throws Exception {
        this.username="bluebox@badwolfbay.com";
        this.password="password";
        signupRequest = new SignupRequest();
        signupRequest.setUsername(username);
        signupRequest.setPassword(password);
        this.authController.registerUser(signupRequest);

        registerInfoRequest = new RegisterInfoRequest();
        registerInfoRequest.setAddress("Blue box st, Granville Road");
        registerInfoRequest.setCity("London");
        registerInfoRequest.setFirstName("The");
        registerInfoRequest.setLastName("Doctor");
        registerInfoRequest.setPostCode("SJ5 8UW");
        registerInfoRequest.setPhone("097379200348");
        registerInfoRequest.setNhsNumber("ZZZZ873");
        registerInfoRequest.setEmail(this.username);
        registerInfoRequest.setBirthdate(date1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(UserController.PATH+"/patient")
                        .content(objectMapper.writeValueAsString(registerInfoRequest))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());

        Patient patient = patientService.getPatient(getRegisteredUser(this.username).getId());
        assertEquals(51.532844, patient.getLatitude());
        assertEquals(-0.194594, patient.getLongitude());
    }

    @Test
    public void registerPatientInfoWithNumber() throws Exception {
        this.username="winter@iscoming.com";
        this.password="password";
        signupRequest = new SignupRequest();
        signupRequest.setUsername(username);
        signupRequest.setPassword(password);
        this.authController.registerUser(signupRequest);

        registerInfoRequest = new RegisterInfoRequest();
        registerInfoRequest.setAddress("23 Smith Street");
        registerInfoRequest.setCity("London");
        registerInfoRequest.setFirstName("Ned");
        registerInfoRequest.setLastName("Stark");
        registerInfoRequest.setPostCode("SW3 4EW");
        registerInfoRequest.setPhone("097379200348");
        registerInfoRequest.setNhsNumber("ZZZZ875");
        registerInfoRequest.setEmail(this.username);
        registerInfoRequest.setBirthdate(date1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(UserController.PATH+"/patient")
                        .content(objectMapper.writeValueAsString(registerInfoRequest))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());

        Patient patient = patientService.getPatient(getRegisteredUser(this.username).getId());
        assertEquals(51.48864, patient.getLatitude());
        assertEquals(-0.162577, patient.getLongitude());
    }
    @Test
    public void registerPatientInfo_KO() throws Exception {
        this.username="hello@sweetie.com";
        this.password="password";
        signupRequest = new SignupRequest();
        signupRequest.setUsername(username);
        signupRequest.setPassword(password);
        this.authController.registerUser(signupRequest);

        registerInfoRequest = new RegisterInfoRequest();
        registerInfoRequest.setAddress("Blue box st, Granville Road");
        registerInfoRequest.setCity("London");
        registerInfoRequest.setFirstName("The");
        registerInfoRequest.setLastName("Doctor");
        registerInfoRequest.setPostCode("SJ5 8UW");
        registerInfoRequest.setPhone("097379200348");
        registerInfoRequest.setNhsNumber("ZZ361112T");
        registerInfoRequest.setEmail(this.username);
        registerInfoRequest.setBirthdate(date1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(UserController.PATH+"/patient")
                        .content(objectMapper.writeValueAsString(registerInfoRequest))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn();
        String message = result.getResponse().getContentAsString();
        assertTrue(message.contains("PSQLException: ERROR: duplicate key value violates unique constraint"));
        assertTrue(message.contains("Key (ssnumber)=(ZZ361112T) already exists"));
    }

    @Test
    public void registerPatientInfo_KO_Address() throws Exception {
        this.username="imaginary@adress.com";
        this.password="password";
        signupRequest = new SignupRequest();
        signupRequest.setUsername(username);
        signupRequest.setPassword(password);
        this.authController.registerUser(signupRequest);

        registerInfoRequest = new RegisterInfoRequest();
        registerInfoRequest.setAddress("Imaginary Road");
        registerInfoRequest.setCity("London");
        registerInfoRequest.setFirstName("Imaginary");
        registerInfoRequest.setLastName("Name");
        registerInfoRequest.setPostCode("SJ5 8UW");
        registerInfoRequest.setPhone("097379200348");
        registerInfoRequest.setNhsNumber("ZZ361112X");
        registerInfoRequest.setEmail(this.username);
        registerInfoRequest.setBirthdate(date1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(UserController.PATH+"/patient")
                        .content(objectMapper.writeValueAsString(registerInfoRequest))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn();
        String message = result.getResponse().getContentAsString();
        assertTrue(message.contains("The address could not be geolocalized"));
    }
}