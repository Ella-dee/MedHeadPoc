package api.com.medhead.service;

import api.com.medhead.model.Patient;
import api.com.medhead.model.User;
import api.com.medhead.payload.request.RegisterInfoRequest;
import api.com.medhead.repository.PatientRepository;
import api.com.medhead.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    public Optional<User> getRegisteredUser(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
