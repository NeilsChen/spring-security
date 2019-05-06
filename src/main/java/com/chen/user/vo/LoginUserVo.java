package com.chen.user.vo;

import java.io.Serializable;
import java.util.Set;

import com.chen.user.pojo.User;

public class LoginUserVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userName;
	
	private Integer userId;
	
	private String avator;
	
	private Set<String> access;
	
	public LoginUserVo() {}
	
	public LoginUserVo(User user,Set<String> userRoles) {
		this.userId = user.getId();
		this.userName = user.getUsername();
		this.avator = user.getAvatar();
		this.access=userRoles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAvator() {
		return avator;
	}

	public void setAvator(String avator) {
		this.avator = avator;
	}

	public Set<String> getAccess() {
		return access;
	}

	public void setAccess(Set<String> access) {
		this.access = access;
	}

	@Override
	public String toString() {
		return "LoginUserVo [userName=" + userName + ", userId=" + userId + ", avator=" + avator + ", access=" + access + "]";
	}
	
}
