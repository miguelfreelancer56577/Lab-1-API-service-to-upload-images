package com.github.mangelt.lab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mangelt.lab1.domain.ReponseBodyPayload;
import com.github.mangelt.lab1.domain.RequestUserPayload;
import com.github.mangelt.lab1.service.ImageUserDetailsService;
import com.github.mangelt.lab1.util.ApiConstants;

@RestController
@RequestMapping(path = ApiConstants.BASE_API_URL + ApiConstants.MAPPING_USER, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	private ImageUserDetailsService<RequestUserPayload> imageUserDetailsService;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReponseBodyPayload<RequestUserPayload>> create(@RequestBody RequestUserPayload payload) {
		return imageUserDetailsService.create(payload);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReponseBodyPayload<RequestUserPayload>> update(@RequestBody RequestUserPayload payload) {
		return imageUserDetailsService.update(payload);
	}
	
	@DeleteMapping(path = "/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable(name = "userId") String id) {
		return imageUserDetailsService.delete(id);
	}
}
