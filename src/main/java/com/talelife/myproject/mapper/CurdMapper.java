package com.talelife.myproject.mapper;
import java.util.List;

import com.github.pagehelper.Page;
/**
 * 通用数据接口
 * date: 2018-09-27 13:43:46
 * @author Liuweiyao
 * @version 1.0
 * @param <T>
 * @param <S>
 */
public interface CurdMapper<T,S>{
	
	int add(T t);
	
	int update(T t);
	
	T findByPK(Long id);
	
	int delete(Long id);

	List<T> findAll();
	
	List<T> findList(S s);
	
	Page<T> findPage(S s);
}