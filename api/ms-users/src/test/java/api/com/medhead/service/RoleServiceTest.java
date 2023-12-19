package api.com.medhead.service;

import api.com.medhead.model.ERole;
import api.com.medhead.model.Role;
import api.com.medhead.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    RoleService roleService;

    @Mock
    RoleRepository roleRepository;

    Role r = new Role(ERole.ROLE_ADMIN);

    @Test
    void findByName() {
        when(roleRepository.findByName(any())).thenReturn(Optional.ofNullable(r));
        Optional<Role> role = roleService.findByName(ERole.ROLE_ADMIN);
        assertEquals(r.getName(), role.get().getName());
    }
}