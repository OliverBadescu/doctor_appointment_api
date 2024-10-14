package mycode.doctor_appointment_api.app.clinic.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@Table(name = "clinic")
@Entity(name = "Clinic")
public class Clinic {

    @Id
    @SequenceGenerator(
            name="clinic_sequence",
            sequenceName = "clinic_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "clinic_sequence"
    )

    @Column(
            name = "id"
    )
    private int id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;


    @Column(
            name = "address",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String address;


    @OneToMany(mappedBy ="clinic",fetch = FetchType.LAZY,cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
        @ToString.Exclude
    @JsonManagedReference
    private Set<Doctor> doctors = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clinic clinic = (Clinic) o;
        return id == clinic.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
