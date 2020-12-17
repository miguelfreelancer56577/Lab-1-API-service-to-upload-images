package com.github.mangelt.lab1.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ImageDetailsPayload {
	@NotBlank(message = ApiConstants.IMAGE_SERVICE_NAME_MANDATORY)
	String imageName;
	@NotNull(message = ApiConstants.IMAGE_SERVICE_FILE_IMAGE_MANDATORY)
	@JsonIgnore
	MultipartFile imageFile;
	String format;
	Long size;
}
