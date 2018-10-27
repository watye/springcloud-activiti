package com.talelife.myproject.controller;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.talelife.myproject.dto.UserForm;
import com.talelife.myproject.dto.UserQuery;
import com.talelife.myproject.model.User;
import com.talelife.myproject.service.UserService;
import com.talelife.util.Page;
import com.talelife.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户控制器
 * @author lwy
 *
 */
@Api(tags="用户操作接口")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	@Resource
	private UserService userService;

	@ApiOperation(value = "分页查询")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Result<Page<User>> page(UserQuery userQuery) {
		return Result.success(userService.findPage(userQuery));
	}

	@ApiOperation(value = "查询单个")
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public Result<User> getUser(@PathVariable @ApiParam(value="用户id") Long id) {
		return Result.success(userService.findByPK(id));
	}

	@ApiOperation(value = "新增")
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public Result<Object> add(UserForm userForm) {
		User user = new User();
		BeanUtils.copyProperties(userForm,user);
		userService.add(user);
		return Result.success();
	}

	@ApiOperation(value = "修改")
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public Result<Object> update(UserForm userForm) {
		User user = new User();
		BeanUtils.copyProperties(userForm,user);
		userService.update(user);
		return Result.success();
	}
	
	@ApiOperation(value = "删除")
	@RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
	public Result<Object> delete(@PathVariable @ApiParam(value="用户id") Long id) {
		userService.delete(id);
		return Result.success();
	}

}
