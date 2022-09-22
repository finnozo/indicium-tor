package com.lov4code.repository;


import com.lov4code.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("FROM Role r where upper(r.name)=?1")
    Optional<Role> findByRoleAndActiveTrue(String name);
}
