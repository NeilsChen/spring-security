package com.chen.user.controller;


import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.common.ResponseObj;
import com.chen.user.pojo.Permission;
import com.chen.user.service.IPermissionService;
import com.chen.user.vo.RouterTreeVo;
import com.google.common.collect.Lists;

import cn.hutool.core.util.ObjectUtil;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenwenchao
 * @since 2019-02-13
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IPermissionService permissionService;
	
	@GetMapping("getPermissionTreeData")
	public ResponseObj<Object> getPermissionTreeData(){
		
		try {
			QueryWrapper<Permission> menuqw = new QueryWrapper<Permission>();
			menuqw.orderByAsc("id_str");
			List<Permission> menuData = permissionService.list(menuqw);
			
			List<Permission> treeTable = getTreeTable(0,menuData);
			return  ResponseObj.success(treeTable);
		
		}catch(Exception e) {
			log.error("查询失败！",e);
			return  ResponseObj.badRequest();
		}
	}
	
	@GetMapping("getRouterTreeData")
	public ResponseObj<Object> getRouterTreeData(@RequestParam(value="token",required=true) String token){
		
		try {
			System.out.println("token ========== "+token);
			QueryWrapper<Permission> menuqw = new QueryWrapper<Permission>();
			menuqw.eq("type", "MENU");
			menuqw.orderByAsc("id_str");
			List<Permission> menuData = permissionService.list(menuqw);
			
			QueryWrapper<Permission> buttonqw = new QueryWrapper<Permission>();
			buttonqw.eq("type", "BUTTON");
			buttonqw.orderByAsc("id_str");
			List<Permission> buttonData = permissionService.list(buttonqw);
			
			List<RouterTreeVo> routerTree = getRouterTree(0,menuData,buttonData);
			
			return  ResponseObj.success(routerTree);
		
		}catch(Exception e) {
			log.error("查询失败！",e);
			return  ResponseObj.badRequest();
		}
	}
	
	/**
	 * 生成树形表格数据
	 * @param pid
	 * @param nodes
	 * @return
	 */
	private List<Permission> getTreeTable(Integer pid, List<Permission> nodes) {
		List<Permission> treeList = Lists.newArrayList();
		Iterator<Permission> it = nodes.iterator();
		while(it.hasNext()) {
			Permission next = it.next();
			if(next.getPid()==pid) {
				next.setChildren(getTreeTable(next.getId(),nodes));
				treeList.add(next);
			}
		}
		return treeList;
	}
	
	/**
	 * 生成路由树
	 * @param pid
	 * @param nodes
	 * @param buttons
	 * @return
	 */
	private List<RouterTreeVo> getRouterTree(Integer pid, List<Permission> nodes,  List<Permission> buttons) {
		List<RouterTreeVo> treeList = Lists.newArrayList();
		Iterator<Permission> it = nodes.iterator();
		while(it.hasNext()) {
			Permission next = it.next();
			if(next.getPid()==pid) {
				RouterTreeVo node = new RouterTreeVo(next);
				setButton(node,buttons);
				node.setChildren(getRouterTree(next.getId(),nodes,buttons));
				treeList.add(node);
			}
		}
		return treeList;
	}

	/**
	 * 路由菜单设置按钮权限
	 * @param node
	 * @param buttons
	 */
	private void setButton(RouterTreeVo node, List<Permission> buttons) {
		Iterator<Permission> it = buttons.iterator();
		while(it.hasNext()) {
			Permission next = it.next();
			if(next.getPid()==node.getId()) {
				if(ObjectUtil.isNotNull(node.getMeta().getBtnPermission())) {
					node.getMeta().getBtnPermission().add(next.getName());
				}else {
					node.getMeta().setBtnPermission(Lists.newArrayList(next.getName()));
				}
				it.remove();
			}
		}
	}
}

