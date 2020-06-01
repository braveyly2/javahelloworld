package com.spring.mvc.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.mvc.integrate.User;

/**
 * 控制器: 返回json格式的字符串
 * 
 * @author 	Lian
 * @date	2016年5月17日
 * @since	1.0
 */
@Controller
@RequestMapping("/json")
public class JsonController {

	@ResponseBody
	@RequestMapping("/user1")
	public User get1() {
		User user = new User();
		user.setId(1);
		user.setName("jayjay");
		user.setBirth(new Date());
		return user;
	}
	//input: 	json NOK
	//			x-www-for-urlencoded OK
	//			form-data: OK
	//output:json
	@ResponseBody
	@RequestMapping("/user2")
	public User get2(User user) {
		System.out.println(user.getId()+user.getName());
		user.setId(user.getId()+1);
		return user;
	}

	//input: 	json OK
	//			x-www-for-urlencoded NOK
	//			form-data: NOK
	//output:json
	@ResponseBody
	@RequestMapping("/user3")
	public User get3(@RequestBody User user) {
		System.out.println(user.getId()+user.getName());
		user.setId(user.getId()+1);
		return user;
	}
}
