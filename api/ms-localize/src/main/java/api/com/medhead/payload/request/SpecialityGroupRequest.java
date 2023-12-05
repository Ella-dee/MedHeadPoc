package api.com.medhead.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class SpecialityGroupRequest {
    @NotEmpty
    @NotBlank
    private String name;
    @NotEmpty
    @NotBlank
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
