package com.example.DemoSecurity.repository;

import com.example.DemoSecurity.Enum.RoleName;
import com.example.DemoSecurity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRoleName(RoleName roleName);

    Role findByRoleName(RoleName roleName);
}
