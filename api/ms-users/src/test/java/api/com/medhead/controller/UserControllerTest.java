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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerTest extends ContainerBase{

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


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private String date1 = "1984-12-24";
    private LocalDate birthdate1 = LocalDate.parse(date1, formatter);
    private SignupRequest signupRequest;
    private RegisterInfoRequest registerInfoRequest;
    private String username;
    private String password;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

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

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
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
        MvcResult userResult =  mockMvc.perform(MockMvcRequestBuilders
                        .get(UserController.PATH+"/email/jackson.michell@test.com")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
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
    public void registerPatientInfo() throws Exception {
        this.username="bluebox@badwolfbay.com";
        this.password="password";
        signupRequest = new SignupRequest();
        signupRequest.setUsername(username);
        signupRequest.setPassword(password);
        this.authController.registerUser(signupRequest);
        assertEquals(4, getRegisteredUser(this.username).getId());
        assertEquals(this.username, patientService.getPatient(4).getEmail());

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

        Patient patient = patientService.getPatient(4);
        assertEquals(51.532844, patient.getLatitude());
        assertEquals(-0.194594, patient.getLongitude());
    }
}