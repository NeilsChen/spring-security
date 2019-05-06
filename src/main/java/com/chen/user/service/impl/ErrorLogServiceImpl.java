package com.chen.user.service.impl;

import com.chen.user.pojo.ErrorLog;
import com.chen.user.dao.IErrorLogDao;
import com.chen.user.service.IErrorLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-25
 */
@Service
public class ErrorLogServiceImpl extends ServiceImpl<IErrorLogDao, ErrorLog> implements IErrorLogService {

}
