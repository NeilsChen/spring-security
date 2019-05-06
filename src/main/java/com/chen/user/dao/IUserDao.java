package com.chen.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.user.pojo.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-17
 */
public interface IUserDao extends BaseMapper<User> {

	/**
	 * 根据用户名查询角色
	 * @param username
	 * @return Set<String>
	 */
	List<String> getRolesByUserName(String username);

	/**
	 * 根据用户id 查询关联的角色id
	 * @param uid
	 * @return
	 */
	@Select("SELECT role_id FROM sys_user_role WHERE user_id=#{uid}")
	List<Integer> getUserRoleIds(@Param("uid") Integer uid);
	
	/**
	 * 批量插入用户角色关联关系
	 * @param uid
	 * @param roleid
	 * @return
	 */
	int insertUserRole(@Param("uid")Integer uid, @Param("roleIds")List<Integer> roleIds);
	
	/**
	 * 根据用户id删除用户关联角色
	 * @param uid
	 * @return
	 */
	int deleteUserRoleByUid(@Param("uid")Integer uid);
	
	/**
	 * 根据用户id删除用户关联角色(批量)
	 * @param uid
	 * @return
	 */
	int deleteUserRoleByUids(@Param("uids")List<Integer> uids);
}
