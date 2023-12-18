package api.com.medhead.service;

import api.com.medhead.model.Hospital;
import api.com.medhead.model.Speciality;
import api.com.medhead.model.SpecialityGroup;
import api.com.medhead.repository.HospitalRepository;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class HospitalServiceTest {

    @InjectMocks
    private HospitalService hospitalService;

    @Mock
    private HospitalRepository hospitalRepositoryMock;
    
    
    private Hospital hospital0 = new Hospital(224, "Bedford Hospital South Wing","South Wing Kempston Road", "Bedford", "MK42 9DJ",  -0.47248, 52.12826,"1234355122", "http://www.bedfordhospital.nhs.uk/index.asp", "pals@bedfordhospital.nhs.uk" ,1);
    private Hospital hospital1 = new Hospital(539, "Calderdale Royal Hospital", "Salterhebble", "Halifax", "HX3 0PW",  -1.85749, 53.70482, "01422 357171", "http://www.cht.nhs.uk", "", 1);
    private Hospital hospital2 = new Hospital(227, "St Monicas Hospital",  "Long Street Easingwold", "York", "YO61 3JD",-1.18931, 54.1184,  "01904 724825",  "https://www.yorkhospitals.nhs.uk/our-hospitals/st-monicas-easingwold/", "", 2);
    private Hospital hospital3 = new Hospital(1154, "Bicester Community Hospital", "Piggy Lane", "Bicester", "OX26 6HT",  -1.15806, 51.89619, "", "", "", 4);
    private List<Hospital> hospitals= new ArrayList<>();
    private List<Hospital> hospitalsFound= new ArrayList<>();

    private Speciality speciality0 = new Speciality(12, "Anesthésie");
    private Speciality speciality1 = new Speciality(16, "Soins Intensifs");
    private Speciality speciality2 = new Speciality(17, "Service de santé communautaire dentaire");
    private Speciality speciality3 = new Speciality(19, "Santé publique dentaire");
    private Speciality speciality4 = new Speciality(2, "Service de santé communautaire médical");
    private List<Speciality> specialities0 = new ArrayList<>();
    private List<Speciality> specialities1 = new ArrayList<>();
    private List<Speciality> specialities2 = new ArrayList<>();
    private List<Speciality> specialities3 = new ArrayList<>();

    private SpecialityGroup specialityGroup0 = new SpecialityGroup(4, "Groupe dentaire");
    private SpecialityGroup specialityGroup1 = new SpecialityGroup(9, "Anesthésie");




    @BeforeEach
    void setup_Test(){
        speciality0.setSpecialityGroup(specialityGroup0);
        speciality1.setSpecialityGroup(specialityGroup0);
        speciality2.setSpecialityGroup(specialityGroup1);
        speciality3.setSpecialityGroup(specialityGroup1);
        speciality4.setSpecialityGroup(specialityGroup1);

        specialities0.add(speciality1);specialities0.add(speciality2);
        specialities1.add(speciality0);specialities1.add(speciality4);
        specialities2.add(speciality3);specialities2.add(speciality1);
        specialities3.add(speciality2);specialities3.add(speciality0);specialities3.add(speciality3);

        hospital0.setSpecialities(specialities0);
        hospital1.setSpecialities(specialities1);
        hospital2.setSpecialities(specialities2);
        hospital3.setSpecialities(specialities3);

        hospitals.add(hospital0);
        hospitals.add(hospital1);
        hospitals.add(hospital2);
        hospitals.add(hospital3);

        hospitalsFound.add(hospital0);hospitalsFound.add(hospital2);
    }
    
    @Test
    void getAllHospitals() {
        when(hospitalService.getAllHospitals()).thenReturn(hospitals);
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        assertEquals(4, hospitals.size());
        assertEquals("Calderdale Royal Hospital", hospitals.get(1).getName());
    }

    @Test
    void findHospitalWithinPerimeter() {
        when(hospitalService.findHospitalWithinPerimeter(5.4,5.6, -1.2, -1.1, 16 )).thenReturn(hospitalsFound);
        List<Hospital>hospitals = hospitalService.findHospitalWithinPerimeter(5.4,5.6, -1.2, -1.1, 16);
        assertEquals(2, hospitals.size());
    }
}