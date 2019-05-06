package com.chen.user.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.user.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-17
 */
public interface IUserService extends IService<User> {

	/**
	 * 根据用户名查询
	 * @param username
	 * @return User
	 */
	public User getUserByUserName(String username);
	
	/**
	 * 根据用户名查询用户角色
	 * @return Set<String>
	 */
	public Set<String> getRolesByUserName(String username);
	
	/**
	 * 根据用户id查询所有角色id
	 * @param uid
	 * @return
	 */
	List<Integer> getUserRoleIds( Integer uid);
	
	/**
	 * 新增或更新用户信息
	 * @param user
	 * @return 
	 */
	boolean saveOrUpdateUser(User user, List<Integer> roleIds);

	/**
	 * 删除用户信息
	 * @param ids
	 * @return
	 */
	public boolean deleteUserByIds(List<Integer> ids);
}
