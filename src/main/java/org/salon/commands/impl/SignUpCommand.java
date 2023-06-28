package org.salon.commands.impl;

import org.salon.commands.CommandController;
import org.salon.models.Role;
import org.salon.models.User;
import org.salon.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class SignUpCommand implements CommandController {
    @Override
    public boolean execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        User user = new User(-1, login, password, firstName, lastName, new ArrayList<Role>());
        User newUser = new UserService().registerUser(user);
        if (newUser != null){
            req.getSession().setAttribute("user", newUser);
            return true;
        }
        return false;
    }
}
