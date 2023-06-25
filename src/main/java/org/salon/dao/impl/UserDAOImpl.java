package org.salon.dao.impl;

import org.salon.dao.UserDAO;
import org.salon.models.Role;
import org.salon.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String SQL_ADD_USER = "INSERT INTO user " +
            "(name, surname, login, password) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE user SET " +
            "name = ?, surname = ?, login = ?, password = ? WHERE id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String SQL_ADD_USER_ROLE = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";

    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public UserDAOImpl(Connection con){
        this.con = con;
    }


    private User getUser(ResultSet rs){
        try {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String login = rs.getString("login");
            String password = rs.getString("password");

            List<Role> roles = new RoleDAOImpl(con).getUserRoles(id);
            return new User(id, name, surname, login, password, roles);
        }
        catch (SQLException ex) {
            logger.error("Error. Can't get User." + ex.getMessage());
        }
        return null;
    }

    private void setUser(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getSurname());
        stmt.setString(3, user.getLogin());
        stmt.setString(4, user.getPassword());
    }


    @Override
    public User get(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getUser(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find User by id " + id + ". " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_USERS)){
            List<User> users = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = getUser(rs);
                    users.add(user);
                }
                return users;
            }
        }
        catch (SQLException ex) {
            logger.error("Error. Can't get all users." + ex.getMessage());
        }
        return null;
    }

    @Override
    public User getByLogin(String login) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_BY_LOGIN)){
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getUser(rs);
                }
            }
        }
        catch (SQLException ex) {
            logger.error("Error. Can't find user by email " + login + ". " + ex.getMessage());
        }
        return null;
    }

    @Override
    public User create(User user) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_USER, PreparedStatement.RETURN_GENERATED_KEYS)){
            setUser(stmt, user);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex){
            logger.error("Error. Can't create new User: " + ex.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_USER)){
            stmt.setLong(5, user.getId());
            setUser(stmt, user);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_USER)){
            new RoleDAOImpl(con).deleteUserRoles(id);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    @Override
    public User addUserRole(User user, Role role) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_USER_ROLE)){
            stmt.setLong(1, user.getId());
            stmt.setLong(2, role.getId());
            stmt.executeUpdate();
            user.getRoles().add(role);
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return user;
    }
}
