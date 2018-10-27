package com.talelife.myproject.dto;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
/**
 * 用户表单类
 * date: 2018-09-27 19:01:30
 * 
 * @author Liuweiyao
 * @version 1.0
 */
public class UserForm implements Serializable{

	private static final long serialVersionUID = 2540995087926723249L;

	@ApiModelProperty(value="用户id")
	private Long id;
	
	@ApiModelProperty(value="姓名",required=true)
	private String username;
	
	@ApiModelProperty(value="年龄",required=true)
	private Integer age;

	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
 	    this.id=id;
    }
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
 	    this.username=username;
    }
	public Integer getAge(){
		return age;
	}
	
	public void setAge(Integer age){
 	    this.age=age;
    }

}