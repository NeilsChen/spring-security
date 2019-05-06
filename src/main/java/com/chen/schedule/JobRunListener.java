package com.chen.schedule;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.common.JobStatusEnum;
import com.chen.user.pojo.JobName;
import com.chen.user.service.IJobNameService;

import cn.hutool.cron.TaskExecutor;
import cn.hutool.cron.listener.TaskListener;

/**
 * 定时任务触发器，运行状态变更时触发相应操作
 */
@Component
public class JobRunListener implements TaskListener{

	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private IJobNameService jobNameService;
	
	@Override
	public void onStart(TaskExecutor executor) {
		String jobName = executor.getTask().getClass().getName();
		log.info("开始运行 "+jobName+" 任务");
		QueryWrapper<JobName> qw = new QueryWrapper<JobName>();
		qw.eq("job_name", jobName);
		JobName one = jobNameService.getOne(qw);
		one.setStatus(JobStatusEnum.RUNNING.getValue());
		one.setLastRunTime(new Date());
		one.setLastRunResult("开始运行...");
		jobNameService.updateById(one);
		
	}

	@Override
	public void onSucceeded(TaskExecutor executor) {
		String jobName = executor.getTask().getClass().getName();
		log.info("运行 "+jobName+" 任务成功");
		QueryWrapper<JobName> qw = new QueryWrapper<JobName>();
		qw.eq("job_name", jobName);
		JobName one = jobNameService.getOne(qw);
		one.setStatus(JobStatusEnum.SUCCESS.getValue());
		one.setLastRunTime(new Date());
		one.setLastRunResult("运行成功！");
		jobNameService.updateById(one);
	}

	@Override
	public void onFailed(TaskExecutor executor, Throwable exception) {
		String jobName = executor.getTask().getClass().getName();
		log.info("运行 "+jobName+" 任务失败");
		QueryWrapper<JobName> qw = new QueryWrapper<JobName>();
		qw.eq("job_name", jobName);
		JobName one = jobNameService.getOne(qw);
		one.setStatus(JobStatusEnum.FAILED.getValue());
		one.setLastRunTime(new Date());
		one.setLastRunResult("运行失败");
		jobNameService.updateById(one);
	}

}
