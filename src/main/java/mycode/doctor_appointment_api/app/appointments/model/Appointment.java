package mycode.doctor_appointment_api.app.appointments.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.patient.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Table(name = "appointment")
@Entity(name = "Appointment")
public class Appointment {

    @Id
    @SequenceGenerator(
            name="appointment_sequence",
            sequenceName = "appointment_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "appointment_sequence"
    )

    @Column(
            name = "id"
    )
    private int id;


    @Column(
            name = "start",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime start;

    @Column(
            name = "end",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @JsonBackReference
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @JsonBackReference
    private Patient patient;

}
