package org.salon;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.salon.dao.impl.*;
import org.salon.models.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        var userDao = new UserDAOImpl(em);
        var roleDao = new RoleDAOImpl(em);
        var appointmentDao = new AppointmentDAOImpl(em);
        var scheduleDao = new ScheduleIntervalDAOImpl(em);
        var reviewDao = new ReviewDAOImpl(em);

        List<Role> roles =  roleDao.getAll();
        for (Role role: roles){
            System.out.println(role.toString());
        }

        Role roleUser = roleDao.get(1);
        Role roleEmployee = roleDao.get(2);

        User employeeUser = new User("Mykyta", "Suxodolniy", "mykyta", "12345");
        userDao.create(employeeUser);
        employeeUser.addRole(roleEmployee);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ScheduleInterval interval1 = new ScheduleInterval(
                employeeUser,
                LocalDateTime.parse("2023-06-27 10:00:00", formatter),
                LocalDateTime.parse("2023-06-27 19:00:00", formatter)
        );
        ScheduleInterval interval2 = new ScheduleInterval(
                employeeUser,
                LocalDateTime.parse("2023-06-28 10:00:00", formatter),
                LocalDateTime.parse("2023-06-28 19:00:00", formatter)
        );
        scheduleDao.create(interval1);
        scheduleDao.create(interval2);

        List<ScheduleInterval> schedule = employeeUser.getEmployee_schedule();
        for (ScheduleInterval interval: schedule){
            System.out.println(interval.toString());
        }

        User customerUser = new User("Bogdan", "Pogulyaka", "bogpog", "12345");
        userDao.create(customerUser);
        customerUser.addRole(roleUser);

        Appointment appointment = new Appointment(
                customerUser,
                employeeUser,
                LocalDateTime.parse("2023-06-27 12:00:00", formatter),
                LocalDateTime.parse("2023-06-27 13:00:00", formatter)
        );
        appointmentDao.create(appointment);

        Review review = new Review(
                appointment,
                "Все на висоті"
        );
        reviewDao.create(review);

        review.setContent("Найкращий майстер");
        reviewDao.update(review);

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}