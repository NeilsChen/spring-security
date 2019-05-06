package com.chen.user.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.common.BaseConst;
import com.chen.user.dao.IUserDao;
import com.chen.user.pojo.User;
import com.chen.user.service.IUserService;

import cn.hutool.core.collection.CollectionUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserDao, User> implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IService<User> service;

	@Override
	public User getUserByUserName(String username) {
		List<User> selectList = userDao.selectList(new QueryWrapper<User>().eq("username", username).eq("is_del", 0));
		if (selectList.size() > 0) {
			return selectList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Set<String> getRolesByUserName(String username) {
		List<String> rolesByUserName = userDao.getRolesByUserName(username);
		HashSet<String> newHashSet = CollectionUtil.newHashSet(rolesByUserName);
		return newHashSet;
	}

	@Override
	public List<Integer> getUserRoleIds(Integer uid) {
		return userDao.getUserRoleIds(uid);
	}

	@Transactional
	@Override
	public boolean saveOrUpdateUser(User user, List<Integer> roleIds) {
		if (user.getId() != null) { // 更新用户
			user.setUpdateTime(new Date());
		} else {
			// 新增用户
			user.setCreateTime(new Date());
			// BCryptPasswordEncoder 加密
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(BaseConst.DEFAULT_PWD));
		}
		userDao.deleteUserRoleByUid(user.getId());
		if (roleIds.size() > 0) {
			userDao.insertUserRole(user.getId(), roleIds);
		}
		return service.saveOrUpdate(user);
	}

	@Transactional
	@Override
	public boolean deleteUserByIds(List<Integer> ids) {
		userDao.deleteUserRoleByUids(ids);
		int deleteBatchIds = userDao.deleteBatchIds(ids);
		return deleteBatchIds > 0 ? true : false;
	}

}
