package com.github.mangelt.lab1.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.github.mangelt.lab1.entity.TableStorageUser;
import com.github.mangelt.lab1.entity.User;
import com.github.mangelt.lab1.exception.AppException;
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
	private final AuthGroupRepository authGroupRepoitory;
	private final ModelMapper mapper;
	
	public LocalUserDetailsImpl(UserRepository userRepository, AuthGroupRepository authGroup, ModelMapper mapper) {
		this.userRepository = userRepository;
		this.authGroupRepoitory = authGroup;
		this.mapper = mapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username){
		List<AuthGroup> findAuthGroup;
	 	Optional<User> findByUsername = userRepository.findByUserId(username);
	 	if(!findByUsername.isPresent()) {
	 		log.debug(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username));
			throw new AppException(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username), new UsernameNotFoundException(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username)));
	 	}
	 	findAuthGroup = authGroupRepoitory.findByUserId(username);
		return new ImageUserPrincipal(findByUsername.get(), findAuthGroup);
	}

	@Override
	public ResponseEntity<Void> delete(String userId) {
		try {
			//delete roles
			log.debug("number of roles deleted: {}", authGroupRepoitory.removeByUserId(userId));
			//delete user
			log.debug("number of users deleted: {}", userRepository.deleteByUserId(userId));
		} catch (Exception e) {
			log.debug(ApiConstants.EXP_ERROR_DELETE_USER);
			throw new AppException(ApiConstants.EXP_ERROR_DELETE_USER, null);
		}
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<ReponseBodyPayload<RequestUserPayload>> create(RequestUserPayload payload) {
		final ReponseBodyPayload<RequestUserPayload> response = new ReponseBodyPayload<>(HttpStatus.CREATED.value(), ApiConstants.MSG_CREATED_USER);
		final Optional<User> existsUser = userRepository.findByUserId(payload.getUserId());
		final User entity = mapper.map(payload, User.class);
		final User rowReturned;
		final RequestUserPayload rs;
		List<AuthGroup> lstRoles;
		if(existsUser.isPresent()) {
			log.debug(ApiConstants.EXP_ERROR_USER_ALREADY_REGISTERED);
			throw new AppException(ApiConstants.EXP_ERROR_USER_ALREADY_REGISTERED, null);
		}
		rowReturned = userRepository.save(entity);
		//map user Roles
		lstRoles = Stream.of(payload.getAuthGroups().split(ApiConstants.SIGN_COMMA))
			.map(row->AuthGroup
					.builder()
					.userId(payload.getUserId())
					.roleGroup(row)
					.build())
			.collect(Collectors.toList());
		lstRoles = authGroupRepoitory.saveAll(lstRoles);
		rs = mapper.map(rowReturned, RequestUserPayload.class);
		//set the roles 
		rs.setAuthGroups(lstRoles
				.stream()
				.map(AuthGroup::getRoleGroup)
				.collect(Collectors
						.joining(ApiConstants.SIGN_COMMA)));
		response.setContent(rs);
		log.debug("response: {}", response);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ReponseBodyPayload<RequestUserPayload>> update(RequestUserPayload t) {
		return null;
	}

}
