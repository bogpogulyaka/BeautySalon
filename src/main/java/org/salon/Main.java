package org.salon;

import org.salon.dao.ScheduleIntervalDAO;
import org.salon.dao.impl.*;
import org.salon.models.*;
import org.salon.util.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection con = ConnectionPool.getConnection()) {
            var userDao = new UserDAOImpl(con);
            var roleDao = new RoleDAOImpl(con);
            var appointmentDao = new AppointmentDAOImpl(con);
            var scheduleDao = new ScheduleIntervalDAOImpl(con);
            var reviewDao = new ReviewDAOImpl(con);

            List<Role> all_roles =  roleDao.getAll();
            for (Role role: all_roles){
                System.out.println(role.toString());
            }

            Role roleUser = roleDao.get(1);
            Role roleEmployee = roleDao.get(2);

            User employeeUser = new User(-1, "Mykyta", "Suxodolniy", "mykyta", "12345", new ArrayList<Role>());
            userDao.create(employeeUser);
            userDao.addUserRole(employeeUser, roleEmployee);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ScheduleInterval interval1 = new ScheduleInterval(
                    -1,
                    employeeUser,
                    LocalDateTime.parse("2023-06-27 10:00:00", formatter),
                    LocalDateTime.parse("2023-06-27 19:00:00", formatter)
            );
            ScheduleInterval interval2 = new ScheduleInterval(
                    -1,
                    employeeUser,
                    LocalDateTime.parse("2023-06-28 10:00:00", formatter),
                    LocalDateTime.parse("2023-06-28 19:00:00", formatter)
            );
            scheduleDao.create(interval1);
            scheduleDao.create(interval2);

            List<ScheduleInterval> schedule = scheduleDao.getByEmployeeId(employeeUser.getId());
            for (ScheduleInterval interval: schedule){
                System.out.println(interval.toString());
            }

            User customerUser = new User(-1, "Bogdan", "Pogulyaka", "bogpog", "12345", new ArrayList<Role>());
            userDao.create(customerUser);
            userDao.addUserRole(customerUser, roleUser);

            Appointment appointment = new Appointment(
                    -1,
                    customerUser,
                    employeeUser,
                    LocalDateTime.parse("2023-06-27 12:00:00", formatter),
                    LocalDateTime.parse("2023-06-27 13:00:00", formatter),
                    Appointment.Status.Scheduled
            );
            appointmentDao.create(appointment);

            Review review = new Review(
                    -1,
                    appointment,
                    "Все на висоті"
            );
            reviewDao.create(review);

            review.setContent("Найкращий майстер");
            reviewDao.update(review);

        } catch (SQLException ex) {
            System.out.println("Error. Can't connect to database. " + ex.getMessage());
        } finally {
            try {
                ConnectionPool.closePool();
            } catch (SQLException ex) {
                System.out.println("Error. Can't close database connection. " + ex.getMessage());
            }
        }
    }
}