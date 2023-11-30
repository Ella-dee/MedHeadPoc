package api.com.medhead.service;

import api.com.medhead.model.ERole;
import api.com.medhead.model.Role;
import api.com.medhead.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(ERole name){
        return roleRepository.findByName(name);
    }
}
