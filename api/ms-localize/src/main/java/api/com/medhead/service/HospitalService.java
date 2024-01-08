package api.com.medhead.service;

import api.com.medhead.model.Hospital;
import api.com.medhead.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {
    @Autowired
    HospitalRepository hospitalRepository;

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public List<Hospital> findHospitalWithinPerimeter(
            Double latitudeLeft, Double latitudeRight,
            Double longitudeRight, Double longitudeLeft, int specialityId) {
        return hospitalRepository.findHospitalWithinPerimeterWithSpeciality(latitudeLeft, latitudeRight, longitudeRight, longitudeLeft, specialityId);
    }

    public Optional<Hospital> bookBed(int hospitalId){
        Optional<Hospital> h = hospitalRepository.findById(hospitalId);
        if(h.isPresent()){
            Hospital bookedH = h.get();
            bookedH.setAvailableBeds(bookedH.getAvailableBeds()-1);
            hospitalRepository.save(bookedH);
        }
        return h;
    }
}
