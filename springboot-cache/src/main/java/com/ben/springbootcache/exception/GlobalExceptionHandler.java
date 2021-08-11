package com.ben.springbootcache.exception;

import com.ben.springbootcache.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {InvalidGrantException.class,IllegalStateException.class})
	public Result unAuthorizedExceptionHandler(Exception e) {
		log.error(e.getMessage());
		return Result.buildFail400(e.getMessage());
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public Result accessDeniedExceptionHandler(Exception e) {
		log.error(e.getMessage(),e);
		return Result.buildFail403(e.getMessage());
	}


	@ExceptionHandler(value = Exception.class)
	public Result ExceptionHandler(Exception e) {
		log.error(e.getMessage(), e);
		return Result.buildFail500(e.getMessage());
	}

}
