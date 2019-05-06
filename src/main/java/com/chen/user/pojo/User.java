package com.chen.user.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chen.common.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author chenwenchao
 * @since 2019-01-17
 */
@TableName("sys_user")
@ApiModel(value="User对象", description="")
public class User extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;

    @ApiModelProperty(value = "用户名")
    protected String username;

    @ApiModelProperty(value = "真实姓名")
    protected String realname;

    @ApiModelProperty(value = "登录密码")
    protected String password;

    @ApiModelProperty(value = "性别 0-女 1-男")
    protected Integer gender;

    @ApiModelProperty(value = "手机号")
    protected String phone;

    @ApiModelProperty(value = "邮箱")
    protected String email;

    @ApiModelProperty(value = "头像")
    protected String avatar;

    @ApiModelProperty(value = "描述")
    protected String descn;

    @ApiModelProperty(value = "状态 0-正常 1-停用 2-锁定")
    protected Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", realname=" + realname + ", password=" + password + ", gender=" + gender + ", phone=" + phone + ", email=" + email + ", avatar=" + avatar + ", descn=" + descn + ", status=" + status
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", deleteTime=" + deleteTime + ", isDel=" + isDel + "]";
	}

}
