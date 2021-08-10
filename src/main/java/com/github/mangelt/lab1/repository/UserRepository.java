package com.github.mangelt.lab1.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.github.mangelt.lab1.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUserId(String userId);
}
