package mycode.doctor_appointment_api.app.working_hours.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Table(name = "workingHours")
@Entity(name = "WorkingHours")
public class WorkingHours {

    @Id
    @SequenceGenerator(
            name="working_hours_sequence",
            sequenceName = "working_hours_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "working_hours_sequence"
    )

    @Column(
            name = "id"
    )
    private int id;


    @Column(
            name = "day_of_week",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private DayOfWeek dayOfWeek;


    @Column(
            name = "start_time",
            nullable = false,
            columnDefinition = "TIME"
    )
    private LocalTime startTime;

    @Column(
            name = "end_time",
            nullable = false,
            columnDefinition = "TIME"
    )
    private LocalTime endTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @JsonBackReference
    private Doctor doctor;

}
