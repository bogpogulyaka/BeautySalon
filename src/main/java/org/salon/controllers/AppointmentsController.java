package org.salon.controllers;

import org.salon.models.Appointment;
import org.salon.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AppointmentsController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @RequestMapping("/html/appointments/{page}")
    public String appointmentList(@PathVariable int page, Model model){
        int pageSize = 3;

        Page<Appointment> pageAppointments = appointmentService.getAllAppointments(page, pageSize);
        List<Appointment> listAppointments = pageAppointments.getContent();
        model.addAttribute("listAppointments", listAppointments);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", pageAppointments.getTotalPages());
        model.addAttribute("startPage", Math.max(1, page - 2));
        model.addAttribute("endPage", Math.min(pageAppointments.getTotalPages(), page + 2));

        return "appointments";
    }
}
