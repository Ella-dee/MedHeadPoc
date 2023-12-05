package api.com.medhead.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="specialityGroup")
public class SpecialityGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany (mappedBy = "specialityGroup", cascade = CascadeType.REMOVE)
    private List<Speciality> specialities = new ArrayList<>();

    public SpecialityGroup(String name) {
        this.name = name;
    }
    public SpecialityGroup() {
    }

    @Override
    public String toString() {
        return "SpecialityGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
