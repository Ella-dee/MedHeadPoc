package api.com.medhead.repository;


import api.com.medhead.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HospitalRepository  extends JpaRepository<Hospital, Integer> {
    @Query(value = "SELECT distinct * FROM hospital h left join speciality_hospitals sh on  hospitals_id=h.id and sh.hospitals_id=:specialityId WHERE (h.latitude between :latitudeRight and :latitudeLeft) AND (h.longitude between :longitudeRight and :longitudeLeft) LIMIT 5", nativeQuery = true)
    List<Hospital> findHospitalWithinPerimeterWithSpeciality(
            @Param("latitudeLeft") Double latitudeLeft,
            @Param("latitudeRight") Double latitudeRight,
            @Param("longitudeRight") Double longitudeRight,
            @Param("longitudeLeft") Double longitudeLeft,
            @Param("specialityId") int specialityId);
}

