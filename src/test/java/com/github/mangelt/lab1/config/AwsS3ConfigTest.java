package com.github.mangelt.lab1.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.github.mangelt.lab1.util.ApiConstants;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AwsS3Config.class)
@TestPropertySource(properties = {
		"app.config.image.aws.accesskey=", 
		"app.config.image.aws.secretkey=",
		"spring.profiles.active="+ApiConstants.PROFILE_AWS},
	locations = "classpath:test.properties")
class AwsS3ConfigTest {

	@Autowired
	AWSCredentials credentials;
	
	@Autowired
	AmazonS3 s3Client;
	
	/*
	 * Test AWSCredentials object is created successfully.
	 * */
	@Test
	void testAWSCredentials() {
		assertThat(credentials).isNotNull();
	}
	
	/*
	 * Test s3 client is created successfully.
	 * */
	@Test
	void testS3Client() {
		assertThat(s3Client).isNotNull();
	}
	
}
