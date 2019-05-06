package com.chen.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.common.ResponseObj;
import com.chen.common.ReturnData;
import com.chen.user.pojo.User;
import com.chen.user.service.IUserService;
import com.chen.user.vo.LoginUserVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author chenwenchao
 * @since 2019-01-17
 */
@RestController
@RequestMapping("/user")
//@RolesAllowed("ROLE_AMDIN")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired	
	private IUserService userService; 
	
	@RequestMapping(value = "/getMessage", method = RequestMethod.GET)
	public ResponseObj getMessage() {
		return new ResponseObj(200, "您拥有用户权限，可以获得该接口的信息！");
	}

	@ApiOperation(value = "通过id查询")
	@GetMapping("selectById") //@RequestParam(name = "id", required = true) 
	public ResponseObj<Object> selectById(@ApiParam( value = "主键id", required = true, example = "123") @RequestParam(name = "id", required = true)  Integer id) {
		try {
			User byId = userService.getById(id);
			return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG, byId);
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}
	
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public ResponseObj selectAll() {
		try {
			
			List<User> list = userService.list();
			
			return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG, list);
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}

	
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public ResponseObj getUserInfo() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByUserName(username);
		Set<String> roles = userService.getRolesByUserName(username);
		LoginUserVo userVo = new LoginUserVo(user,roles);
		
		if(BeanUtil.isEmpty(user)) {
			return new ResponseObj(ReturnData.BAD_REQUEST_CODE,ReturnData.BAD_REQUEST_MSG);
		}
		return new ResponseObj(ReturnData.SUCCESS_CODE,ReturnData.SUCCESS_MSG,userVo);
	}
	
	@ApiOperation(value = "排序分页查询")
	@GetMapping("selectOrderBy")
	public ResponseObj<Object> selectOrderBy(@ApiParam(value = "页次，从1开始", required = false, example = "1") @RequestParam(name = "current", required = false) Integer current,
			@ApiParam(name = "pagesize", value = "每页大小", required = false, example = "10") @RequestParam(name = "pagesize", required = false) Integer pagesize,
			@ApiParam(name = "username", value = "用户名或真实名", required = false) @RequestParam(name = "username", required = false) String username,
			@ApiParam(name = "phone", value = "手机号", required = false) @RequestParam(name = "phone", required = false) String phone,
			@ApiParam(name = "gender", value = "性别", required = false) @RequestParam(name = "gender", required = false) String gender,
			@ApiParam(name = "status", value = "状态", required = false) @RequestParam(name = "status", required = false) String status,
			@ApiParam(name = "orderBy", value = "排序字段", required = false) @RequestParam(name = "orderBy", required = false) String orderBy,
			@ApiParam(name = "isAsc", value = "是否正序排序，默认正序", required = false) @RequestParam(name = "isAsc", required = false) Boolean isAsc) {

		try {
			current = current == null ? 0 : current;
			pagesize = pagesize == null ? 10 : pagesize;
			isAsc = isAsc == null ? true : isAsc;
			// 添加分页数据
			Page<User> page = new Page<User>(current, pagesize);
			// 添加排序字段
			QueryWrapper<User> qw = new QueryWrapper<User>();
			if(StrUtil.isNotEmpty(username)) {
				qw.and(i -> i.like("username", username).or().like("realname", username));
			}
			if(StrUtil.isNotEmpty(phone)) {
				qw.like("phone", phone);
			}
			if(StrUtil.isNotEmpty(gender)) {
				qw.eq("gender", gender);
			}
			if(StrUtil.isNotEmpty(status)) {
				qw.eq("status", status);
			}
			if (StrUtil.isNotEmpty(orderBy)) {
				qw.orderBy(true, isAsc, orderBy);
			}else {
				qw.orderBy(true, false, "create_time");
			}
			IPage<User> pageData = userService.page(page, qw);
			
			return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG, pageData);
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}

	}
	
	@RequestMapping(value = "/getUserRoleIds", method = RequestMethod.GET)
	public ResponseObj<Object> getUserRoleIds(@RequestParam("uid") Integer uid) {
		
		List<Integer> userRoleIds = userService.getUserRoleIds(uid);
		
		return new ResponseObj<Object>(ReturnData.SUCCESS_CODE,ReturnData.SUCCESS_MSG,userRoleIds);
	}
	
	@ApiOperation(value = "添加或更新数据")
	@PostMapping("saveOrUpdateData")
	public ResponseObj<Object> saveOrUpdateData(
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "realname", required = true) String realname,
			@RequestParam(name = "gender", required = false) Integer gender,
			@RequestParam(name = "status", required = false) Integer status,
			@RequestParam(name = "phone", required = false) String phone,
			@RequestParam(name = "roles", required = false) String roles,
			@RequestParam(name = "descn", required = false) String descn) {
		try {
			
			User user = new User();
			user.setId(id);
			user.setUsername(username);
			user.setRealname(realname);
			user.setGender(gender);
			user.setStatus(status);
			user.setPhone(phone);
			user.setDescn(descn);
			
			List<Integer> roleIds = new ArrayList<Integer>();
			if(StrUtil.isNotBlank(roles)) {
				String[] split = roles.split(",");
				for (String rid : split) {
					roleIds.add(Integer.parseInt(rid));
				}
			}
			
			boolean saveOrUpdateUser = userService.saveOrUpdateUser(user, roleIds);
			
			if(saveOrUpdateUser) {
				return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG);
			}else {
				return new ResponseObj<Object>(203, "数据保存失败！");
			}
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}
	
	@ApiOperation(value = "根据id删除")
	@PostMapping("deleteByIds")
	public ResponseObj<Object> deleteByIds(@ApiParam(value = "主键id", required = true, example = "123") @RequestParam(name = "ids", required = true) String ids) {
		try {
			List<Integer> uids = new ArrayList<Integer>();
			if(StrUtil.isNotBlank(ids)) {
				String[] split = ids.split(",");
				for (String rid : split) {
					uids.add(Integer.parseInt(rid));
				}
			}
			boolean removeById = userService.deleteUserByIds(uids);

			if (removeById) {
				return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG);
			} else {
				return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, "删除数据失败！");
			}
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}
	
	
	@ApiOperation(value = "修改用户信息")
	@PostMapping("updateUserInfo")
	public ResponseObj<Object> updateUserInfo(@ApiParam(value = "主键id", required = true, example = "123") @RequestParam(name = "id", required = true) Integer id,
			 @RequestParam(name = "realname", required = true) String realname,
			 @RequestParam(name = "phone", required = false) String phone,
			 @RequestParam(name = "email", required = false) String email,
			 @RequestParam(name = "gender", required = false) Integer gender,
			 @RequestParam(name = "descn", required = false) String descn) {
		try {

			User user = new User();
			user.setId(id);
			user.setRealname(realname);
			user.setPhone(phone);
			user.setEmail(email);
			user.setGender(gender);
			user.setDescn(descn);
			user.setUpdateTime(new Date());
			
			if (userService.updateById(user)) {
				return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG);
			} else {
				return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, "保存数据失败！");
			}
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}
	
	
	@ApiOperation(value = "修改密码")
	@PostMapping("updatePassword")
	public ResponseObj<Object> updatePassword(@ApiParam(value = "主键id", required = true, example = "123") @RequestParam(name = "id", required = true) Integer id,
			 @RequestParam(name = "oldPass", required = true) String oldPass,
			 @RequestParam(name = "newPass", required = true) String newPass) {
		try {

			User user = userService.getById(id);
			if(user==null) {
				return new ResponseObj<Object>(400, "参数错误！");
			}
			
			// BCryptPasswordEncoder 加密
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String oldPassEncode = encoder.encode(oldPass);
			if(!oldPassEncode.equals(user.getPassword())) {
				return new ResponseObj<Object>(400, "原密码错误！");
			}
			user.setPassword(encoder.encode(newPass));
			user.setUpdateTime(new Date());
			if (userService.updateById(user)) {
				return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG);
			} else {
				return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, "修改数据失败！");
			}
		} catch (Exception e) {
			log.error(ReturnData.BAD_REQUEST_MSG, e);
			return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, ReturnData.BAD_REQUEST_MSG);
		}
	}
	
}
