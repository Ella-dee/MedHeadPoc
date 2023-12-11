package api.com.medhead.payload.request;

import jakarta.validation.constraints.*;

public class PatientSearchRequest {
    @NotNull
    @Min(value = 1, message = "No specialty given")
    private int specialityId;
    @NotNull
    private double longitude = 0;
    @NotNull
    private double latitude = 0;


    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}
