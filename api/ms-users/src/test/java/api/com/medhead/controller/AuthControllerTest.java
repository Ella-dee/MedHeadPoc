package api.com.medhead.controller;

import api.com.medhead.payload.request.LoginRequest;
import api.com.medhead.payload.request.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AuthControllerTest extends ContainerBase{

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private SignupRequest signupRequest;
    private String username;
    private String password;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setup_test() {
        this.username = "test@test.com";
        this.password = "password";
        signupRequest = new SignupRequest();
        signupRequest.setUsername(username);
        signupRequest.setPassword(password);
    }

    @Test
    public void registerUser() throws Exception {
        // GIVEN

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post(AuthController.PATH+"/signup")
                        .content(objectMapper.writeValueAsString(signupRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void authenticateUser() throws Exception {
        // GIVEN

        mockMvc.perform(MockMvcRequestBuilders
                .post(AuthController.PATH+"/signup")
                .content(objectMapper.writeValueAsString(signupRequest)));

        // WHEN
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(signupRequest.getUsername());
        loginRequest.setPassword(signupRequest.getPassword());

        mockMvc.perform(MockMvcRequestBuilders
                        .post(AuthController.PATH+"/signin")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void authenticateUnknownUser() throws Exception {
        // GIVEN

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("unknown_user@test.com");
        loginRequest.setPassword("unknown_password");

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post(AuthController.PATH+"/signin")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                // THEN
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

}
