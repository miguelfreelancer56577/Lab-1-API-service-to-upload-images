package com.github.mangelt.lab1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Profile(ApiConstants.PROFILE_AWS)
@Slf4j
public class AwsS3Config {
	
	@Value(ApiConstants.APP_CONFIG_IMAGE_AWS_ACCESSKEY)
	String accesskey;
	@Value(ApiConstants.APP_CONFIG_IMAGE_AWS_SECRETKEY)
	String secretkey;
	@Value(ApiConstants.APP_CONFIG_IMAGE_AWS_REGION)
	String region;
	
	@Bean
	AWSCredentials awsCredencials() {
		log.info("Setting up aws credencials.");
		return new BasicAWSCredentials(accesskey, secretkey);
	}
	
	@Bean(name = ApiConstants.BEAN_S3_CLIENT)
	AmazonS3 s3Client(AWSCredentials credentials) {
		log.info("Setting up aws client.");
		return AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.valueOf(region))
				  .build();
	}
}
