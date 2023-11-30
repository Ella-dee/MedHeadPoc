package api.com.medhead.controller;

import api.com.medhead.model.Hospital;
import api.com.medhead.model.Location;
import api.com.medhead.payload.request.PatientAddressRequest;
import api.com.medhead.repository.HospitalRepository;
import api.com.medhead.service.GraphhopperService;
import api.com.medhead.service.HospitalService;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.PointList;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.json.JSONException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import static api.com.medhead.utils.Utils.convertSecondsToTime;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

@RestController
@RequestMapping("/api/hospitals")
public class NhsHospitalController {
    @Autowired
    HospitalService hospitalService;
    @Autowired
    GraphhopperService graphhopperService;

    @Value("${location.search.perimeter.meters}")
    private int locationSearchPerimeterMeters;

    @GetMapping("all")
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }
    //test url http://localhost:9005/api/hospitals/getNearest/51.510067/-0.133869

    @PostMapping("getNearest")
    public List<Hospital> getNearestHospital(@Valid @RequestBody PatientAddressRequest patientAddressRequest){
        return graphhopperService.getNearestHospital(patientAddressRequest, locationSearchPerimeterMeters);
    }



}
