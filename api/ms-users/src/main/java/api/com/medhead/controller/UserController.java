package api.com.medhead.controller;

import api.com.medhead.model.Patient;
import api.com.medhead.model.User;
import api.com.medhead.payload.request.RegisterInfoRequest;
import api.com.medhead.payload.response.MessageResponse;
import api.com.medhead.service.PatientService;
import api.com.medhead.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping(UserController.PATH)
public class UserController {
    public static final String PATH = "/api/users";
    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/all")
    public List<User> getUsersList() {
        return userService.getUsersList();
    }

    @GetMapping("/{userId}")
    public Patient getPatient(@PathVariable int userId) {
        return patientService.getPatient(userId);
    }
    @GetMapping("/email/{email}")
    public Optional<User> getRegisteredUser(@PathVariable String email) {
        return userService.getRegisteredUser(email);
    }

    @PostMapping("/patient")
    public ResponseEntity<?> registerPatientInfo(@Valid @RequestBody RegisterInfoRequest registerInfoRequest) {
        Patient p = new Patient();
        try{
            p = patientService.registerPatientInfo(registerInfoRequest);
        }catch (Exception e){
            String msg =e.getMessage();
            Throwable t = e.getCause();
            if(t.getCause() != null) {
                msg = t.getCause().toString();
            }
            return ResponseEntity.badRequest().body(new MessageResponse(msg));
        }
        if (p.getLatitude()==0){
            return ResponseEntity.badRequest().body(new MessageResponse("The address could not be geolocalized"));
        }
        return ResponseEntity.ok(new MessageResponse("Infos registered successfully!"));
    }

}
