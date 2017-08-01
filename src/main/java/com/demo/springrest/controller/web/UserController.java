package com.demo.springrest.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.springrest.controller.core.ApiReturnObj;
import com.demo.springrest.controller.core.json.JSONField;
import com.demo.springrest.controller.core.json.JSONFieldMap;
import com.demo.springrest.model.Tag;
import com.demo.springrest.model.User;
import com.demo.springrest.model.UserTag;

@Controller
@RequestMapping("user")
public class UserController {

	@ResponseBody
	@RequestMapping(value = "/u", method = RequestMethod.GET)
	public User getUser() {
		User user = new User();
		user.setId(1);
		user.setEmail("xxx@xxx.com");
		user.setPhone("13000000001");
		user.setName("张三");
		user.setPassword("123456");
		return user;
	}

	@JSONField(type = User.class, include = "name,phone", filter = "")
	@RequestMapping(value = "/u1", method = RequestMethod.GET)
	public User getUser1() {
		User user = new User();
		user.setId(1);
		user.setEmail("xxx@xxx.com");
		user.setPhone("13000000001");
		user.setName("张三");
		user.setPassword("123456");
		return user;
	}

	// 返回时只包含 include 内的 name, phone 字段 ,不能有 @ResponseBody
	// 可以使用多个 @JSON 注解，如果是嵌套对象的话
	@JSONField(type = User.class, include = "name,phone")
	@RequestMapping(value = "/u2", method = RequestMethod.GET)
	public Object user2() {
		User user = new User();
		user.setId(1);
		user.setEmail("xxx@xxx.com");
		user.setPhone("13000000001");
		user.setName("张三");
		user.setPassword("123456");
		return user;

	}

	// 返回时只包含 include 内的 name, phone 字段 ,不能有 @ResponseBody
	// 可以使用多个 @JSON 注解，如果是嵌套对象的话
	@JSONField(type = User.class, include = "name,email")
	@RequestMapping(value = "/u3", method = RequestMethod.GET)
	public Object user3() {
		User user = new User();
		user.setId(1);
		user.setEmail("xxx@xxx.com");
		user.setPhone("13000000001");
		user.setName("张三");
		user.setPassword("123456");
		return user;

	}
	// 返回时只包含 include 内的 name, phone 字段
		// 可以使用多个 @JSON 注解，如果是嵌套对象的话
	@JSONField(type = User.class, include = "name,phone,email")
	@JSONField(type = Tag.class, filter = "id")
	@RequestMapping(value = "/u4", method = RequestMethod.GET)
	public Object user4() {
		
		UserTag userTag = new UserTag();
		
		User user = new User();
		user.setId(1);
		user.setEmail("xxx@xxx.com");
		user.setPhone("13000000001");
		user.setName("张三");
		user.setPassword("123456");
		
		userTag.setUser(user);
		
		Tag tag= new Tag();
		tag.setId(1);
		tag.setTagName("好好学习");
		userTag.setTag(tag);
		return userTag;

	}

	// 返回时候不包含 filter 内的 password, id 字段 ,不能有 @ResponseBody
	@JSONField(type = User.class, filter = "id,password")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
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
	
	
	
	// 返回时候不包含 filter 内的 password, id 字段 ,不能有 @ResponseBody
		@JSONField(type = User.class, filter = "id,password")
		@RequestMapping(value = "/listcount", method = RequestMethod.GET)
		public Object listcount(@RequestParam("count") int count)  {
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

		
		@JSONFieldMap(type="MAP",include = "name,email")
		@RequestMapping(value = "/map", method = RequestMethod.GET)
		public Object map() {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", "1");
			map.put("name", "张三");
			map.put("age", "30");
			map.put("email", "xxx@xxx.com");
			return map;

		}
		
		
		@JSONFieldMap(type="MAPLIST",include = "name,email")
		@RequestMapping(value = "/maplist", method = RequestMethod.GET)
		public Object maplist() {
			List<Map<String, String>> maplist=new ArrayList<Map<String,String>>();
			for (int i=0;i<10;i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", i+"");
				map.put("name", "张三"+i);
				map.put("age", "30");
				map.put("email", "xxx@xxx.com");
				maplist.add(map);
			}
			
			return maplist;

		}
		
		
		@JSONFieldMap(type="MAPLIST",filter = "id")
		@RequestMapping(value = "/maplist2", method = RequestMethod.GET)
		public Object maplist2() {
			List<Map<String, String>> maplist=new ArrayList<Map<String,String>>();
			for (int i=0;i<10;i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", i+"");
				map.put("name", "张三"+i);
				map.put("age", "30");
				map.put("email", "xxx@xxx.com");
				maplist.add(map);
			}
			
			return maplist;

		}
		
		
		

}