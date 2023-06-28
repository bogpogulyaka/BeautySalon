package org.salon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.salon.util.ConnectionPool;
import org.salon.dao.RoleDAO;
import org.salon.dao.UserDAO;
import org.salon.dao.impl.RoleDAOImpl;
import org.salon.dao.impl.UserDAOImpl;
import org.salon.models.Role;
import org.salon.models.User;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUser(long id){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                return userDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers(){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                return userDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public User loginUser(String login, String password) {
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                User user = userDAO.getByLogin(login);
                if (Objects.equals(password, user.getPassword())){
                    return user;
                }
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public User registerUser(User user) {
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                Role role = roleDAO.getByName("user");
                if (userDAO.create(user) != null) {
                    userDAO.addUserRole(user, role);
                    con.commit();
                    return user;
                }
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public void updateUser(User user){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                con.setAutoCommit(false);
                userDAO.update(user);
                con.commit();
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
    }

    public boolean deleteUser(long id, String password){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                con.setAutoCommit(false);
                User user = userDAO.get(id);
                if (Objects.equals(password, user.getPassword())) {
                    userDAO.delete(id);
                    con.commit();
                    return true;
                }
            } catch (Exception ex){
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return false;
    };

    public void addUserRole(long userId, long roleId){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                User user = userDAO.get(userId);
                Role role = roleDAO.get(roleId);
                if (userDAO.addUserRole(user, role) != null) {
                    con.commit();
                }
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
    }
}
