package org.salon.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {
    public enum Status {
        Scheduled("Scheduled"), Completed("Completed");
        private final String value;
        Status(String value){
            this.value = value;
        }
        @Override
        public String toString() {
            return value;
        }
    }

    public Appointment(User client, User employee, LocalDateTime startDateTime, LocalDateTime finishDateTime) {
        this.client = client;
        this.employee = employee;
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    @Column(name = "start_datetime")
    private LocalDateTime startDateTime;

    @Column(name = "finish_datetime")
    private LocalDateTime finishDateTime;

    private Status status = Status.Scheduled;

    @OneToMany(mappedBy="appointment")
    private Review review;
}
