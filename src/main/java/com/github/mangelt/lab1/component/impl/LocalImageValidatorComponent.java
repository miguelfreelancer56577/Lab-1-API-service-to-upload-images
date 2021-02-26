package com.github.mangelt.lab1.component.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.mangelt.lab1.exception.AppException;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile(value = ApiConstants.PROFILE_LOCAL)
@Slf4j
public class LocalImageValidatorComponent extends BaseImageValidatorComponent{

	@Value(ApiConstants.APP_CONFIG_IMAGE_DIRECTORY)
	String path;
	
	@Override
	public void checkStorage() {
		final boolean mkdir;
//		check if directory already exist otherwise it's created
		File dir = new File(path);
		log.debug("Checking permissions for local storage.");
		if(!dir.exists()) {
			mkdir = dir.mkdir();
			if(!mkdir) {
				log.error(ApiConstants.EXP_ERROR_CREATE_DIR);
				throw new AppException(ApiConstants.EXP_ERROR_CREATE_DIR, null);
			}
		}
		log.debug("Local storage is is availability.");
	}

}
