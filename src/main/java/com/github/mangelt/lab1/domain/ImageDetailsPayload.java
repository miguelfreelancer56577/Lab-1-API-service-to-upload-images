package com.github.mangelt.lab1.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.github.mangelt.lab1.util.ApiConstants;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDetailsPayload {
	@NotBlank(message = ApiConstants.IMAGE_SERVICE_NAME_MANDATORY)
	String imageName;
	@NotNull(message = ApiConstants.IMAGE_SERVICE_FILE_IMAGE_MANDATORY)
	MultipartFile imageFile;
}
