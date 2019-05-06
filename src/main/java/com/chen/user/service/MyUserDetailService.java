package com.chen.user.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chen.common.UserStatusEnum;
import com.chen.user.pojo.MyUserDetails;
import com.chen.user.pojo.User;

@Service
public class MyUserDetailService  implements UserDetailsService {

	@Autowired
	private IUserService userService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 //构建用户信息的逻辑(取数据库/LDAP等用户信息)
		User user = userService.getUserByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("找不到该用户！");
		}
		if(user.getStatus() != UserStatusEnum.ENABLE.getValue())
        {
			log.debug("用户被禁用或锁定，无法登陆 用户名:{}", username);
            throw new UsernameNotFoundException("用户被禁用或锁定！");
        }
		
        MyUserDetails userInfo = new MyUserDetails();
        userInfo.setUsername(username);
        
//        userInfo.setPassword(new BCryptPasswordEncoder().encode("123"));
        
        userInfo.setPassword(user.getPassword());
        
        Set<GrantedAuthority> authoritiesSet = new HashSet<GrantedAuthority>();
        Set<String> roles = userService.getRolesByUserName(username);
        if (roles != null) {
            //设置角色名称
            for (String role : roles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                authoritiesSet.add(authority);
            }
        }
		
//        // 角色使用  ROLE_ 开头  在securityConfig 的访问路径 配置角色时自动带上ROLE_ 
//        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
//        authoritiesSet.add(authority);
        
        userInfo.setAuthorities(authoritiesSet);

        return userInfo;
	}

}
