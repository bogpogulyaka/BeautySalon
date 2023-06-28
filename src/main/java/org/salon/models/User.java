package org.salon.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String login;
    private String password;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<Role>();

    @OneToMany(mappedBy="client")
    private List<Appointment> client_appointments = new ArrayList<Appointment>();

    @OneToMany(mappedBy="employee")
    private List<Appointment> employee_appointments = new ArrayList<Appointment>();

    @OneToMany(mappedBy="employee")
    private List<ScheduleInterval> employee_schedule = new ArrayList<ScheduleInterval>();

    public void addRole(Role role){
        this.roles.add(role);
    }

    public String toString() {
        return String.format("User { id: %d, name: %s, surname: %s, login: %s, password: %s }", id, name, surname, login, password);
    }
}
