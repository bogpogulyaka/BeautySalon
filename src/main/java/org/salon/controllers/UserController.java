package org.salon.controllers;

import jakarta.servlet.http.HttpSession;
import org.salon.models.User;
import org.salon.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/html/signup")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model, HttpSession session){
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        User user = userService.registerUser(userForm);
        if(user!=null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        model.addAttribute("error", "User with this login already exists");
        return "signup";
    }

    @PostMapping("/html/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model, HttpSession session){
        User user = userService.loginUser(login, password);
        if(user!=null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @RequestMapping("/html/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
