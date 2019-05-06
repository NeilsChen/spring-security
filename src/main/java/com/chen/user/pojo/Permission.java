package com.chen.user.pojo;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenwenchao
 * @since 2019-02-13
 */
@TableName("sys_permission")
@ApiModel(value="Permission对象", description="")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    @ApiModelProperty(value = "id串")
    private String idStr;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "类型：menu button")
    private String type;

    @ApiModelProperty(value = "路由路径")
    private String path;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "菜单中隐藏 0-不隐藏 1-隐藏")
    private Integer hideInMenu;

    @ApiModelProperty(value = "面包屑中隐藏 0-不隐藏 1-隐藏")
    private Integer hideInBread;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "标签页缓存 0-缓存 1-不缓存")
    private Integer notCache;

    @ApiModelProperty(value = "链接")
    private String href;

    @ApiModelProperty(value = "关闭页签时调用方法")
    private String beforeCloseName;

    @ApiModelProperty(value = "顺序号")
    private Integer orderNo;

    @TableField(exist = false)
    private List<Permission> children;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getHideInMenu() {
        return hideInMenu;
    }

    public void setHideInMenu(Integer hideInMenu) {
        this.hideInMenu = hideInMenu;
    }

    public Integer getHideInBread() {
        return hideInBread;
    }

    public void setHideInBread(Integer hideInBread) {
        this.hideInBread = hideInBread;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getNotCache() {
        return notCache;
    }

    public void setNotCache(Integer notCache) {
        this.notCache = notCache;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getBeforeCloseName() {
        return beforeCloseName;
    }

    public void setBeforeCloseName(String beforeCloseName) {
        this.beforeCloseName = beforeCloseName;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
    
    public List<Permission> getChildren() {
		return children;
	}

	public void setChildren(List<Permission> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", pid=" + pid + ", idStr=" + idStr + ", level=" + level + ", type=" + type + ", path=" + path + ", name=" + name + ", component=" + component + ", hideInMenu=" + hideInMenu + ", hideInBread=" + hideInBread + ", title="
				+ title + ", icon=" + icon + ", notCache=" + notCache + ", href=" + href + ", beforeCloseName=" + beforeCloseName + ", orderNo=" + orderNo + ", children=" + children + "]";
	}
}
