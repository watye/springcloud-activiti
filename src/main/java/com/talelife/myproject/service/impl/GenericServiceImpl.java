package com.talelife.myproject.service.impl;
import java.util.List;

import com.talelife.myproject.mapper.CurdMapper;
import com.talelife.util.Page;
/**
 * 通用crud实现类
 * <p>本类对dao的基本操作进行封装，具体业务类继承本类就可以直接调用基本的crud方法</p>
 * date: 2018-08-27 18:01:00
 * 
 * @author Liuweiyao
 * @version 1.0
 * @param <T> 模型类
 * @param <S> 查询类
 * @param <U> mapper类
 */

public abstract class GenericServiceImpl<T, S, U extends CurdMapper<T,S>>{

	
	public int add(U mapper, T t) {
		return mapper.add(t);
	}

	
	public int delete(U mapper, Long id) {
		return mapper.delete(id);
	}

	
	public int update(U mapper, T t) {
		return mapper.update(t);
	}

	
	public T findByPK(U mapper, Long id) {
		return mapper.findByPK(id);
	}

	
	public List<T> findAll(U mapper) {
		return mapper.findAll();
	}

	
	public List<T> findList(U mapper, S s) {
		return mapper.findList(s);
	}

	
	public Page<T> findPage(U mapper, S s) {
		return new Page<T>(mapper.findPage(s));
	}
	
}