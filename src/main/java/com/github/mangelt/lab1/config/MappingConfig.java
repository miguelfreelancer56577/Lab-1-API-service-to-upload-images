package com.github.mangelt.lab1.config;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.mangelt.lab1.domain.RequestUserPayload;
import com.github.mangelt.lab1.entity.TableStorageUser;

@Configuration
public class AppConfig {
	
	@Bean
	public ModelMapper mapper() {
		final ModelMapper modelMapper = new ModelMapper();
		//User map
		modelMapper
			.typeMap(RequestUserPayload.class, TableStorageUser.class)
			.addMappings(mapper->{
				mapper.map(RequestUserPayload::getUserId, TableStorageUser::setPartitionKey);
				mapper.map(RequestUserPayload::getUserId, TableStorageUser::setRowKey);
				mapper.map(RequestUserPayload::getAuthGroups, TableStorageUser::setAuthGroups);
				mapper.map(RequestUserPayload::getIsAccountNonExpired, TableStorageUser::setIsAccountNonExpired);
				mapper.map(RequestUserPayload::getIsAccountNonLocked, TableStorageUser::setIsAccountNonLocked);
				mapper.map(RequestUserPayload::getIsCredentialsNonExpired, TableStorageUser::setIsCredentialsNonExpired);
				mapper.map(RequestUserPayload::getIsEnabled, TableStorageUser::setIsEnabled);
			});
		return modelMapper;
	}
}
