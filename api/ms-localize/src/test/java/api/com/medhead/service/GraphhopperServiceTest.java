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
    final int locationSearchPerimeter = 9000;

    private Hospital hospital0 = new Hospital(224, "Bedford Hospital South Wing","South Wing Kempston Road", "Bedford", "MK42 9DJ",  -0.47248, 52.12826,"1234355122", "http://www.bedfordhospital.nhs.uk/index.asp", "pals@bedfordhospital.nhs.uk" ,1);
    private Hospital hospital1 = new Hospital(539, "Calderdale Royal Hospital", "Salterhebble", "Halifax", "HX3 0PW",  -1.85749, 53.70482, "01422 357171", "http://www.cht.nhs.uk", "", 1);
    private Hospital hospital2 = new Hospital(227, "St Monicas Hospital",  "Long Street Easingwold", "York", "YO61 3JD",-1.18931, 54.1184,  "01904 724825",  "https://www.yorkhospitals.nhs.uk/our-hospitals/st-monicas-easingwold/", "", 2);
    private Hospital hospital3 = new Hospital(1154, "Bicester Community Hospital", "Piggy Lane", "Bicester", "OX26 6HT",  -1.15806, 51.89619, "", "", "", 4);
    private List<Hospital> hospitals= new ArrayList<>();
    private List<Hospital> hospitalsFound= new ArrayList<>();
/*
    @BeforeEach
    void setup_Test() {
        hospitals.add(hospital0);
        hospitals.add(hospital1);
        hospitals.add(hospital2);
        hospitals.add(hospital3);

        hospitalsFound.add(hospital0);hospitalsFound.add(hospital2);

        hopper.setOSMFile("src/main/resources/england-latest.osm.pbf");
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
    void routing() {Location loc = generateLocation(9000, 51.488954, -0.163229);
        List<Hospital> hospitalList = graphhopperService.routing(hopper, 51.488954, -0.163229, hospitalsFound);
        assertEquals(2, hospitalList.size());
    }

    @Test
    void getNearestHospital() {
        when(graphhopperService.findHospitalsWithinPerimeter(9000, 51.488954, -0.163229,16)).thenReturn(hospitalsFound);
        List<Hospital> hospitals = graphhopperService.findHospitalsWithinPerimeter(9000, 51.488954, -0.163229,16);
        assertEquals(2, hospitals.size());
    }
*/


}