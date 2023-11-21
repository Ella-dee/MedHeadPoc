package api.com.medhead.model;

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

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", latitudeLeft=" + latitudeLeft +
                ", latitudeRight=" + latitudeRight +
                ", longitudeLeft=" + longitudeLeft +
                ", longitudeRight=" + longitudeRight +
                '}';
    }

    public Double getLatitudeLeft() {
        return latitudeLeft;
    }

    public void setLatitudeLeft(Double latitudeLeft) {
        this.latitudeLeft = latitudeLeft;
    }

    public Double getLatitudeRight() {
        return latitudeRight;
    }

    public void setLatitudeRight(Double latitudeRight) {
        this.latitudeRight = latitudeRight;
    }

    public Double getLongitudeLeft() {
        return longitudeLeft;
    }

    public void setLongitudeLeft(Double longitudeLeft) {
        this.longitudeLeft = longitudeLeft;
    }

    public Double getLongitudeRight() {
        return longitudeRight;
    }

    public void setLongitudeRight(Double longitudeRight) {
        this.longitudeRight = longitudeRight;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}

