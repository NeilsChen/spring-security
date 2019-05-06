package com.chen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer  {

	@Value("${filepath.avatar}")
	private String avatorPath;
	
	@Value("${filepath.blog}")
	private String blogFilePath;

	// @Override
	// public void addInterceptors(InterceptorRegistry registry) {
	// registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
	// .excludePathPatterns("/index.html","/","/user/login","/static/**");
	// }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/blog/**").addResourceLocations("file:"+blogFilePath);
		registry.addResourceHandler("/avatar/**").addResourceLocations("file:"+avatorPath);
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		
//		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
