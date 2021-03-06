package com.chen.user.service.impl;

import com.chen.user.pojo.JobName;
import com.chen.user.dao.IJobNameDao;
import com.chen.user.service.IJobNameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-12
 */
@Service
@Transactional
public class JobNameServiceImpl extends ServiceImpl<IJobNameDao, JobName> implements IJobNameService {

}
