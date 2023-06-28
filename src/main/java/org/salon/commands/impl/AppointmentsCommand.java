package org.salon.commands.impl;

import org.salon.commands.CommandController;
import org.salon.models.Appointment;
import org.salon.services.AppointmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AppointmentsCommand implements CommandController {

    @Override
    public boolean execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            AppointmentService appointmentsService = new AppointmentService();
            List<Appointment> appointments = appointmentsService.getAppointmentsByStatus(Appointment.Status.Scheduled);
            req.getSession().setAttribute("appointments", appointments);
            req.getRequestDispatcher("/html/appointments.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
