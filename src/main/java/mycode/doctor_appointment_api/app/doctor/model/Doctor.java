package mycode.doctor_appointment_api.app.doctor.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.working_hours.model.WorkingHours;

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
@Builder
@Table(name = "doctor")
@Entity(name = "Doctor")
public class Doctor {


    @Id
    @SequenceGenerator(
            name="doctor_sequence",
            sequenceName = "doctor_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "doctor_sequence"
    )

    @Column(
            name = "id"
    )
    private int id;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
            name = "full_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String fullName;

    @Column(
            name = "phone",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String phone;

    @Column(
            name = "specialization",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String specialization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    @JsonBackReference
    private Clinic clinic;

    @OneToMany(mappedBy ="doctor",fetch = FetchType.LAZY,cascade = CascadeType.ALL ,orphanRemoval = true)
    @Builder.Default
            @ToString.Exclude
    @JsonManagedReference
    private Set<Appointment> appointments = new HashSet<>();


    @OneToMany(mappedBy ="doctor",fetch = FetchType.LAZY,cascade = CascadeType.ALL ,orphanRemoval = true)
    @Builder.Default
        @ToString.Exclude
    @JsonManagedReference
    private Set<WorkingHours> workingHours = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
