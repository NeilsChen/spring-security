package com.chen.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chen.util.JwtTokenUtil;
import com.chen.util.RedisUtil;

import cn.hutool.core.date.DateUtil;

/**
 * 管理存储在redis中的用户登录信息
 */
@Service
public class LoginService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MyUserDetailService userDetailsService;

	@Value("${spring.redis.token.expire-time}")
	private Long expireTime;

	public boolean saveUserToRedis(String username, Map map) {

		return RedisUtil.hmset(JwtTokenUtil.KEY_PREFIX + username, map, expireTime);

	}

	public boolean isLogin(String username, String token) {
		return token.equals(RedisUtil.hget(JwtTokenUtil.KEY_PREFIX + username, "token"));
	}

	public void updateUserInfo(String username) {
		RedisUtil.expire(JwtTokenUtil.KEY_PREFIX + username, expireTime); // 更新redis 中token过期时间
		RedisUtil.hset(JwtTokenUtil.KEY_PREFIX + username, "last_time", DateUtil.now()); // 更新最后访问时间
	}

	public List listOnlineUser() {
		List list = new ArrayList();
		Set<String> allKeys = RedisUtil.allKeys(JwtTokenUtil.KEY_PREFIX + "*");
		for (String key : allKeys) {
			Map<Object, Object> hmget = RedisUtil.hmget(key);
			hmget.put("username", key.substring(JwtTokenUtil.KEY_PREFIX.length()));
			hmget.put("expire_time", RedisUtil.getExpire(key));
			list.add(hmget);
		}
		return list;
	}

	public void kickoutUser(String username) {
		RedisUtil.del(JwtTokenUtil.KEY_PREFIX + username);
	}
	
	public void clearLogin() {
		try {
			if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
				
				String name = SecurityContextHolder.getContext().getAuthentication().getName();
				RedisUtil.del(JwtTokenUtil.KEY_PREFIX + name);
				SecurityContextHolder.clearContext(); // 清空安全上下文
			}
		} catch (Exception e) {
			log.error("清除登录信息失败："+e.getMessage());
		}

	}

	

}
