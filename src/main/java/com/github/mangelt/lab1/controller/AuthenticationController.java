package com.github.mangelt.lab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.domain.RequestAuthenticationPayload;
import com.github.mangelt.lab1.domain.ResponseAuthenticationPayload;
import com.github.mangelt.lab1.service.AuthenticationService;
import com.github.mangelt.lab1.util.ApiConstants;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = ApiConstants.MAPPING_AUTHENTICATION)
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping(path = ApiConstants.MAPPING_SIGNIN)
	public ResponseEntity<ReponseBodyPayload<ResponseAuthenticationPayload>> signIn(@RequestBody RequestAuthenticationPayload payload){
		return authenticationService.signIn(payload);
	}
	
}
