package com.github.mangelt.lab1.domain;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.mangelt.lab1.util.ApiConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserPayload {
	@NotBlank(message = ApiConstants.USER_SERVICE_USERID_MANDATORY)
	private String userId;
	@NotBlank(message = ApiConstants.USER_SERVICE_NAME_MANDATORY)
	private String name;
	@NotBlank(message = ApiConstants.USER_SERVICE_LASTNAME_MANDATORY)
    private String lastName;
	@NotBlank(message = ApiConstants.USER_SERVICE_SECONDNAME_MANDATORY)
    private String secondName;
	@NotBlank(message = ApiConstants.USER_SERVICE_PASSWORD_MANDATORY)
    private String password;
	@NotBlank(message = ApiConstants.USER_SERVICE_EDGE_MANDATORY)
    private String edge;
    private String authGroups;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;
    public String getAuthGroups() {
    	return Optional.ofNullable(this.authGroups).orElseGet(()->ApiConstants.DEFAULT_ROLE);
    }
    public Boolean getIsAccountNonExpired() {
    	return Optional.ofNullable(isAccountNonExpired).orElseGet(()->true);
    }
    public Boolean getIsAccountNonLocked() {
    	return Optional.ofNullable(isAccountNonLocked).orElseGet(()->true);
    }
    public Boolean getIsCredentialsNonExpired() {
    	return Optional.ofNullable(isCredentialsNonExpired).orElseGet(()->true);
    }
    public Boolean getIsEnabled() {
    	return Optional.ofNullable(isEnabled).orElseGet(()->true);
    }
    public String getPassword() {
    	final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder(ApiConstants.DEFAULTS_SECURITY_STRENGTH);
    	return bcryptEncoder.encode(password);
    }
}
