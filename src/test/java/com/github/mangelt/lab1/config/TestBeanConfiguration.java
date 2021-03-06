package com.github.mangelt.lab1.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.github.mangelt.lab1.component.ImageValidator;
import com.github.mangelt.lab1.component.impl.BaseImageValidatorComponent;
import com.github.mangelt.lab1.util.TestConstants;

import lombok.extern.slf4j.Slf4j;

@TestConfiguration
@Slf4j
public class TestBeanConfiguration {

	private class MockValidator extends BaseImageValidatorComponent{

		@Override
		public void checkStorage() {
			log.debug("Mock method.");
		}
	}
	
	@Bean 
	ImageValidator mockValidator() {
		return new MockValidator();
	}
	
	
}
