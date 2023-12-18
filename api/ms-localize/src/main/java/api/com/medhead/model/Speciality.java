package api.com.medhead.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
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
    @JsonIgnore
    private SpecialityGroup specialityGroup;

    public Speciality(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Speciality() {
    }
}
