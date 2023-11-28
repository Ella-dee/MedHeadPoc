package api.com.medhead.controller;

import api.com.medhead.model.Patient;
import api.com.medhead.model.User;
import api.com.medhead.payload.request.RegisterInfoRequest;
import api.com.medhead.payload.request.SignupRequest;
import api.com.medhead.payload.response.MessageResponse;
import api.com.medhead.repository.PatientRepository;
import api.com.medhead.repository.UserRepository;
import jakarta.validation.Valid;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;

    private final String GEO_API_KEY = "2ed374a9-4d97-4d99-b892-a8063032d84b";
    private final String GEO_API_URL = "https://graphhopper.com/api/1/geocode?q=";

    @GetMapping("/all")
    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public Patient getPatient(@PathVariable int userId) {
        return patientRepository.findByUserId(userId);
    }
    @GetMapping("/email/{email}")
    public Optional<User> getRegisteredUser(@PathVariable String email) {
        return userRepository.findByEmail(email);
    }

    @PostMapping("/patient")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody RegisterInfoRequest registerInfoRequest) throws IOException, InterruptedException, JSONException {
        Patient p = patientRepository.findByEmail(registerInfoRequest.getEmail());
        p.setFirstName(registerInfoRequest.getFirstName());
        p.setLastName(registerInfoRequest.getLastName());
        p.setAddress(registerInfoRequest.getAddress());
        p.setCity(registerInfoRequest.getCity());
        p.setPhone(registerInfoRequest.getPhone());
        p.setPostCode(registerInfoRequest.getPostCode());

        //convert String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        String date = registerInfoRequest.getBirthdate();
        LocalDate birthdate = LocalDate.parse(date, formatter);
        p.setBirthdate(birthdate);
        p.setSocialSecurityNumber(registerInfoRequest.getNhsNumber());

        String patientAddress = URLEncoder.encode(p.getAddress()+" "+p.getCity()+" "+p.getPostCode());
        String urlForGeolocalization = GEO_API_URL+patientAddress+"&key="+GEO_API_KEY;
        try {
            JSONObject objectForCoordinates = getRouteObject(urlForGeolocalization);
            JSONArray hits = objectForCoordinates.getJSONArray("hits");
            for (int i = 0; i < hits.length(); i++) {
                JSONObject object = hits.getJSONObject(i);

                if (object.get("postcode").equals(p.getPostCode()) && object.get("city").equals(p.getCity())) {
                    JSONObject point = (JSONObject) object.get("point");
                    String lat = point.get("lat").toString();
                    String lon = point.get("lng").toString();
                    String patientLat, patientLon;
                    if (lon.length() > 9) {
                        lon = lon.substring(0, 9);
                    }
                    if (lat.length() > 9) {
                        lat = lat.substring(0, 9);
                    }
                    p.setLatitude(Double.valueOf(lat));
                    p.setLongitude(Double.valueOf(lon));
                    patientRepository.save(p);
                }
            }
        }catch (Exception e){
            return ResponseEntity.ok(new MessageResponse("The address could not be geolocalized"));
        }
        return ResponseEntity.ok(new MessageResponse("Infos registered successfully!"));
    }

    private JSONObject getRouteObject(String url) throws IOException, JSONException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;

        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject object = new JSONObject(response.body());
        return object;
    }
}
