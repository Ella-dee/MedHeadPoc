package api.com.medhead.service;

import api.com.medhead.model.ERole;
import api.com.medhead.model.Patient;
import api.com.medhead.model.Role;
import api.com.medhead.model.User;
import api.com.medhead.payload.request.SignupRequest;
import api.com.medhead.repository.PatientRepository;
import api.com.medhead.repository.RoleRepository;
import api.com.medhead.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;
    @Mock
    RoleService roleService;
    @Mock
    UserService userService;
    @Mock
    PatientService patientService;
    @Mock
    PasswordEncoder passwordEncoder;

    private SignupRequest signupRequest = new SignupRequest();
    private User user = new User();
    private Patient patient = new Patient();
    private Role role = new Role(ERole.ROLE_USER);

    @BeforeEach
    void setup_test(){
        signupRequest.setUsername("besoindevacaces@test.com");
        signupRequest.setPassword("password");

        user.setEmail(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());

        patient.setEmail(user.getEmail());
    }

    @Test
    void signupUser() {
        when(userService.save(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("hashedPasswordWithLotsOfEncoding");
        when(roleService.findByName(any())).thenReturn(Optional.ofNullable(role));
        when(patientService.save(any())).thenReturn(patient);
        authService.signupUser(signupRequest);
    }
}