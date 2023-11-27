package api.com.medhead.controller;

import api.com.medhead.model.Patient;
import api.com.medhead.model.User;
import api.com.medhead.repository.PatientRepository;
import api.com.medhead.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/all")
    public List<User> getUsersList(){
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public Patient getUser(@PathVariable int userId){
        return patientRepository.findByUserId(userId);
    }
}
