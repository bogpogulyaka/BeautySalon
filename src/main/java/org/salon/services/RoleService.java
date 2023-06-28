package org.salon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.salon.util.ConnectionPool;
import org.salon.dao.RoleDAO;
import org.salon.dao.impl.RoleDAOImpl;
import org.salon.models.Role;

import java.sql.Connection;
import java.util.List;

public class RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public Role getRole(long id) {
        try (Connection con = ConnectionPool.getConnection()){
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                return roleDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public Role getRoleByName(String name) {
        try (Connection con = ConnectionPool.getConnection()){
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                return roleDAO.getByName(name);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<Role> getAllRoles() {
        try (Connection con = ConnectionPool.getConnection()){
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                return roleDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public Role createRole(Role role){
        try (Connection con = ConnectionPool.getConnection()){
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                if (roleDAO.create(role) != null) {
                    con.commit();
                    return role;
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

    public void updateRole(Role role){
        try (Connection con = ConnectionPool.getConnection()){
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                roleDAO.update(role);
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

    public boolean deleteRole(long id){
        try (Connection con = ConnectionPool.getConnection()){
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                roleDAO.delete(id);
                con.commit();
                return true;
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
    }
}
