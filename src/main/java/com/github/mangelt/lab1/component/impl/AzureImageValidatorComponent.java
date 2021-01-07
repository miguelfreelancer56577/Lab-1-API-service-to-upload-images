package com.github.mangelt.lab1.component.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.azure.storage.blob.BlobContainerClient;
import com.github.mangelt.lab1.exception.AppException;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile(ApiConstants.PROFILE_AZURE)
public class AzureImageValidatorComponent extends BaseImageValidatorComponent{

	@Autowired
	BlobContainerClient blobContainer;
	
	@Value("${azure.storage.container.name}")
	protected String containerName;

	@Override
	public void checkStorage() {
		if(!blobContainer.exists()) {
			log.error(ApiConstants.EXP_ERROR_NOT_EXIST_BUCKET.concat(ApiConstants.MSG_FORMAT_ADDING_INFO), containerName);
			throw new AppException(ApiConstants.EXP_ERROR_NOT_EXIST_BUCKET, null);
		}
	}
	

}
