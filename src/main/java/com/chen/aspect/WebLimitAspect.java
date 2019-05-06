package com.chen.aspect;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chen.annotation.ServiceLimit;
import com.chen.annotation.ServiceLimit.LimitType;
import com.chen.util.HttpUtils;
import com.google.common.collect.ImmutableList;

/**
 * 请求限流aop
 */
@Aspect
@Configuration
public class WebLimitAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisTemplate<String, Serializable> limitRedisTemplate;

//	@Autowired
//	public WebLimitAspect(RedisTemplate<String, Serializable> limitRedisTemplate) {
//		this.limitRedisTemplate = limitRedisTemplate;
//	}

	 @Around("execution(public * *(..)) && @annotation(com.chen.annotation.ServiceLimit)")
	    public Object interceptor(ProceedingJoinPoint pjp) {
	        MethodSignature signature = (MethodSignature) pjp.getSignature();
	        Method method = signature.getMethod();
	        ServiceLimit limitAnnotation = method.getAnnotation(ServiceLimit.class);
	        LimitType limitType = limitAnnotation.limitType();
	        String key;
	        int limitPeriod = limitAnnotation.period();
	        int limitCount = limitAnnotation.count();
	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	        switch (limitType) {
	            case IP:
	                key = HttpUtils.getIpAddress(request);
	                break;
	            case CUSTOMER:
	                // TODO 如果此处想根据表达式或者一些规则生成 请看 一起来学Spring Boot | 第二十三篇：轻松搞定重复提交（分布式锁）
	                key = limitAnnotation.key();
	                break;
	            default:
	                key = StringUtils.upperCase(method.getName());
	        }
	        
	        System.out.println(key);
	        
	        ImmutableList<String> keys = ImmutableList.of( key);
	        try {
	            String luaScript = buildLuaScript();
	            RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
	            Number count = limitRedisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
	            logger.info("Access try limitPeriod is {} count is {} for  key = {}", limitPeriod, count, key);
	            if (count != null && count.intValue() <= limitCount) {
	                return pjp.proceed();
	            } else {
	                throw new RuntimeException("服务器繁忙，请稍后再试！");
	            }
	        } catch (Throwable e) {
	            if (e instanceof RuntimeException) {
	                throw new RuntimeException(e.getLocalizedMessage());
	            }
	            throw new RuntimeException("server exception");
	        }
	    }
	


	/**
	 * 限流 脚本
	 *
	 * @return lua脚本
	 */
	public static String buildLuaScript() {
		StringBuilder lua = new StringBuilder();
		lua.append("local c");
		lua.append("\nc = redis.call('get',KEYS[1])");
		// 调用不超过最大值，则直接返回
		lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
		lua.append("\nreturn c;");
		lua.append("\nend");
		// 执行计算器自加
		lua.append("\nc = redis.call('incr',KEYS[1])");
		lua.append("\nif tonumber(c) == 1 then");
		// 从第一次调用开始限流，设置对应键值的过期
		lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
		lua.append("\nend");
		lua.append("\nreturn c;");
		return lua.toString();
	}

	
}
