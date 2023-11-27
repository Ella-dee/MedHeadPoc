package api.com.medhead.repository;

import api.com.medhead.model.Patient;
import api.com.medhead.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByEmail(String email);
    Patient findByUserId(int userId);
    Boolean existsByEmail(String email);
}
