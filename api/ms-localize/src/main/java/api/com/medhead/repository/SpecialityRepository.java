package api.com.medhead.repository;


import api.com.medhead.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
  Speciality findByName(String name);
    List<Speciality> findBySpecialityGroupId(int groupId);
    List<Speciality> findBySpecialityGroupName(String name);
    Speciality findOneById(int id);
}
