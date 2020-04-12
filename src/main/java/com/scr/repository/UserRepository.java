package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findAllByStatusId(Integer statusId);
	void deleteById(Long id);
	List<User> findByIdAndStatusId(Long id, Integer statusId);
	boolean existsByStatusId(Integer statusId);
	boolean existsByEmailAndStatusId(String email, Integer statusId);
	User findByEmailIgnoreCase(String email);
}