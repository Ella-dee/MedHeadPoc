package api.com.medhead.service;

import api.com.medhead.model.Speciality;
import api.com.medhead.model.SpecialityGroup;
import api.com.medhead.repository.SpecialityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialityServiceTest {

    @InjectMocks
    private SpecialityService specialityService;

    @Mock
    private SpecialityRepository specialityRepositoryMock;

    private String speciality_name_test = "Chirurgie Cardiaque";
    private Speciality speciality0 = new Speciality();
    private Speciality speciality1 = new Speciality();
    private Speciality speciality2 = new Speciality();
    private Speciality speciality3 = new Speciality();
    private Speciality speciality4 = new Speciality();
    private List< Speciality> specialities = new ArrayList<Speciality>();
    private List< Speciality> specialitiesWSpeGroup = new ArrayList<Speciality>();

    private SpecialityGroup specialityGroup = new SpecialityGroup("Anesthésie");

    @BeforeEach
    public void setup_Test() {

        speciality0.setId(3);
        speciality0.setName(speciality_name_test);
        speciality1.setId(4);
        speciality1.setName("Pédiatrie");
        speciality2.setId(5);
        speciality2.setName("Anesthésie");
        speciality3.setId(6);
        speciality3.setName("Médecine d'urgence");
        speciality4.setId(7);
        speciality4.setName("Soins intensifs");
        specialities.add(speciality0);
        specialities.add(speciality1);
        specialities.add(speciality2);
        specialities.add(speciality3);
        specialities.add(speciality3);

        specialityGroup.setId(13);
        speciality2.setSpecialityGroup(specialityGroup);
        speciality4.setSpecialityGroup(specialityGroup);
        specialitiesWSpeGroup.add(speciality2);
        specialitiesWSpeGroup.add(speciality4);
        specialityGroup.setSpecialities(specialitiesWSpeGroup);
    }

    @Test
    void findOneById() {
        when(specialityService.findOneById(speciality0.getId())).thenReturn(speciality0);
        Speciality speciality = specialityService.findOneById(speciality0.getId());
        assertEquals(speciality.getName(), speciality_name_test);
    }

    @Test
    void getSpecialityByName() {
        when(specialityService.getSpecialityByName(speciality0.getName())).thenReturn(speciality0);
        Speciality speciality = specialityService.getSpecialityByName(speciality0.getName());
        assertEquals(speciality.getName(), speciality_name_test);
    }

    @Test
    void getSpecialities() {
        when(specialityService.getSpecialities()).thenReturn(specialities);
        List<Speciality> specialities = specialityService.getSpecialities();
        assertEquals(5, specialities.size());
        assertEquals(specialities.get(0).getName(), speciality_name_test);
    }

    @Test
    void save() {
        when(specialityService.save(speciality0)).thenReturn(speciality0);
        Speciality speciality = specialityService.save(speciality0);
        assertEquals(3, speciality.getId());
    }

    @Test
    void getSpecialitiesBySpecialityGroupId() {
        when(specialityService.getSpecialitiesBySpecialityGroupId(specialityGroup.getId())).thenReturn(specialitiesWSpeGroup);
        List<Speciality> specialities = specialityService.getSpecialitiesBySpecialityGroupId(specialityGroup.getId());
        assertEquals(2, specialities.size());
        assertEquals("Soins intensifs", specialities.get(1).getName());
    }

    @Test
    void getSpecialitiesBySpecialityGroupName() {
        when(specialityService.getSpecialitiesBySpecialityGroupName(specialityGroup.getName())).thenReturn(specialitiesWSpeGroup);
        List<Speciality> specialities = specialityService.getSpecialitiesBySpecialityGroupName(specialityGroup.getName());
        assertEquals(2, specialities.size());
        assertEquals("Anesthésie", specialities.get(0).getName());
    }

}