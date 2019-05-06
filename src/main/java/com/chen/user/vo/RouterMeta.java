package com.chen.user.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * vue 路由meta属性
 */
@JsonInclude(Include.NON_NULL) 
public class RouterMeta implements Serializable{

	private static final long serialVersionUID = 1L;
	private String title;
	private String icon;
	private Boolean hideInBread;
	private Boolean hideInMenu;
	private Boolean notCache;
	private List<String> btnPermission;
	private String beforeCloseName;
	private String href;
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
	public Boolean getHideInBread() {
		return hideInBread;
	}
	public void setHideInBread(Boolean hideInBread) {
		this.hideInBread = hideInBread;
	}
	public Boolean getHideInMenu() {
		return hideInMenu;
	}
	public void setHideInMenu(Boolean hideInMenu) {
		this.hideInMenu = hideInMenu;
	}
	public Boolean getNotCache() {
		return notCache;
	}
	public void setNotCache(Boolean notCache) {
		this.notCache = notCache;
	}
	public List<String> getBtnPermission() {
		return btnPermission;
	}
	public void setBtnPermission(List<String> btnPermission) {
		this.btnPermission = btnPermission;
	}
	public String getBeforeCloseName() {
		return beforeCloseName;
	}
	public void setBeforeCloseName(String beforeCloseName) {
		this.beforeCloseName = beforeCloseName;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	@Override
	public String toString() {
		return "RouterMeta [title=" + title + ", icon=" + icon + ", hideInBread=" + hideInBread + ", hideInMenu=" + hideInMenu + ", notCache=" + notCache + ", btnPermission=" + btnPermission + ", beforeCloseName=" + beforeCloseName + ", href=" + href + "]";
	}
	
}
