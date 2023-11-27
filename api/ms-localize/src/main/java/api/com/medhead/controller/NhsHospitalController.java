package api.com.medhead.controller;

import api.com.medhead.model.Hospital;
import api.com.medhead.model.Location;
import api.com.medhead.repository.HospitalRepository;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.PointList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static api.com.medhead.utils.Utils.convertSecondsToTime;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

@RestController
@RequestMapping("/api/hospitals")
public class NhsHospitalController {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Autowired
    HospitalRepository hospitalRepository;

    @Value("${location.search.perimeter.meters}")
    private int locationSearchPerimeterMeters;

    @GetMapping("all")
    public List<Hospital> getAllHospitals() throws IOException, InterruptedException, JSONException {
        return hospitalRepository.findAll();
    }
    //test url http://localhost:9005/api/hospitals/getNearest/51.510067/-0.133869

    @GetMapping("getNearest/{latitude}/{longitude}")
    public List<Hospital> getNearestHospital(@PathVariable Double latitude, @PathVariable Double longitude){
        GraphHopper hopper = createGraphHopperInstance("ms-localize/src/main/resources/england-latest.osm.pbf");
        List<Hospital> nearestHospitals = routing(hopper, latitude, longitude);
        // release resources to properly shutdown or start a new instance
        hopper.close();
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

    private Location generateLocation(int meters, Double latitude, Double longitude){
        Location myLoc = new Location();
        myLoc.setLatitude(latitude);
        myLoc.setLongitude(longitude);

        //Earth’s radius, sphere
        Double earthRadius=6378137.00;
        //Coordinate offsets in radians
        Double dLat = meters/earthRadius;
        Double dLon = meters/(earthRadius*cos(PI*myLoc.getLatitude()/180));
        //OffsetPosition, decimal degrees
        myLoc.setLatitudeLeft(trimDouble(myLoc.getLatitude() + dLat * 180/PI));
        myLoc.setLongitudeLeft(trimDouble(myLoc.getLongitude() + dLon * 180/PI));
        myLoc.setLatitudeRight(trimDouble(myLoc.getLatitude() + -dLat * 180/PI));
        myLoc.setLongitudeRight(trimDouble(myLoc.getLongitude() + -dLon * 180/PI));
        return myLoc;
    }

    private Double trimDouble(Double longLat){
        String longLatTrimmed = String.valueOf(longLat);
        if(longLatTrimmed.length()>=8){
            longLatTrimmed = longLatTrimmed.substring(0, 9);
        }
        return Double.valueOf(longLatTrimmed);
    }
    public List<Hospital> routing(GraphHopper hopper, Double latitude, Double longitude) {
        Location myLoc = generateLocation(locationSearchPerimeterMeters, latitude, longitude);
        List<Hospital> hospitals = hospitalRepository.findHospitalWithinPerimeter(myLoc.getLatitudeLeft(), myLoc.getLatitudeRight(), myLoc.getLongitudeRight(), myLoc.getLongitudeLeft());
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
                throw new RuntimeException(rsp.getErrors().toString());

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
}
