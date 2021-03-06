package com.github.mangelt.lab1.config;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	Tika tikaDetector() {
		return new Tika();
	}
	
}
