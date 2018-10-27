package com.talelife.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*import com.talelife.myproject.feign.UserWebService;
import com.talelife.myproject.feign.dto.UserDto;*/
import com.talelife.util.Result;

/**
 * 用户客户端调用例子
 * @author lwy
 *
 */
@RestController
@RequestMapping("/user_client_demo")
public class UserClientDemoController extends BaseController {
	
	/*@Autowired
	private UserWebService userWebService;
	
	@GetMapping("/get_user")
	public Result<UserDto> getUser(Long id){
		return userWebService.getUser(id);
	}*/

}
