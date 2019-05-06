package com.chen.user.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.chen.common.ResponseObj;
import com.chen.common.ReturnData;
import com.chen.user.pojo.Message;
import com.chen.user.service.IMessageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenwenchao
 * @since 2019-02-01
 */
@RestController
@RequestMapping("/message")
public class MessageController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IMessageService messageService;
	
	@ApiOperation(value = "排序查询所有")
	@GetMapping("selectAllOrderBy")
	public ResponseObj<Object> selectAllOrderBy(@ApiParam(name = "orderBy", value = "排序字段", required = false) @RequestParam(name = "orderBy", required = false) String orderBy,
			@ApiParam(name = "isAsc", value = "是否正序排序，默认正序", required = false) @RequestParam(name = "isAsc", required = false) Boolean isAsc) {
		try {
			isAsc = isAsc == null ? true : isAsc;
			// 添加排序字段
			QueryWrapper<Message> qw = new QueryWrapper<Message>();
			if (!StringUtils.isEmpty(orderBy)) {
				qw.orderBy(true, isAsc, orderBy);
			}
			qw.eq("status", 1);
			List<Message> list = messageService.list(qw);

			return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG, list);
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}
	
	@ApiOperation(value = "查询未读消息条数")
	@GetMapping("selectUnreadCount")
	public ResponseObj<Object> selectUnreadCount() {
		try {
			// 添加排序字段
			QueryWrapper<Message> qw = new QueryWrapper<Message>();
			qw.eq("status", 1);
			int count = messageService.count(qw);
			return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG, count);
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}
}

