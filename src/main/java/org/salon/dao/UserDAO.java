package org.salon.dao;

import org.salon.models.Role;
import org.salon.models.User;

public interface UserDAO extends DAO<User>  {
    User getByLogin(String login);
}
