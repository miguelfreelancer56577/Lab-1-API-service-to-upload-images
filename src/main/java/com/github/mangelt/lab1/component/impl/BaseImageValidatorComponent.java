package com.github.mangelt.lab1.component.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.mangelt.lab1.component.ImageValidator;
import com.github.mangelt.lab1.domain.FieldError;
import com.github.mangelt.lab1.domain.ImageDetailsPayload;
import com.github.mangelt.lab1.exception.AppValidationException;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile(value = ApiConstants.PROFILE_LOCAL)
@Slf4j
public abstract class BaseImageValidatorComponent implements ImageValidator{

	@Override
	public void checkImage(ImageDetailsPayload image) {
		final List<FieldError> errors = new ArrayList<>();
		
		log.debug("Validating received image. {}", image);
//		it must be of type JPG
		Optional
			.ofNullable(image.getImageFile().getOriginalFilename())
			.ifPresent(content->{
				if(!content.toLowerCase().contains(ApiConstants.CARD_JPG)) {
					errors.add(FieldError
							.builder()
							.fieldName(ApiConstants.REQ_PARAM_IMAGE_FILE)
							.fieldMessage(ApiConstants.IMAGE_SERVICE_FILE_IMAGE_TYPE)
							.build());
				}
			});
		
//		size must be equal or less than 1M
		Optional
		.ofNullable(image.getImageFile().getSize())
		.ifPresent(size->{
			if(size > ApiConstants.RULE_MAX_IMAGE_SIZE) {
				errors.add(FieldError
						.builder()
						.fieldName(ApiConstants.REQ_PARAM_IMAGE_FILE)
						.fieldMessage(ApiConstants.IMAGE_SERVICE_FILE_IMAGE_SIZE)
						.build());
			}
		});
		
		if(!errors.isEmpty()) {
			log.debug("There are some errors with the received image. {}", errors);
			throw new AppValidationException(errors);
		}
		
//		check if storage is present
		this.checkStorage();
		
	}
	
}
