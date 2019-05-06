package com.chen.user.controller;


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
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.annotation.ServiceLimit;
import com.chen.common.ResponseObj;
import com.chen.common.ReturnData;
import com.chen.schedule.JobUtil;
import com.chen.user.pojo.JobName;
import com.chen.user.service.IJobNameService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-12
 */

@RestController
@RequestMapping("/job")
public class JobNameController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IJobNameService jobNameService;
	
	@ServiceLimit(period=5, count=5)
	@ApiOperation(value = "排序分页查询")
	@GetMapping("selectOrderBy")
	public ResponseObj<Object> selectOrderBy(@ApiParam(value = "页次，从1开始", required = false, example = "1") @RequestParam(name = "current", required = false) Integer current,
			@ApiParam(name = "pagesize", value = "每页大小", required = false, example = "10") @RequestParam(name = "pagesize", required = false) Integer pagesize,
			@ApiParam(name = "orderBy", value = "排序字段", required = false) @RequestParam(name = "orderBy", required = false) String orderBy,
			@ApiParam(name = "isAsc", value = "是否正序排序，默认正序", required = false) @RequestParam(name = "isAsc", required = false) Boolean isAsc,
			@ApiParam(name = "jobName", value = "类名", required = false) @RequestParam(name = "jobName", required = false) String jobName,
			@ApiParam(name = "cron", value = "表达式", required = false) @RequestParam(name = "cron", required = false) String cron,
			@ApiParam(name = "status", value = "运行状态", required = false) @RequestParam(name = "status", required = false) String status) {

		try {
			current = current == null ? 0 : current;
			pagesize = pagesize == null ? 10 : pagesize;
			isAsc = isAsc == null ? true : isAsc;
			// 添加分页数据
			Page<JobName> page = new Page<JobName>(current, pagesize);
			// 添加排序字段
			QueryWrapper<JobName> qw = new QueryWrapper<JobName>();
			qw.eq("is_del", 0);// 未删除
			if(StringUtils.isNotEmpty(jobName)) {
				qw.like("job_name", jobName);
			}
			if(StringUtils.isNotEmpty(cron)) {
				qw.like("cron", cron);
			}
			if(StringUtils.isNotEmpty(status)) {
				qw.eq("status", status);
			}
			
			if (StringUtils.isNotEmpty(orderBy)) {
				qw.orderBy(true, isAsc, orderBy);
			}else {
				qw.orderBy(true, false, "create_time");
			}
			
			IPage<JobName> pageData = jobNameService.page(page, qw);
			return new ResponseObj<Object>(ReturnData.SUCCESS_CODE , ReturnData.SUCCESS_MSG, pageData);
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}

	}
	
	@PostMapping("saveOrUpdateData")
	public ResponseObj<Object> saveOrUpdateData(
			@RequestParam(name="id",required=false) Integer id,
			@RequestParam(name="jobName",required=true) String jobName,
			@RequestParam(name="cron",required=true) String cron,
			@RequestParam(name="descn",required=false) String descn) {
		try {
			
			QueryWrapper<JobName> qw = new QueryWrapper<JobName>();
			qw.eq("job_name", jobName);
			int count = jobNameService.count(qw);
			if(count > 0) {
				return new ResponseObj<Object>(203, "唯一约束：运行类已存在！");
			}
			JobName job = new JobName();
			job.setId(id);
			job.setCreateTime(DateUtil.date());
			job.setJobName(jobName);
			job.setCron(cron);
			job.setDescn(descn);
			boolean saveOrUpdate = jobNameService.saveOrUpdate(job);
			Console.log(job);
			if(saveOrUpdate) {
				return new ResponseObj<Object>(ReturnData.SUCCESS_CODE,ReturnData.SUCCESS_MSG,job);
			}else {
				return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE,ReturnData.BAD_REQUEST_MSG);
			}
		}catch(Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE,ReturnData.BAD_REQUEST_MSG);
		}
	} 
	
	@ApiOperation(value = "删除数据")
	@PostMapping("deleteByIds")
	public ResponseObj<Object> deleteByIds(@ApiParam( required = true) 
	@RequestParam(name = "ids", required = true) String ids) {
		try {
			QueryWrapper<JobName> qw = new QueryWrapper<JobName>();
			qw.inSql("id", ids);
			boolean remove = jobNameService.remove(qw);
			if(remove) {
				return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG);
			}else {
				return new ResponseObj<Object>(203, "数据删除失败！");
			}
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}
	
	
	@PostMapping("startJob")
	public ResponseObj<Object> startJob(@RequestParam(name="id",required=true) Integer id) {
		try {
			JobName job = jobNameService.getById(id);
			if(BeanUtil.isEmpty(job)) {
				return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE,"数据不存在！");
			}
			boolean startJob = JobUtil.startJob(String.valueOf(id),job.getJobName(), job.getCron());
			if(startJob) {
				job.setStatus(1);
				jobNameService.updateById(job);
				return new ResponseObj<Object>(ReturnData.SUCCESS_CODE,ReturnData.SUCCESS_MSG);
			}else {
				return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE,"任务启动失败，请检查配置！");
			}
		}catch(Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE,ReturnData.BAD_REQUEST_MSG);
		}
	} 
	
	@PostMapping("stopJob")
	public ResponseObj<Object> stopJob(@RequestParam(name="id",required=true) Integer id) {
		try {
			JobName job = jobNameService.getById(id);
			if(BeanUtil.isEmpty(job)) {
				return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE,"数据不存在！");
			}
			JobUtil.stopJob(String.valueOf(id));
			job.setStatus(0);
			jobNameService.updateById(job);
			
			return new ResponseObj<Object>(ReturnData.SUCCESS_CODE,ReturnData.SUCCESS_MSG);
		}catch(Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE,ReturnData.BAD_REQUEST_MSG);
		}
	} 
}

