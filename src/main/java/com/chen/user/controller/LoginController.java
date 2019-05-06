package com.chen.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chen.annotation.ServiceLimit;
import com.chen.annotation.ServiceLimit.LimitType;
import com.chen.common.ResponseObj;
import com.chen.common.ReturnData;
import com.chen.user.service.LoginService;
import com.chen.user.service.MyUserDetailService;
import com.chen.util.HttpUtils;
import com.chen.util.JwtTokenUtil;

import cn.hutool.core.date.DateUtil;

@RestController
public class LoginController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	MyUserDetailService userDetailsService;

	@Autowired
	LoginService loginService;
	
//	@Autowired  
//	SessionRegistry sessionRegistry;
	
	@ServiceLimit(limitType=LimitType.IP, period=5, count=5)
	@GetMapping(value = { "/", "/home" })
	public ResponseObj<Object> home() {
		return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, "Home!");
	}

	@GetMapping(value = "/haha")
	public ResponseObj<Object> haha() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	System.out.println("name:" + name);
		return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, "haha!");
	}
	
	
	@GetMapping("/hello")
	public ResponseObj<Object> hello() {
		return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, "Hello World !");
	}

	@ServiceLimit(limitType=LimitType.IP, period=5, count=5)
	@PostMapping("/login")
	public ResponseObj<Object> login(HttpServletRequest request, String username, String password) {
		String ipAddress = HttpUtils.getIpAddress(request);
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			Authentication authentication = authenticationManager.authenticate(upToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String generateToken = JwtTokenUtil.generateToken(username);
			Map<String,String> userInfo = new HashMap<String,String>();
			userInfo.put("host", ipAddress);
			String now = DateUtil.now();
			userInfo.put("login_time", now);
			userInfo.put("last_time", now);
			userInfo.put("token", generateToken);
			
			loginService.clearLogin(); // 禁止多端登录  登录前清除已登录信息
			boolean saveUserInfo = loginService.saveUserToRedis(username, userInfo);
			if(saveUserInfo) {
				return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, "WelCome !!!", generateToken);
			}else {
				return new ResponseObj<Object>(ReturnData.BAD_REQUEST_CODE, "保存用户登录信息到redis错误！");
			}
		} catch (AuthenticationException e) {
			return new ResponseObj<Object>(ReturnData.UNAUTHORIZED_CODE, "登录失败，用户名或密码错误！");
		}
	}

	@GetMapping("/onlineUser")
	public ResponseObj<Object> onlineUser() {
//		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
//		System.out.println("allPrincipals:"+allPrincipals);
		List list = loginService.listOnlineUser();
		System.out.println("list:"+list);
		return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, ReturnData.SUCCESS_MSG,list);
	}
	
	@PostMapping("/kickoutUser")
	public ResponseObj<Object> kickoutUser(@RequestParam("username") String username) {
		loginService.kickoutUser(username);
		return ResponseObj.success();
	}
	
	@PostMapping("/doLogout")
	public ResponseObj<Object> logout() {
		
		loginService.clearLogin();
		return new ResponseObj<Object>(ReturnData.SUCCESS_CODE, "GoodBye !!!");

	}
}
