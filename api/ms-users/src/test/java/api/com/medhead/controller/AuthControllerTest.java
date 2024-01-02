package api.com.medhead.controller;

import api.com.medhead.model.ERole;
import api.com.medhead.model.Role;
import api.com.medhead.model.User;
import api.com.medhead.payload.request.LoginRequest;
import api.com.medhead.payload.request.SignupRequest;
import api.com.medhead.repository.UserRepository;
import api.com.medhead.security.UserDetailsImpl;
import api.com.medhead.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Autowired
    AuthController authController;

    @MockBean
    UserRepository userRepository;
    @MockBean
    LoginRequest loginRequest;
    @MockBean
    SignupRequest signupRequest;

    private User u1 = new User(2,  "password", "bluebox@badwolfbay.com");
    private User u2 = new User(4, "password","test@test.com");
    private Role role = new Role(ERole.ROLE_USER);
    private Set<Role> roles  = new HashSet<>();
    private List<User> userList=new ArrayList<>();
    private String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5keS5sbG95ZEB0ZXN0LmNvbSIsImlhdCI6MTcwMjk3NTc2MywiZXhwIjoxNzAzMDYyMTYzfQ.XO3rBnzVC-EqtvmiH9VYca84roDjrcE8dlkJ8qofZik";

    @BeforeEach
    void setup_test(){
      loginRequest.setPassword("password");
      loginRequest.setUsername("bluebox@badwolfbay.com");
      signupRequest.setPassword("password");
      signupRequest.setUsername("test@test.com");
      roles.add(role);
      u1.setRoles(roles);
    }

    @Test
   // @WithMockUser(username = "bluebox@badwolfbay.com",  password = "password", roles = {"ROLE_USER"})
    void authenticateUser() {

       // authController.authenticateUser(loginRequest);
    }

    @Test
    void registerUser() {

       // authController.registerUser(signupRequest);
    }
}