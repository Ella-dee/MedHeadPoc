package api.com.medhead.service;

import api.com.medhead.model.Hospital;
import api.com.medhead.model.Location;
import api.com.medhead.payload.request.PatientSearchRequest;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.PointList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import static api.com.medhead.utils.Utils.convertSecondsToTime;
import static api.com.medhead.utils.Utils.generateLocation;

@Service
public class GraphhopperService implements CommandLineRunner {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    @Value("${osm.location}")
    private String osmLocation;
    private GraphHopper hopper;

    @Autowired
    HospitalService hospitalService;

    public String getOsmLocation(){
        return osmLocation;
    }

    public List<Hospital> getNearestHospital(PatientSearchRequest patientSearchRequest, int locationSearchPerimeterMeters){
        //GraphHopper hopper = createGraphHopperInstance(getOsmLocation());
        List<Hospital> hospitals = findHospitalsWithinPerimeter(locationSearchPerimeterMeters, patientSearchRequest.getLatitude(), patientSearchRequest.getLongitude(), patientSearchRequest.getSpecialityId());
        List<Hospital> nearestHospitals = routing(hopper, patientSearchRequest.getLatitude(), patientSearchRequest.getLongitude(), hospitals);
        // release resources to properly shutdown or start a new instance
        //hopper.close();
        return nearestHospitals;
    }

    static GraphHopper createGraphHopperInstance(String ghLoc) {
        GraphHopper hopper = new GraphHopper();
        hopper.setOSMFile(ghLoc);
        // specify where to store graphhopper files
        hopper.setGraphHopperLocation("target/routing-graph-cache");

        // see docs/core/profiles.md to learn more about profiles
        hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false));

        // this enables speed mode for the profile we called car
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));

        // now this can take minutes if it imports or a few seconds for loading of course this is dependent on the area you import
        hopper.importOrLoad();
        return hopper;
    }

    public List<Hospital> findHospitalsWithinPerimeter(int meters, Double latitude, Double longitude, int specialityId){
        Location myLoc = generateLocation(meters, latitude, longitude);
        return hospitalService.findHospitalWithinPerimeter(myLoc.getLatitudeLeft(), myLoc.getLatitudeRight(), myLoc.getLongitudeRight(), myLoc.getLongitudeLeft(), specialityId);
    }

    public List<Hospital> routing(GraphHopper hopper, Double latitude, Double longitude, List<Hospital> hospitals) {
       List<Hospital> hospitalListSortedByTime = new ArrayList<>();

        for (int i = 0; i <hospitals.size();++i) {

            // simple configuration of the request object
            GHRequest req = new GHRequest(latitude, longitude, hospitals.get(i).getLatitude(), hospitals.get(i).getLongitude()).
                    // note that we have to specify which profile we are using even when there is only one like here
                            setProfile("car").
                    // define the language for the turn instructions
                            setLocale(Locale.UK);
            GHResponse rsp = hopper.route(req);

            // handle errors
            if (rsp.hasErrors())
                System.out.println(rsp.getErrors().toString());

            // use the best path, see the GHResponse class for more possibilities.
            ResponsePath path = rsp.getBest();

            // points, distance in meters and time in millis of the full path
            PointList pointList = path.getPoints();
            double distance = path.getDistance();
            long timeInMs = path.getTime()/500;

            hospitals.get(i).setDistanceInTime(timeInMs);
            hospitals.get(i).setFormattedDistanceInKm(df.format(Double.valueOf(distance) / 1000) + " km");
            hospitals.get(i).setFormattedDistanceInTime(convertSecondsToTime(timeInMs));
            hospitalListSortedByTime.add(hospitals.get(i));
        }
        Collections.sort(hospitalListSortedByTime, Hospital.HospitalTimeComparator);
        return  hospitalListSortedByTime;
    }

    @Override
    public void run(String... args) throws Exception {
        hopper = new GraphHopper();
        hopper.setOSMFile(getOsmLocation());
        // specify where to store graphhopper files
        hopper.setGraphHopperLocation("target/routing-graph-cache");

        // see docs/core/profiles.md to learn more about profiles
        hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false));

        // this enables speed mode for the profile we called car
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));

        // now this can take minutes if it imports or a few seconds for loading of course this is dependent on the area you import
        hopper.importOrLoad();
    }
}
