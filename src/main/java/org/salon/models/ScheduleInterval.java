package org.salon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleInterval {
    private long id;
    private User employee;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;

    public String toString() {
        return String.format("ScheduleInterval { id: %d, employee: %s, start: %s, end: %s }", id, employee.toString(), startDateTime, finishDateTime);
    }
}
