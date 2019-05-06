package com.chen.common;


import java.util.Date;

/**
 * 实体类父类
 * @author Administrator
 */
public class BaseEntity {

	// 创建时间
	protected Date createTime;
	
	// 更新时间
	protected Date updateTime;
	
	// 删除
	protected Date deleteTime;
	
	// 是否删除（0-未删除，1-逻辑删除，2-永久删除）
	protected Integer isDel;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
}
