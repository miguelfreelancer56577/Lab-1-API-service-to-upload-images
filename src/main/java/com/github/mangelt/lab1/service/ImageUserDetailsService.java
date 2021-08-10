package com.github.mangelt.lab1.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.domain.RequestUserPayload;

public interface ImageUserDetailsService<T> extends UserDetailsService{
	ResponseEntity<ReponseBodyPayload<RequestUserPayload>> saveUser(T t);
	void deleteUser(T t);
}
