package com.talelife.myproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.talelife.util.Result;

public class BaseController {
	protected static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	public Result exceptionHandle(Exception exception){
		logger.error(exception.getMessage(),exception);
		return Result.fail(exception.getMessage());
	}
}
