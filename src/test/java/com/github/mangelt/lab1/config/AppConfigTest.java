package com.github.mangelt.lab1.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class AppConfigTest {
	
	@Autowired
	Tika detector;
	
	@Test
	void beanIsNotNull() {
		assertThat(detector).isNotNull();
	}
}
