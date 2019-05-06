package com.chen.user.service.impl;

import com.chen.user.pojo.Permission;
import com.chen.user.dao.IPermissionDao;
import com.chen.user.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-02-13
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<IPermissionDao, Permission> implements IPermissionService {

}
