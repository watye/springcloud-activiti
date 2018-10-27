package com.talelife.myproject.service;
import java.util.List;

import com.talelife.util.Page;
/**
 * 通用业务接口
 * date: 2018-09-27 13:55:00
 * 
 * @author Liuweiyao
 * @version 1.0
 */
public interface CurdService<T,S>{
	
	int add(T t);
	
	int delete(Long id);
	
	int update(T t);
	
	T findByPK(Long id);
	
	List<T> findAll();
	
	List<T> findList(S s);
	
	Page<T> findPage(S s);
	
}