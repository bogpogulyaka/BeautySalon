package org.salon.commands.impl;

import org.salon.commands.CommandController;
import org.salon.models.User;
import org.salon.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements CommandController {
    @Override
    public boolean execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserService userService = new UserService();
        User newUser = userService.loginUser(login, password);
        if (newUser != null) {
            req.getSession().setAttribute("user", newUser);
            return true;
        }
        return false;
    }
}