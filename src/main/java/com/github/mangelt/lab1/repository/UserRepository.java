package com.github.mangelt.lab1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.github.mangelt.lab1.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(String userId);
	Long deleteByUserId(String userId);
}
