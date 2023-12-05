package api.com.medhead.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "speciality")
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String name;
    @ManyToMany
    @JsonIgnore
    private List<Hospital> hospitals;
    @ManyToOne //plusieurs specialités pour un seul groupe
    @JoinColumn(name = "specialityGroup_fk")
    private SpecialityGroup specialityGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public SpecialityGroup getSpecialityGroup() {
        return specialityGroup;
    }

    public void setSpecialityGroup(SpecialityGroup specialityGroup) {
        this.specialityGroup = specialityGroup;
    }

    public Speciality(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Speciality{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public Speciality() {
    }
}
