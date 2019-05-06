package com.chen.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.chen.common.ResponseObj;
import com.chen.common.ReturnData;

import cn.hutool.json.JSONUtil;

@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

    	ResponseObj<Object> responseObj = new ResponseObj<>(ReturnData.FORBIDDEN_CODE, ReturnData.FORBIDDEN_MSG);
    	
    	httpServletResponse.setCharacterEncoding("utf-8");
    	httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(JSONUtil.toJsonStr(responseObj));
        writer.close();
    }
}
