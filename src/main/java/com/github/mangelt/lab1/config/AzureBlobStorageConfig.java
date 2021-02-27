package com.github.mangelt.lab1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Profile(ApiConstants.PROFILE_AZURE)
@Slf4j
public class AzureBlobStorageConfig {
	
	@Value(ApiConstants.AZURE_STORAGE_CONNECTION_STRING)
	protected String connectionString;

	@Value(ApiConstants.AZURE_STORAGE_CONTAINER_NAME)
	protected String containerName;
	
	@Bean
	protected BlobServiceClient storageClient()
	{
//			return a new client to use blob storage
		log.debug("{} Setting up azure storage client.", ApiConstants.AZURE_CONFIG_IDENTIFIER);
		return new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
	}
	
	@Bean
	@ConditionalOnClass(value = {BlobServiceClient.class})
	protected BlobContainerClient  blobContainer(BlobServiceClient storageClient)
	{
		log.debug("{} Setting up azure blob client.", ApiConstants.AZURE_CONFIG_IDENTIFIER);
		return storageClient.getBlobContainerClient(containerName);
	}
}
