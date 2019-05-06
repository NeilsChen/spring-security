package com.chen.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.user.dao.IRoleDao;
import com.chen.user.pojo.Role;
import com.chen.user.service.IRoleService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<IRoleDao, Role> implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IService<Role> service;
	
	@Override
	public List<Integer> getRoleUserIds(Integer roleid) {
		return roleDao.getRoleUserIds(roleid);
	}

	@Transactional
	@Override
	public boolean deleteRoleByIds(List<Integer> roleIds) {
		
		roleDao.deleteUserRoleByRoleid(roleIds);
		return service.removeByIds(roleIds);
	}

	
}
