package com.demo.springrest.controller.core;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class ApiResponseAdviceException {
	
	private static Logger logger = Logger.getLogger(ApiResponseAdviceException.class);

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseBody
	public ExceptionCodeMsg handle(RuntimeException ex) {
		// *记入异常日志
		System.out.println("记入异常日志");
		ExceptionCodeMsg exceptionCodeMsg = new ExceptionCodeMsg();
		
		exceptionCodeMsg.setException(ex.toString());
		if (ex instanceof NumberFormatException) {// 判断是不是数据转换异常
			exceptionCodeMsg.setCode(-1);
			exceptionCodeMsg.setMsg("异常NumberFormatException");
		}else if (ex instanceof NullPointerException) {// 判断是不是空指针异常
			exceptionCodeMsg.setCode(-1);
			exceptionCodeMsg.setMsg("异常NullPointerException");
		}else{
			exceptionCodeMsg.setCode(-1);
			exceptionCodeMsg.setMsg("异常"+ex.toString());
		}
	 
      
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		ex.printStackTrace(new PrintStream(baos));  
		String exception = baos.toString();  
		  // 记录error级别的信息  
        logger.error(exception); 
        
		return exceptionCodeMsg;
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ExceptionCodeMsg handle(Exception ex) {
		// *记入异常日志
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		ex.printStackTrace(new PrintStream(baos));  
		String exception = baos.toString();  
		  // 记录error级别的信息  
        logger.error(exception); 
        
		ExceptionCodeMsg exceptionCodeMsg = new ExceptionCodeMsg();
		exceptionCodeMsg.setCode(-1);
		exceptionCodeMsg.setMsg(ex.getMessage());
		exceptionCodeMsg.setException("异常");
		return exceptionCodeMsg;

	}
}
