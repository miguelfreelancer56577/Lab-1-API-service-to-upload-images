package com.github.mangelt.lab1.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.mangelt.lab1.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class PerformanceStorageLoggerAspect {

	@Value(ApiConstants.SPRING_PROFILES_ACTIVE)
	String source;
	
	@Before("@annotation(com.github.mangelt.lab1.annotation.PerformanceStorageLogger)")
	public void logBeforeStorageSource(JoinPoint joinPoint){
		log.info("Getting information from {} storage for {} method.", source, joinPoint.getSignature());
	}
	
	@AfterReturning(pointcut = "@annotation(com.github.mangelt.lab1.annotation.PerformanceStorageLogger)", returning = "result")
	public void logAfterStorageSource(JoinPoint joinPoint, Object result){
		log.info("Information retrieve successfully {}.", result.toString());
	}
	
	@Before("target(com.github.mangelt.lab1.component.ImageValidator) && args(..)")
	public void logBeforeValidatingStorage(JoinPoint joinPoint) {
		log.info("Validating storage information for signature: {}.", joinPoint.getSignature());
	}
	
	@AfterThrowing(value = "target(com.github.mangelt.lab1.component.ImageValidator)", throwing = "ex")
	public void logAfterReturningValidatingStorage(Exception ex) {
		log.error("There was an exception validating the image or the storage. {}", ex.getMessage());
	}
	
	@Around("@within(org.springframework.web.bind.annotation.RestController)")
	public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		log.info("Calling service {}", proceedingJoinPoint.getSignature());
			return proceedingJoinPoint.proceed();
	}
	
	@AfterReturning("@within(org.springframework.web.bind.annotation.RestController)")
	public void logAfterReturningController(JoinPoint joinpoint) {
		log.info("The service {} was retrieved successfully.", joinpoint.getSignature());
	}
	
}
