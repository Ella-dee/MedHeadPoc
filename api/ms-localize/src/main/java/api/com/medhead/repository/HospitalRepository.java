package api.com.medhead.repository;


import api.com.medhead.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HospitalRepository  extends JpaRepository<Hospital, Integer> {
    @Query(value = "SELECT distinct * FROM hospital h WHERE (h.latitude between :latitudeRight and :latitudeLeft) AND (h.longitude between :longitudeRight and :longitudeLeft)", nativeQuery = true)
    List<Hospital> findHospitalWithinPerimeter(
            @Param("latitudeLeft") Double latitudeLeft,
            @Param("latitudeRight") Double latitudeRight,
            @Param("longitudeRight") Double longitudeRight,
            @Param("longitudeLeft") Double longitudeLeft);
}

