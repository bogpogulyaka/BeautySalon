package org.salon.dao;

import org.salon.models.Role;

import java.util.List;

public interface RoleDAO extends DAO<Role> {
    Role getByName(String name);
}
