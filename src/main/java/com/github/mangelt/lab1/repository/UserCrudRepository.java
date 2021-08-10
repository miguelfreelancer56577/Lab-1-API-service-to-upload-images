package com.github.mangelt.lab1.repository;

import java.util.Optional;

public interface UserCrudRepository<T>{

		T insertOrReplace(T entity);

		Optional<T> findByEntity(T entity);

		void deleteByEntity(T entity);

}
