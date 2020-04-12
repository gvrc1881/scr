package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Optional<Role> findByNameAndStatusId(String roleName, Integer statusId);   
}