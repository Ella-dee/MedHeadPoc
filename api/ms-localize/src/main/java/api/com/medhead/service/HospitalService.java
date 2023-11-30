package api.com.medhead.service;

import api.com.medhead.model.Hospital;
import api.com.medhead.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {
    @Autowired
    HospitalRepository hospitalRepository;

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public List<Hospital> findHospitalWithinPerimeter(
            Double latitudeLeft, Double latitudeRight,
            Double longitudeRight, Double longitudeLeft) {
        return hospitalRepository.findHospitalWithinPerimeter(latitudeLeft, latitudeRight, longitudeRight, longitudeLeft);
    }
}
