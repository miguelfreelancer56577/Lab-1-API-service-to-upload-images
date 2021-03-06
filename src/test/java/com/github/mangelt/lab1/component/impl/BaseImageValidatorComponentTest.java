package com.github.mangelt.lab1.component.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.github.mangelt.lab1.component.ImageValidator;
import com.github.mangelt.lab1.config.AppConfig;
import com.github.mangelt.lab1.config.TestBeanConfiguration;
import com.github.mangelt.lab1.domain.ImageDetailsPayload;
import com.github.mangelt.lab1.exception.AppValidationException;
import com.github.mangelt.lab1.util.ApiConstants;
import com.github.mangelt.lab1.util.TestConstants;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestBeanConfiguration.class, AppConfig.class})
@TestPropertySource(properties = {"spring.profiles.active="+ApiConstants.PROFILE_LOCAL})
@Slf4j
class BaseImageValidatorComponentTest {

	@Autowired
	ImageValidator imageValidator;
	
	@Test
	void invalidFormat() {
		final byte[] content = null;
//		first it's created #{MultipartFile} object to be passed through component
		final MultipartFile imageFile = new MockMultipartFile(
				TestConstants.T_FILE_NAME1, 
				TestConstants.T_FILE_ORIGINAL_NAME1,
				TestConstants.T_FILE_CONTENT_TYPE1,
				content);
//		It's created a new POJO to call method.
		final ImageDetailsPayload image = ImageDetailsPayload
				.builder()
				.imageFile(imageFile)
				.build();
		Assertions.assertThrows(AppValidationException.class, ()->{
			imageValidator.checkImage(image);
		});
	}
	
	@Test
	void invalidContentType() {
		final Path path = Paths.get(TestConstants.T_FILE_PATH1);
		final byte[] content;
		final MultipartFile imageFile;
		try {
		    content = Files.readAllBytes(path);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
//		first it's created #{MultipartFile} object to be passed through component
		imageFile = new MockMultipartFile(
				TestConstants.T_FILE_NAME1, 
				TestConstants.T_FILE_ORIGINAL_NAME2,
				TestConstants.T_FILE_CONTENT_TYPE1,
				content);
//		It's created a new POJO to call method.
		final ImageDetailsPayload image = ImageDetailsPayload
				.builder()
				.imageFile(imageFile)
				.build();
		Assertions.assertThrows(AppValidationException.class, ()->{
			imageValidator.checkImage(image);
		});
	}
	
	@Test
	void invalidSize() {
		final Path path = Paths.get(TestConstants.T_FILE_PATH2);
		final byte[] content;
		final MultipartFile imageFile;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
//		first it's created #{MultipartFile} object to be passed through component
		imageFile = new MockMultipartFile(
				TestConstants.T_FILE_NAME1, 
				TestConstants.T_FILE_ORIGINAL_NAME2,
				TestConstants.T_FILE_CONTENT_TYPE1,
				content);
//		It's created a new POJO to call method.
		final ImageDetailsPayload image = ImageDetailsPayload
				.builder()
				.imageFile(imageFile)
				.build();
		Assertions.assertThrows(AppValidationException.class, ()->{
			imageValidator.checkImage(image);
		});
	}
	
	@Test
	void happyPath() {
		Exception expFlag = null;
		final Path path = Paths.get(TestConstants.T_FILE_PATH3);
		final byte[] content;
		final MultipartFile imageFile;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
//		first it's created #{MultipartFile} object to be passed through component
		imageFile = new MockMultipartFile(
				TestConstants.T_FILE_NAME1, 
				TestConstants.T_FILE_ORIGINAL_NAME2,
				TestConstants.T_FILE_CONTENT_TYPE1,
				content);
//		It's created a new POJO to call method.
		final ImageDetailsPayload image = ImageDetailsPayload
				.builder()
				.imageFile(imageFile)
				.build();
		try {
			imageValidator.checkImage(image);
		} catch (Exception e1) {
			expFlag = e1;
		}
		assertThat(expFlag).isNull();
	}
	
}
