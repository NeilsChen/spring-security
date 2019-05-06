package com.chen.user.dao;

import com.chen.user.pojo.Role;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-17
 */
public interface IRoleDao extends BaseMapper<Role> {

	
	/**
	 * 根据角色id 查询关联的用户id
	 * @param uid
	 * @return
	 */
	@Select("SELECT user_id FROM sys_user_role WHERE role_id=#{roleid}")
	List<Integer> getRoleUserIds(@Param("roleid") Integer roleid);
	
	
	int deleteUserRoleByRoleid(@Param("roleIds") List<Integer> roleIds);
}
