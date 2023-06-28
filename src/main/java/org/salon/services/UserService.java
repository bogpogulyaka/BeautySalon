package org.salon.services;

import org.salon.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.salon.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User getUser(long id){
        return userRepository.findById(id).orElse(null);
    };
    public List<User> getAllUsers(){
        return userRepository.findAll();
    };

    public User loginUser(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user != null && Objects.equals(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User registerUser(User user) {
        user.addRole(roleService.getRoleByName("user"));
        if (userRepository.findByLogin(user.getLogin()) != null) {
            return null;
        }
        return userRepository.save(user);
    }

    public void updateUser(User user){
        userRepository.save(user);
    }
    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
}
