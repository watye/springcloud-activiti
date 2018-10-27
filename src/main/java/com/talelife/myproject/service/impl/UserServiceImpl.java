package com.talelife.myproject.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talelife.myproject.dto.UserQuery;
import com.talelife.myproject.mapper.CurdMapper;
import com.talelife.myproject.mapper.UserMapper;
import com.talelife.myproject.model.User;
import com.talelife.myproject.service.UserService;
import com.talelife.util.Page;
/**
 * 用户业务实现类
 * date: 2018-09-27 17:46:40
 * 
 * @author Liuweiyao
 * @version 1.0
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, UserQuery,CurdMapper<User,UserQuery>>  implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public int add(User t) {
		return super.add(userMapper, t);
	}

	@Override
	public int delete(Long id) {
		return super.delete(userMapper, id);
	}

	@Override
	public int update(User t) {
		return super.update(userMapper, t);
	}

	@Override
	public User findByPK(Long id) {
		return super.findByPK(userMapper, id);
	}

	@Override
	public List<User> findAll() {
		return super.findAll(userMapper);
	}

	@Override
	public List<User> findList(UserQuery s) {
		return super.findList(userMapper, s);
	}

	@Override
	public Page<User> findPage(UserQuery s) {
		return super.findPage(userMapper, s);
	}

	
	
	
	
	
}