package com.talelife.myproject.dto;
import java.io.Serializable;

import com.talelife.util.Page;

import io.swagger.annotations.ApiModelProperty;
/**
 * 用户查询类
 * date: 2018-09-27 19:01:30
 * 
 * @author Liuweiyao
 * @version 1.0
 */
public class PageQuery implements Serializable{

	private static final long serialVersionUID = 2267247332350702957L;
	
	@ApiModelProperty(value="页码")
	private Integer pageNum=1;

	@ApiModelProperty(value="每页记录数")
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}