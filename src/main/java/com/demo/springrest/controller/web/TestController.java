package com.demo.springrest.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springrest.controller.core.ApiReturnObj;
import com.demo.springrest.controller.core.ErrorCodeMsg;
import com.demo.springrest.model.User;
import com.demo.springrest.util.PropertiesUtil;

@RestController
@RequestMapping("/test")
public class TestController {

	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public Object user() {
		User user = new User();
		user.setId(1);
		user.setEmail("xxx@xxx.com");
		user.setPhone("13000000001");
		user.setName("张三");
		user.setPassword("123456");
		return user;

	}

	
	
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<User> list() {
		List<User> userList = new ArrayList<User>();
		for (int i = 1; i < 10; i++) {
			User user = new User();
			user.setId(i);
			user.setEmail("xxx" + i + "@xxx.com");
			user.setPhone("1300000000" + i);
			user.setName("张三" + i);
			user.setPassword("123456");
			userList.add(user);
		}
		return userList;

	}

	@RequestMapping(value = "/div", method = RequestMethod.GET)
	@ResponseBody
	public int div(@RequestParam("a") int a, @RequestParam("b") int b) {
		// *) div zero error
		// http://localhost:8080/SpringRestApi/test/div?a=10
		// http://localhost:8080/SpringRestApi/test/div?a=10&b=hehe
		// http://localhost:8080/SpringRestApi/test/div?a=10&b=0
		return a / b;
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	@ResponseBody
	public String exception() {
		String a = null;
		return a.toString();
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	@ResponseBody
	public Object error(@RequestParam("code") String code) {
		if ("TEST".equals(code)) {
			//直接返回对象
			return  new ApiReturnObj(1,"成功",code);
		} else {
			ErrorCodeMsg ErrorCodeMsg = new ErrorCodeMsg();
			ErrorCodeMsg.setCode(10001);
			ErrorCodeMsg.setMsg(PropertiesUtil.getValueByKey("ErrorCode.properties", "10001"));
			return ErrorCodeMsg;
		}

	}

	

	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public Object users(@RequestParam("count") int count) {
		
		if(count<=0){
			return  new ApiReturnObj(0,"没有查到数据","");
		}else if(count>1){
			List<User> userList = new ArrayList<User>();
			for (int i = 1; i <= count; i++) {
				User user = new User();
				user.setId(i);
				user.setEmail("xxx" + i + "@xxx.com");
				user.setPhone("1300000000" + i);
				user.setName("张三" + i);
				user.setPassword("123456");
				userList.add(user);
			}
			return userList;
		}else{
			User user = new User();
			user.setId(1);
			user.setEmail("xxx@xxx.com");
			user.setPhone("13000000001");
			user.setName("张三");
			user.setPassword("123456");
			return user;
		}
		

	}
	
	@ResponseBody
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public Object map() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "1");
		map.put("name", "张三");
		map.put("age", "30");
		map.put("email", "xxx@xxx.com");
		return map;

	}
	
}
