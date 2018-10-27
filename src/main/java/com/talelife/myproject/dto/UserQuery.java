package com.talelife.myproject.dto;
import io.swagger.annotations.ApiModelProperty;
/**
 * 用户查询类
 * date: 2018-09-27 19:01:30
 * 
 * @author Liuweiyao
 * @version 1.0
 */
public class UserQuery extends PageQuery{

	private static final long serialVersionUID = -2815316440518412017L;

	@ApiModelProperty(value="姓名")
	private String username;
	
	@ApiModelProperty(value="年龄")
	private Integer age;

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