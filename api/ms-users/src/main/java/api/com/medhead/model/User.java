package api.com.medhead.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "ssnumber"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "postcode")
    private String postCode;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column (name= "birthdate")
    private LocalDate birthdate;
    @Column (name= "ssnumber")
    private String socialSecurityNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(int id, String firstName, String lastName, String password, String address, String city, String postCode, double longitude, double latitude, String phone, String email, LocalDate birthdate, String socialSecurityNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
        this.city = city;
        this.postCode = postCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phone = phone;
        this.email = email;
        this.birthdate = birthdate;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public User() {
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
