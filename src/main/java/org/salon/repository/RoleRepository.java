package org.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.salon.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    @Query("SELECT * FROM roles WHERE name = ?1")
    Role findByName(String name);
}
