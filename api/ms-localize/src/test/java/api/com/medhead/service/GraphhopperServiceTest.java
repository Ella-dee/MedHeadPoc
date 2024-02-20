package api.com.medhead.service;

import api.com.medhead.model.Hospital;
import api.com.medhead.model.Location;
import api.com.medhead.repository.HospitalRepository;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

import static api.com.medhead.utils.Utils.generateLocation;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GraphhopperServiceTest {

    @InjectMocks
    private GraphhopperService graphhopperService;
    @Mock
    private HospitalService hospitalService;
    @Mock
    private HospitalRepository hospitalRepository;

    GraphHopper hopper = new GraphHopper();

    private Hospital hospital1 = new Hospital(81, "Hendon Hospital", "Hendon Hospital 46-50 Sunny Gardens Road Hendon", "London", "NW4 1RP", -0.22458,51.59371, "", "", "", 1);
    private Hospital hospital2 = new Hospital(79, "The Clementine Churchill Hospital",  "Sudbury Hill", "Harrow", "SO22 5HA",-0.33269, 51.56496,  "HA1 3RX",  "", "", 2);
    private Hospital hospital3 = new Hospital(86, "The London Independent Hospital","1 Beaumont Square Stepney Green",  "London", "E1 4NL",-0.04697, 51.5204, "","","", 1);
    private Hospital hospital4 = new Hospital(85, "The Kings Oak Hospital", "The Ridgeway", "Enfield",  "EN2 8SD",  -0.10555, 51.6679,  "", "","",4);

    private List<Hospital> hospitals= new ArrayList<>();
    private List<Hospital> hospitalsFound= new ArrayList<>();

    @BeforeEach
    void setup_Test() {

        hospitals.add(hospital1);
        hospitals.add(hospital2);
        hospitals.add(hospital3);
        hospitals.add(hospital4);

        hospitalsFound.add(hospital3);hospitalsFound.add(hospital4);
        hopper.setOSMFile("src/main/resources/greater-london-latest.osm.pbf");
        hopper.setGraphHopperLocation("target/routing-graph-cache");
        hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false));
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));
        hopper.importOrLoad();

    }

    @Test
    void findHospitalsWithinPerimeter(){
        when(graphhopperService.findHospitalsWithinPerimeter(9000, 51.488954, -0.163229,16)).thenReturn(hospitalsFound);
        List<Hospital> hospitals = graphhopperService.findHospitalsWithinPerimeter(9000, 51.488954, -0.163229,16);
        assertEquals(2, hospitals.size());
    }

    @Test
    void routing() {
        assertEquals(4, hospitals.size());
        List<Hospital> hospitalList = graphhopperService.routing(hopper, 51.488954, -0.163229, hospitals);
        Hospital h1 = hospitalList.get(0);
        Hospital h2 = hospitalList.get(1);
        Hospital h3 = hospitalList.get(2);
        Hospital h4 = hospitalList.get(3);
        assertTrue(h1.getDistanceInTime() < h2.getDistanceInTime());
        assertTrue(h2.getDistanceInTime() < h3.getDistanceInTime());
        assertTrue(h3.getDistanceInTime() < h4.getDistanceInTime());
    }

}