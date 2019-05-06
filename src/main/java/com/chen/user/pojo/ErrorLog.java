package com.chen.user.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chen.common.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author chenwenchao
 * @since 2019-01-25
 */
@TableName("sys_error_log")
@ApiModel(value="ErrorLog对象", description="")
public class ErrorLog extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "类型：ajax，script")
    private String type;

    @ApiModelProperty(value = "错误代码")
    private Integer code;

    @ApiModelProperty(value = "错误信息")
    private String mes;

    @ApiModelProperty(value = "请求地址")
    private String url;

    @ApiModelProperty(value = "操作用户")
    private Integer userId;

    @ApiModelProperty(value = "客户端ip")
    private String clientIp;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
	public String toString() {
		return "ErrorLog [id=" + id + ", type=" + type + ", code=" + code + ", mes=" + mes + ", url=" + url + ", userId=" + userId + ", clientIp=" + clientIp + ", createTime=" + createTime + ", updateTime=" + updateTime + ", deleteTime=" + deleteTime
				+ ", isDel=" + isDel + "]";
	}
}
