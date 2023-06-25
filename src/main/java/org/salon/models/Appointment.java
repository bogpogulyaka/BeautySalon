package org.salon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private long id;
    private User client;
    private User employee;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;
    private Status status;
}
