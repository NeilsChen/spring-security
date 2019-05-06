package com.chen.user.service;

import com.chen.user.pojo.Role;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-17
 */
public interface IRoleService extends IService<Role> {

	
	List<Integer> getRoleUserIds(Integer roleid);
	
	boolean deleteRoleByIds(List<Integer> roleIds);
	
}
