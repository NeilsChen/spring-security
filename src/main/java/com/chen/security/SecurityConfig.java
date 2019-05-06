package com.chen.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chen.user.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;  //  未登陆时返回 JSON 格式的数据给前端（否则为 html）

    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;    // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）

    @Autowired
    MyUserDetailService userDetailsService; // 自定义user

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter; // JWT 拦截器
    
    // 重写 authenticationManagerBean 方法，将authenticationManager添加到spring容器
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    // 注入 BCryptPasswordEncoder 密码加密方式
    @Bean()
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
//	内存用户验证
//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//      auth
//          .inMemoryAuthentication()
//              .withUser("user").password("password").roles("USER");
//  }
    
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable() // Cross-Site Request Forgery - 跨站请求伪造
        	.cors().and() // Cross Origin Resourse-Sharing - 跨站资源共享
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用 JWT，关闭token
            .and()
            	.authorizeRequests()
                .antMatchers("/", "/home", "/login","/websocket").permitAll() // 允许直接访问的请求
                .antMatchers("/logout").denyAll() // 禁止访问的请求
                .antMatchers("/hello").hasRole("ADMIN")
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 过滤post请求前置option请求
                .anyRequest() // 除以上过滤的，其他请求都需要以下鉴权
//              .access("@rbacauthorityservice.hasPermission(request,authentication)") // RBAC 动态 url 认证
                .authenticated() // 登录认证
            .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint)
            .and()
                .addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);
            
     	// 禁用缓存
     	http.headers().cacheControl();
     	
    }

	
	// 过滤请求
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
        		"/v2/api-docs",
                "/swagger-resources/**",
                "/images/**",
                "/webjars/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/druid",
                "/druid/**",
                "/avatar/*"
                
        );
    }
	
}
