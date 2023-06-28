package org.salon.dao.impl;

import org.salon.dao.RoleDAO;
import org.salon.models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    private static final String SQL_SELECT_ROLE_BY_ID = "SELECT * FROM roles WHERE id = ?";
    private static final String SQL_SELECT_ALL_ROLES = "SELECT * FROM roles";
    private static final String SQL_SELECT_ROLE_BY_NAME = "SELECT * FROM roles WHERE name = ?";
    private static final String SQL_ADD_ROLE = "INSERT INTO roles (name) VALUES(?)";
    private static final String SQL_UPDATE_ROLE = "UPDATE roles SET name WHERE id = ?";
    private static final String SQL_DELETE_ROLE = "DELETE FROM roles WHERE id = ?";
    private static final String SQL_SELECT_USER_ROLES = "SELECT id, name FROM user_role ur JOIN roles r " +
            "ON ur.role_id = r.id WHERE ur.user_id = ?";
    private static final String SQL_DELETE_USER_ROLES = "DELETE FROM user_role WHERE user_id = ?";

    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public RoleDAOImpl(Connection con){
        this.con = con;
    }


    private Role getRole(ResultSet rs){
        try {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Role(id, name);
        }
        catch (SQLException ex) {
            logger.error("Error. Can't get role." + ex.getMessage());
        }
        return null;
    }

    private void setRole(PreparedStatement stmt, Role role) throws SQLException{
        stmt.setString(1, role.getName());
    }

    @Override
    public Role get(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ROLE_BY_ID)) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getRole(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find role by id " + id + ". " + ex.getMessage());
        }
        return null;
    }


    @Override
    public List<Role> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_ROLES)) {
            List<Role> roles = new ArrayList<>();
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Role role = getRole(rs);
                    roles.add(role);
                }

                return roles;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get all roles."+ ex.getMessage());
        }
        return null;
    }

    @Override
    public Role create(Role role) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_ROLE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setRole(stmt, role);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()) {
                    role.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't add new role " + role.getName() + ". " + ex.getMessage());
        }
        return role;
    }

    @Override
    public void update(Role role) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_ROLE)) {
            stmt.setLong(1, role.getId());
            setRole(stmt, role);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't update role " + role.getName() + ". " + ex.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_ROLE)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete role with id " + id + ". " + ex.getMessage());
        }
    }

    @Override
    public Role getByName(String name) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ROLE_BY_NAME)) {
            stmt.setString(1, name);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getRole(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find Roel by name " + name + ". " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Role> getUserRoles(long userId){
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_ROLES)){
            List<Role> userRoles = new ArrayList<>();
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Role role = getRole(rs);
                    userRoles.add(role);
                }
                return userRoles;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find roles for user with id = " + userId + ". " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void deleteUserRoles(long userId){
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_USER_ROLES)){
            stmt.setLong(1, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete roles for user with id = " + userId + ". " + ex.getMessage());
        }
    }
}
