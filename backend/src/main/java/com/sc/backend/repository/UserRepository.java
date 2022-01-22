package com.sc.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sc.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findById(Long id);
}
