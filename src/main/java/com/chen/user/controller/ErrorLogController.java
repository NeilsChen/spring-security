package com.chen.user.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.common.ResponseObj;
import com.chen.common.ReturnData;
import com.chen.user.pojo.ErrorLog;
import com.chen.user.service.IErrorLogService;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/errorLog")
public class ErrorLogController  {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IErrorLogService errorLogService;
	
	@ApiOperation(value = "排序分页查询")
	@GetMapping("selectOrderBy")
	public ResponseObj<Object> selectOrderBy(@ApiParam(value = "页次，从1开始", required = false, example = "1") @RequestParam(name = "pageNo", required = false) Integer pageNo,
			@ApiParam(name = "pageSize", value = "每页大小", required = false, example = "10") @RequestParam(name = "pageSize", required = false) Integer pageSize,
			@ApiParam(name = "orderBy", value = "排序字段", required = false) @RequestParam(name = "orderBy", required = false) String orderBy,
			@ApiParam(name = "isAsc", value = "是否正序排序，默认正序", required = false) @RequestParam(name = "isAsc", required = false) Boolean isAsc) {

		try {
			pageNo = pageNo == null ? 0 : pageNo;
			pageSize = pageSize == null ? 10 : pageSize;
			isAsc = isAsc == null ? true : isAsc;
			// 添加分页数据
			Page<ErrorLog> page = new Page<ErrorLog>(pageNo, pageSize);
			// 添加排序字段
			QueryWrapper<ErrorLog> qw = new QueryWrapper<ErrorLog>();
			if (StrUtil.isNotEmpty(orderBy)) {
				qw.orderBy(true, isAsc, orderBy);
			}else {
				qw.orderBy(true, false, "create_time");
			}
			IPage<ErrorLog> pageData = errorLogService.page(page, qw);
			return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG, pageData);
		} catch (Exception e) {
			log.error("查询错误  ", e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}
	
	@PostMapping(value="saveErrorLog")
	public ResponseObj saveErrorLog(HttpServletRequest request, @RequestParam("data") String data) {
		try {
			ErrorLog errorLog = JSONUtil.toBean(data, ErrorLog.class);
			String clientIP = ServletUtil.getClientIP(request);
			errorLog.setClientIp(clientIP);
			errorLog.setCreateTime(new Date());
			System.out.println(errorLog);
			boolean save = errorLogService.save(errorLog);
			return new ResponseObj(ReturnData.SUCCESS_CODE,ReturnData.SUCCESS_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存数据错误  " + e.getMessage());
			return new ResponseObj(ReturnData.BAD_REQUEST_CODE,ReturnData.BAD_REQUEST_MSG);
		}
	}
	
}
