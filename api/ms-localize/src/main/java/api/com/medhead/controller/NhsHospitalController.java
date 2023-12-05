package api.com.medhead.controller;

import api.com.medhead.model.Hospital;
import api.com.medhead.model.Speciality;
import api.com.medhead.model.SpecialityGroup;
import api.com.medhead.payload.request.PatientAddressRequest;
import api.com.medhead.payload.request.SpecialityGroupRequest;
import api.com.medhead.service.GraphhopperService;
import api.com.medhead.service.HospitalService;
import api.com.medhead.service.SpecialityGroupService;
import api.com.medhead.service.SpecialityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/hospitals")
public class NhsHospitalController {
    @Autowired
    HospitalService hospitalService;
    @Autowired
    SpecialityService specialityService;
    @Autowired
    GraphhopperService graphhopperService;
    @Autowired
    SpecialityGroupService specialityGroupService;

    @Value("${location.search.perimeter.meters}")
    private int locationSearchPerimeterMeters;
    private int randomgenerator(int min, int max){
        Random random = new Random();
        return random.nextInt((max+1) - min) +min;
    }

    @GetMapping("all")
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("specialities")
    public List<Speciality> getAllSpecialities() {
        return specialityService.getSpecialities();
    }

    @PostMapping("specialities")
    public List<Speciality> getSpecialitiesBySpecialityGroupName(@Valid @RequestBody SpecialityGroupRequest specialityGroupRequest) {
        return specialityService.getSpecialitiesBySpecialityGroupName(specialityGroupRequest.getName());
    }

    @GetMapping("speciality-groups")
    public List<SpecialityGroup> getAllSpecialityGroups() {
        return specialityGroupService.getAllSpecialityGroups();
    }

    //test url http://localhost:9005/api/hospitals/getNearest/51.510067/-0.133869

    @PostMapping("getNearest")
    public List<Hospital> getNearestHospital(@Valid @RequestBody PatientAddressRequest patientAddressRequest){
        return graphhopperService.getNearestHospital(patientAddressRequest, locationSearchPerimeterMeters);
    }



}
