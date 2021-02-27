package com.github.mangelt.lab1.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.github.mangelt.lab1.util.ApiConstants;

/***
 * in order to run this test class you must have declared AZURE_STORAGE_CONNECTION_STRING as an environment variable.
 * 
 * */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AzureBlobStorageConfig.class)
@TestPropertySource(properties = "spring.profiles.active="+ApiConstants.PROFILE_AZURE,
	locations = {"classpath:test.properties"})
class AzureBlobStorageConfigTest {
	
	@Autowired
	BlobServiceClient client;
	
	@Autowired
	BlobContainerClient container;
	
	/**
	 * Test azure client was created successfully.
	 * **/
	@Test
	void testClient() {
		assertThat(client).isNotNull();
	} 
	
	@Test
	void testBlobContainer() {
		assertThat(container).isNotNull();
	}
}
