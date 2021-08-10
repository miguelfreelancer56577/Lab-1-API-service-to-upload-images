package com.github.mangelt.lab1.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.mangelt.lab1.auth.ImageUserPrincipal;
import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.domain.RequestUserPayload;
import com.github.mangelt.lab1.entity.AuthGroup;
import com.github.mangelt.lab1.entity.User;
import com.github.mangelt.lab1.repository.AuthGroupRepository;
import com.github.mangelt.lab1.repository.UserRepository;
import com.github.mangelt.lab1.service.ImageUserDetailsService;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile(ApiConstants.PROFILE_LOCAL)
public class LocalUserDetailsImpl implements ImageUserDetailsService<RequestUserPayload>{
	
	private final UserRepository userRepository;
	private final AuthGroupRepository authGroup;
	
	public LocalUserDetailsImpl(UserRepository userRepository, AuthGroupRepository authGroup) {
		this.userRepository = userRepository;
		this.authGroup = authGroup;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username){
		List<AuthGroup> findAuthGroup;
	 	Optional<User> findByUsername = userRepository.findByUserId(username);
	 	if(!findByUsername.isPresent()) {
	 		log.error("cannot find username: " + username);
	 		throw new UsernameNotFoundException("cannot find username: " + username);
	 	}
	 	findAuthGroup = authGroup.findByUserId(username);
		return new ImageUserPrincipal(findByUsername.get(), findAuthGroup);
	}

	@Override
	public ResponseEntity<ReponseBodyPayload<RequestUserPayload>> saveUser(RequestUserPayload t) {
		final ReponseBodyPayload<RequestUserPayload> response = new ReponseBodyPayload<>(HttpStatus.CREATED.value(), ApiConstants.MSG_CREATED_USER_OK);
		return null;
	}

	@Override
	public void deleteUser(RequestUserPayload t) {
		
	}

}
