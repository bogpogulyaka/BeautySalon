package org.salon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private long id;
    private Appointment appointment;
    private String content;

    public String toString() {
        return String.format("Review { id : %d, content: %s }", id, content);
    }
}
