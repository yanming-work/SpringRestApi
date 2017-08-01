package com.demo.springrest.controller.core.json;

import com.demo.springrest.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JSONField_Test {
/**
 * springmvc对传入参数的接口HandlerMethodArgumentResolver。那么springmvc同样也也提供了一系列对响应返回值进行处理的接口，核心接口类就是本篇要介绍的
 * @param args
 */
	public static void main(String[] args) {
		    CustomerJsonSerializer cjs= new CustomerJsonSerializer();
		   
			try {
				 // 设置转换 User 类时，只包含 id, name
			    cjs.filter(User.class, "id,name", null);  
				String	include = cjs.toJson(new User());
				cjs = new CustomerJsonSerializer();
			    // 设置转换 User 类时，过滤掉 id, name
			    cjs.filter(User.class, null, "id,password");  

			    String filter = cjs.toJson(new User());

			    System.out.println("include: " + include);
			    System.out.println("filter: " + filter);  
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		     
		
	}
	
}
