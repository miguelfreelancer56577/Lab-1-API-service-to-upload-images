package com.github.mangelt.lab1.domain;

import javax.validation.constraints.NotBlank;

import com.github.mangelt.lab1.util.ApiConstants;

import lombok.Data;

@Data
@NotBlank(message = ApiConstants.IMAGE_SERVICE_NAME_MANDATORY)
public class ImageDetailsPayload {
	String name;
}
