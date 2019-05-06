package com.chen.user.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chen.common.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwenchao
 * @since 2019-01-12
 */
@TableName("sys_job_name")
@ApiModel(value="JobName对象", description="")
public class JobName extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "运行类名")
    private String jobName;

    @ApiModelProperty(value = "cron表达式")
    private String cron;

    @ApiModelProperty(value = "运行状态 0-未运行，1-运行中，2-运行完毕，3-运行失败")
    private Integer status;

    @ApiModelProperty(value = "定时任务描述")
    private String descn;

    @ApiModelProperty(value = "最后一次运行时间")
    private Date lastRunTime;

    @ApiModelProperty(value = "最后一次运行结果")
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
		return "JobName [id=" + id + ", jobName=" + jobName + ", cron=" + cron + ", status=" + status + ", descn=" + descn + ", lastRunTime=" + lastRunTime + ", lastRunResult=" + lastRunResult + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", deleteTime=" + deleteTime + ", isDel=" + isDel + "]";
	}

}
