package com.chen.schedule;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务实体类
 */
public class JobBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String jobName;
	private String cron;
	private Integer status;
	private String descn;
	private Date lastRunTime;
	private String lastRunResult;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Date getLastRunTime() {
		return lastRunTime;
	}
	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
	public String getLastRunResult() {
		return lastRunResult;
	}
	public void setLastRunResult(String lastRunResult) {
		this.lastRunResult = lastRunResult;
	}
	@Override
	public String toString() {
		return "JobBean [id=" + id + ", jobName=" + jobName + ", cron=" + cron + ", status=" + status + ", descn=" + descn + ", lastRunTime=" + lastRunTime + ", lastRunResult=" + lastRunResult + "]";
	}
	
}
