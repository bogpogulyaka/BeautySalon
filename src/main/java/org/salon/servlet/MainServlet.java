package org.salon.servlet;

import org.salon.commands.CommandController;
import org.salon.commands.impl.LoginCommand;
import org.salon.commands.impl.SignUpCommand;
import org.salon.commands.impl.AppointmentsCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/html/login", "/html/signup", "/html/logout", "/html/appointments"})
public class MainServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MainServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        HttpSession session = req.getSession();
        logger.info("Command: " + command);
        switch (command) {
            case "appointments" -> {
                CommandController workdaysCommand = new AppointmentsCommand();
                workdaysCommand.execute(req, resp);
                resp.sendRedirect("/html/appointments.jsp");
            }
            default -> {
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        HttpSession session = req.getSession();
        logger.info("Command: " + command);
        switch (command) {
            case "login" -> {
                CommandController loginCommand = new LoginCommand();
                if (loginCommand.execute(req, resp)) {
                    resp.sendRedirect("/html/home.jsp");
                } else {
                    req.setAttribute("error", "Invalid username or password");
                    req.getRequestDispatcher("/html/login.jsp").forward(req, resp);
                }
            }
            case "signup" -> {
                CommandController signUpCommand = new SignUpCommand();
                if (signUpCommand.execute(req, resp)) {
                    resp.sendRedirect("/html/home.jsp");
                } else {
                    req.setAttribute("error", "That login or email is already taken");
                    req.getRequestDispatcher("/html/signup.jsp").forward(req, resp);
                }
            }
            case "logout" -> {
                session.setAttribute("user", null);
                resp.sendRedirect("/html/login.jsp");
            }
            default -> {
            }
        }
    }
}