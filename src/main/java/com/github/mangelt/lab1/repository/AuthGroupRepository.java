package com.github.mangelt.lab1.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.mangelt.lab1.entity.AuthGroup;

@Transactional
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Long>{
	List<AuthGroup> findByUserId(String userid);
	Long removeByUserId(String userid);
}
