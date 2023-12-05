package api.com.medhead.repository;


import api.com.medhead.model.SpecialityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityGroupRepository extends JpaRepository<SpecialityGroup, Long> {
   SpecialityGroup findByName(String name);
}
