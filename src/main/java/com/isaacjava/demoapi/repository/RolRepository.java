package com.isaacjava.demoapi.repository;

import com.isaacjava.demoapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
