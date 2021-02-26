package com.github.mangelt.lab1.component.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.github.mangelt.lab1.exception.AppException;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile(ApiConstants.PROFILE_AWS)
public class AwsImageValidatorComponent extends BaseImageValidatorComponent{

	@Value(ApiConstants.APP_CONFIG_IMAGE_AWS_BUCKET_NAME)
	String bucketName;
	
	@Autowired
	@Qualifier(ApiConstants.BEAN_S3_CLIENT)
	AmazonS3 client;

	@Override
	public void checkStorage() {
		log.debug("Checking S3 storage availability.");
		if(!client.doesBucketExist(bucketName)) {
			log.error(ApiConstants.EXP_ERROR_NOT_EXIST_BUCKET.concat(ApiConstants.MSG_FORMAT_ADDING_INFO), bucketName);
			throw new AppException(ApiConstants.EXP_ERROR_NOT_EXIST_BUCKET, null);
		}
		log.debug("S3 storage is availability.");
	}
	

}
