package com.github.mangelt.lab1.domain;

import javax.validation.constraints.NotBlank;

import com.github.mangelt.lab1.util.ApiConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class RequestAuthenticationPayload {
	@NotBlank(message = ApiConstants.USER_SERVICE_USERID_MANDATORY)
	protected String userId;
	@NotBlank(message = ApiConstants.USER_SERVICE_PASSWORD_MANDATORY)
	protected String password;
}
