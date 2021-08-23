package com.github.mangelt.lab1.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.domain.RequestAuthenticationPayload;
import com.github.mangelt.lab1.domain.ResponseAuthenticationPayload;

@Validated
public interface AuthenticationService {
	ResponseEntity<ReponseBodyPayload<ResponseAuthenticationPayload>> signIn(@Valid RequestAuthenticationPayload payload);
}
