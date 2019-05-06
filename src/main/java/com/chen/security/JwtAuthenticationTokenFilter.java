package com.chen.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chen.aspect.WebLogUtil;
import com.chen.user.service.LoginService;
import com.chen.user.service.MyUserDetailService;
import com.chen.util.JwtTokenUtil;

/**
 * 请求过滤器  验证用户登录信息 <br/>
 *  验证通过  在SecurityContextHolder.getContext() 中添加认证信息
 *  并更新登录用户信息
 */
@Component  
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MyUserDetailService userDetailsService;
	
	@Autowired
	LoginService loginService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		log.info("请求前处理  JwtAuthenticationTokenFilter  URL:" + request.getRequestURL().toString());
		
		WebLogUtil.printParams(log, request);// 打印body中的参数
		WebLogUtil.printHeaderParams(log, request);
		
		String authHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);

		if (authHeader != null && authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
			final String authToken = authHeader.substring(JwtTokenUtil.TOKEN_PREFIX.length()); // 请求头token
			
			// token 验证
			String username = JwtTokenUtil.parseToken(authToken);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (userDetails != null) {
					if(loginService.isLogin(username,authToken)) { // 验证redis中的token
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);
						loginService.updateUserInfo(username); // 更新redis中用户信息
						
					}
				}
			}
		}
		chain.doFilter(request, response);
	} 
}
