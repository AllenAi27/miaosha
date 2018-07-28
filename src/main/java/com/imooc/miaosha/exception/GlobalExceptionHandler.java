package com.imooc.miaosha.exception;

import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;

@ControllerAdvice
public class GlobalExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result<String> exceptionHandle(Exception e){
		e.printStackTrace();
		if(e instanceof GlobalException) {
			GlobalException exception = (GlobalException) e;
			CodeMsg codeMsg = exception.getCm();
			return Result.fail(codeMsg);
		}
		if(e instanceof BindException) {
			List<ObjectError> errorList = ((BindException) e).getAllErrors();
			ObjectError error = errorList.get(0);
			CodeMsg codeMsg = CodeMsg.BIND_ERROR.fillArgs(error.getDefaultMessage());
			return Result.fail(codeMsg);
		}else {
			return Result.fail(CodeMsg.SERVER_ERROR);
		}
	}
}
