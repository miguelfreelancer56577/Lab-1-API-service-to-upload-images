package com.github.mangelt.lab1.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.mangelt.lab1.auth.AzureUserPrincipal;
import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.domain.RequestUserPayload;
import com.github.mangelt.lab1.entity.TableStorageUser;
import com.github.mangelt.lab1.exception.AppException;
import com.github.mangelt.lab1.repository.UserCrudRepository;
import com.github.mangelt.lab1.service.ImageUserDetailsService;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile(ApiConstants.PROFILE_AZURE)
public class AzureUserDetailsImpl implements ImageUserDetailsService<RequestUserPayload>{

	@Autowired
	UserCrudRepository<TableStorageUser> repository;
	
	@Autowired ModelMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		final TableStorageUser tableStorageUser = new TableStorageUser(username);
		final Optional<TableStorageUser> findByEntity = repository.findByEntity(tableStorageUser);
		if(!findByEntity.isPresent()) {
			log.debug(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username));
			throw new AppException(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username), new UsernameNotFoundException(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username)));
	 	}
		return new AzureUserPrincipal(findByEntity.get());
	}

	@Override
	public ResponseEntity<Void> delete(String userId) {
		final TableStorageUser tableStorageUser = new TableStorageUser(userId);
		try {
			repository.deleteByEntity(tableStorageUser);
		} catch (Exception e) {
			log.debug(ApiConstants.EXP_ERROR_DELETE_USER);
			throw new AppException(ApiConstants.EXP_ERROR_DELETE_USER, null);
		}
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<ReponseBodyPayload<RequestUserPayload>> create(RequestUserPayload user) {
		final ReponseBodyPayload<RequestUserPayload> response = new ReponseBodyPayload<>(HttpStatus.CREATED.value(), ApiConstants.MSG_CREATED_USER);
		final TableStorageUser row = mapper.map(user, TableStorageUser.class);
		final Optional<TableStorageUser> existsUser = repository.findByEntity(row);
		if(existsUser.isPresent()) {
			log.debug(ApiConstants.EXP_ERROR_USER_ALREADY_REGISTERED);
			throw new AppException(ApiConstants.EXP_ERROR_USER_ALREADY_REGISTERED, null);
		}
		final TableStorageUser rowReturned = repository.insertOrReplace(row);
		RequestUserPayload rs = mapper.map(rowReturned, RequestUserPayload.class);
		response.setContent(rs);
		log.debug("response: {}", response);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ReponseBodyPayload<RequestUserPayload>> update(RequestUserPayload user) {
		final ReponseBodyPayload<RequestUserPayload> response = new ReponseBodyPayload<>(HttpStatus.OK.value(), ApiConstants.MSG_OK_USER);
		final TableStorageUser row = mapper.map(user, TableStorageUser.class);
		final Optional<TableStorageUser> existsUser = repository.findByEntity(row);
		if(!existsUser.isPresent()) {
			log.debug(ApiConstants.EXP_ERROR_USER_NOT_REGISTERED);
			throw new AppException(ApiConstants.EXP_ERROR_USER_NOT_REGISTERED, null);
		}
		final TableStorageUser rowReturned = repository.insertOrReplace(row);
		RequestUserPayload rs = mapper.map(rowReturned, RequestUserPayload.class);
		response.setContent(rs);
		log.debug("response: {}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
