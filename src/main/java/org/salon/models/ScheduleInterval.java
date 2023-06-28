package org.salon.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule_interval")
public class ScheduleInterval {
    public ScheduleInterval(User employee, LocalDateTime startDateTime, LocalDateTime finishDateTime){
        this.employee = employee;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    @Column(name = "start_datetime")
    private LocalDateTime startDateTime;

    @Column(name = "finish_datetime")
    private LocalDateTime finishDateTime;
}
