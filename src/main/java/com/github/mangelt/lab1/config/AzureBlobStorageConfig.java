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

@Configuration
@Profile(ApiConstants.PROFILE_AZURE)
public class AzureBlobStorageConfig {
	
	@Value("${azure.storage.connection.string}")
	protected String connectionString;

	@Value("${azure.storage.container.name}")
	protected String containerName;
	
	@Bean
	protected BlobServiceClient storageClient()
	{
//			return a new client to use blob storage
			return new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
	}
	
	@Bean
	@ConditionalOnClass(value = {BlobServiceClient.class})
	protected BlobContainerClient  blobContainer(BlobServiceClient storageClient)
	{
		return storageClient.getBlobContainerClient(containerName);
	}
}
