package api.com.medhead.model;

import lombok.Data;

@Data
public class Location {
    private Double longitude;
    private Double latitude;
    private Double latitudeLeft;
    private Double latitudeRight;
    private Double longitudeLeft;
    private Double longitudeRight;

    public Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location() {
    }

}

