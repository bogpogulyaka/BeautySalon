package org.salon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String name;
    private String surname;
    private String login;
    private String password;

    private List<Role> roles;

    public String toString() {
        return String.format("User { id: %d, name: %s, surname: %s, login: %s, password: %s }", id, name, surname, login, password);
    }
}
