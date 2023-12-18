package api.com.medhead.utils;

import api.com.medhead.model.Location;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

public class Utils {
    public static String convertSecondsToTime(Long seconds){
        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;
        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }

    public static Double trimDouble(Double longLat){
        String longLatTrimmed = String.valueOf(longLat);
        if(longLatTrimmed.length()>=8){
            longLatTrimmed = longLatTrimmed.substring(0, 9);
        }
        return Double.valueOf(longLatTrimmed);
    }

    public static Location generateLocation(int meters, Double latitude, Double longitude){
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


}
