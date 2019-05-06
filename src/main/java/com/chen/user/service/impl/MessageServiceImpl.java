package com.chen.user.service.impl;

import com.chen.user.pojo.Message;
import com.chen.user.dao.IMessageDao;
import com.chen.user.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenwenchao
 * @since 2019-02-01
 */
@Service
public class MessageServiceImpl extends ServiceImpl<IMessageDao, Message> implements IMessageService {

}
