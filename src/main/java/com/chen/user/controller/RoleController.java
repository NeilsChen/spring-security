package com.chen.user.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.chen.common.ResponseObj;
import com.chen.user.pojo.Role;
import com.chen.user.service.IRoleService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenwenchao
 * @since 2019-01-17
 */
@RestController
@RequestMapping("/role")
public class RoleController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IRoleService roleSerivce;
	
	@ApiOperation(value = "排序分页查询")
	@GetMapping("selectOrderBy")
	public ResponseObj<Object> selectOrderBy(@ApiParam(value = "页次，从1开始", required = false, example = "1") @RequestParam(name = "current", required = false) Integer current,
			@ApiParam(name = "pagesize", value = "每页大小", required = false, example = "10") @RequestParam(name = "pagesize", required = false) Integer pagesize,
			@ApiParam(name = "name", value = "角色名或描述", required = false) @RequestParam(name = "name", required = false) String name,
			@ApiParam(name = "status", value = "状态", required = false) @RequestParam(name = "status", required = false) String status,
			@ApiParam(name = "orderBy", value = "排序字段", required = false) @RequestParam(name = "orderBy", required = false) String orderBy,
			@ApiParam(name = "isAsc", value = "是否正序排序，默认正序", required = false) @RequestParam(name = "isAsc", required = false) Boolean isAsc) {

		try {
			current = current == null ? 0 : current;
			pagesize = pagesize == null ? 10 : pagesize;
			isAsc = isAsc == null ? true : isAsc;
			// 添加分页数据
			Page<Role> page = new Page<Role>(current, pagesize);
			// 添加排序字段
			QueryWrapper<Role> qw = new QueryWrapper<Role>();
			if(StringUtils.isNotEmpty(name)) {
				qw.and(i -> i.like("name", name).or().like("descn", name));
			}
			if(StringUtils.isNotEmpty(status)) {
				qw.eq("status", status);
			}
			if (StringUtils.isNotEmpty(orderBy)) {
				qw.orderBy(true, isAsc, orderBy);
			}else {
				qw.orderBy(true, false, "create_time");
			}
			IPage<Role> pageData = roleSerivce.page(page, qw);
			return  ResponseObj.success(pageData);
		} catch (Exception e) {
			log.error("查询错误 ", e);
			return  ResponseObj.badRequest();
		}

	}

	@ApiOperation(value = "添加或更新数据")
	@PostMapping("saveOrUpdateData")
	public ResponseObj<Object> saveOrUpdateData(
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "status", required = false) Integer status,
			@RequestParam(name = "descn", required = false) String descn) {
		try {
			
			Role role = new Role();
			role.setName(name);
			role.setStatus(status);
			role.setDescn(descn);
			if(id==null) {
				role.setCreateTime(new Date());
			}else {
				role.setId(id);
				role.setUpdateTime(new Date());
			}
			
			boolean saveOrUpdate = roleSerivce.saveOrUpdate(role);
//			List<Integer> roleUserIds = roleSerivce.getRoleUserIds(id);
			
			if(saveOrUpdate) {
				return ResponseObj.success();
			}else {
				return new ResponseObj<Object>(203, "数据保存失败！");
			}
		} catch (Exception e) {
			log.error("操作错误 ", e);
			return ResponseObj.badRequest();
		}
	}

	@ApiOperation(value = "查询所有角色")
	@GetMapping("selectAll")
	public ResponseObj<Object> selectAll() {
		try {
			
			QueryWrapper<Role> qw = new QueryWrapper<Role>();
			qw.eq("is_del", 0);
			qw.orderByAsc("name");
			List<Role> list = roleSerivce.list(qw);
			
			return ResponseObj.success(list);
		} catch (Exception e) {
			log.error("操作错误 ", e);
			return ResponseObj.badRequest();
		}
	}
	
	@ApiOperation(value = "删除角色")
	@PostMapping("deleteByIds")
	public ResponseObj<Object> deleteByIds(@RequestParam(name = "ids", required = false) String ids) {
		try {
			
			List<Integer> roleIds = new ArrayList<Integer>();
			if(StrUtil.isNotBlank(ids)) {
				String[] split = ids.split(",");
				for (String rid : split) {
					roleIds.add(Integer.parseInt(rid));
				}
			}
			if(roleSerivce.deleteRoleByIds(roleIds)) {
				return ResponseObj.success();
			}else {
				return ResponseObj.badRequest("删除角色失败");
			}
		} catch (Exception e) {
			log.error("操作错误 ", e);
			return ResponseObj.badRequest();
		}
	}
	
}

