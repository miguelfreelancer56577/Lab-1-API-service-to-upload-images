package com.github.mangelt.lab1.exception;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mangelt.lab1.domain.ReponseBodyPayload;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		final byte[] body;
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		body = new ObjectMapper().writeValueAsBytes(ReponseBodyPayload
			.builder()
			.status(HttpStatus.UNAUTHORIZED.value())
			.message(authException.getMessage())
			.content(authException.getCause())
			.build());
		
		response.getOutputStream().write(body);
	}

}
