package com.martins.mailExample.repository;


import com.martins.mailExample.models.ERole;
import com.martins.mailExample.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
