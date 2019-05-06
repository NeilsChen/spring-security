package com.chen.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.chen.common.ResponseObj;
import com.chen.common.ReturnData;

import cn.hutool.json.JSONUtil;

@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

		ResponseObj<Object> responseObj = new ResponseObj<>(ReturnData.NOT_LOGIN_CODE,  "未登录或已过期，请重新登录！");

		httpServletResponse.setCharacterEncoding("utf-8");
		httpServletResponse.setContentType("application/json; charset=utf-8");
		PrintWriter writer = httpServletResponse.getWriter();
		writer.write(JSONUtil.toJsonStr(responseObj));
		writer.close();
	}
}
