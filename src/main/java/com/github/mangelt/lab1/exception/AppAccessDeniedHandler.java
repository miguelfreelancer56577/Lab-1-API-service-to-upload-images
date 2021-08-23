package com.github.mangelt.lab1.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mangelt.lab1.domain.ReponseBodyPayload;

@Component
public class AppAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		final byte[] body;
		
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		body = new ObjectMapper().writeValueAsBytes(ReponseBodyPayload
			.builder()
			.status(HttpStatus.FORBIDDEN.value())
			.message(accessDeniedException.getMessage())
			.content(accessDeniedException.getCause())
			.build());
		
		response.getOutputStream().write(body);
	}

}
