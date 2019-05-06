package com.chen.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.hutool.core.lang.Console;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;

@Component
public class JobUtil {

	private static JobRunListener jobRunListener;
	
	@Autowired
	public void setJobRunListener(JobRunListener jobRunListener) {
		JobUtil.jobRunListener = jobRunListener;
	}
	
	public static void main(String[] args) throws Exception {
		String jobName = "com.chen.schedule.job.HelloJob1";
		String jobName2 = "com.chen.schedule.job.BackupJob";
		String cron = "0/3 * * * * *";
		String cron2 = "0/5 * * * * *";
		boolean startJob = JobUtil.startJob("1",jobName, cron);
		Thread.sleep(1000);
		boolean startJob2 = JobUtil.startJob("2",jobName2, cron2);
		
		System.out.println(startJob+"  "+startJob2);
		
		Thread.sleep(10000);
		stopJob(jobName);
		Thread.sleep(4000);
		startJob("1",jobName, cron);
		Thread.sleep(10000);
		stopAll();
	}
	
	public static boolean startJob(String id, String jobName , String cron) {
		try {
			Task task = (Task) Class.forName(jobName).newInstance();
			CronUtil.schedule(id,cron, task);
			
			// 支持秒级别定时任务
			CronUtil.setMatchSecond(true);
			if(!CronUtil.getScheduler().isStarted()) {
				CronUtil.getScheduler().addListener(jobRunListener);
				CronUtil.start();
			}
			
			return true;
		} catch (Exception e) {
			Console.log(e);
			return false;
		} 
	}
	
	/**
	 * 停止任务  如果任务id存在则停止
	 * @param jobId  任务id
	 */
	public static boolean restartJob(String id, String jobName , String cron) {
		try {
			Task task = (Task) Class.forName(jobName).newInstance();
			CronUtil.schedule(id,cron, task);
			// 支持秒级别定时任务
			CronUtil.setMatchSecond(true);
			if(!CronUtil.getScheduler().isStarted()) {
				CronUtil.start();
			}
			return true;
		} catch (Exception e) {
			Console.log(e);
			return false;
		} 
	}
	
	/**
	 * 停止任务  如果任务id存在则停止
	 * @param jobId  任务id
	 */
	public static void stopJob(String id) {
		CronUtil.remove(id);
		System.out.println("修改"+id+" 的运行状态为运行结束");
	}
	
	/**
	 * 停止所有任务
	 */
	public static void stopAll() {
		CronUtil.stop();
	}
	
}
