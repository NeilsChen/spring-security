package com.chen.user.vo;

import java.io.Serializable;
import java.util.List;

import com.chen.user.pojo.Permission;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * vue 路由菜单树
 * @author Administrator
 */
@JsonInclude(Include.NON_NULL) 
public class RouterTreeVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer pid;
	private String name;
	private String path;
	private String component; 
	private RouterMeta meta;
	private List<RouterTreeVo> children;
	
	public RouterTreeVo() {}
	
	public RouterTreeVo(Permission p) {
		RouterMeta meta = new RouterMeta();
		this.setId(p.getId());
		this.setPid(p.getPid());
		this.setName(p.getName());
		this.setPath(p.getPath());
		this.setComponent(p.getComponent());
		
		meta.setTitle(p.getTitle());
		meta.setIcon(p.getIcon());
		meta.setHideInMenu(p.getHideInMenu()==0?false:true);
		meta.setHideInBread(p.getHideInBread()==0?false:true);
		meta.setNotCache(p.getNotCache()==0?false:true);
		meta.setBeforeCloseName(p.getBeforeCloseName());
		this.setMeta(meta);
	}
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public RouterMeta getMeta() {
		return meta;
	}
	public void setMeta(RouterMeta meta) {
		this.meta = meta;
	}
	public List<RouterTreeVo> getChildren() {
		return children;
	}
	public void setChildren(List<RouterTreeVo> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "RouterTreeVo [id=" + id + ", pid=" + pid + ", name=" + name + ", path=" + path + ", component=" + component + ", meta=" + meta + ", children=" + children + "]";
	}
	
}
