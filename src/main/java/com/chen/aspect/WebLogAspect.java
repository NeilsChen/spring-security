package com.chen.aspect;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.json.JSONUtil;

@Aspect
@Component
public class WebLogAspect {
    
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
//	* com.xyz.service.AccountService.*(..)  execution(public * com.chen.*.controller..*.*(..))  or
    @Pointcut("execution(public * com.chen.*.controller..*.*(..))")
    public void webPackageLog(){
    }
    
//    @Pointcut("execution(public * com.chen.common.BaseController.*(..))")
    public void webClassLog(){
    }
    
    
//    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            log.info("name:{},value:{}", name, request.getParameter(name));
        }        
    }
    
//    @Around("webPackageLog() || webClassLog()")
    @Around("webPackageLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {  
        //包围方法前
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUri = request.getRequestURI();  
		long interStart = WebLogUtil.start(log, request.getMethod()+"   "+requestUri);
		log.info("file.encoding=" + System.getProperty("file.encoding"));
		WebLogUtil.printParams(log, request);// 打印body中的参数
		WebLogUtil.printHeaderParams(log, request);
        Object[] args =  pjp.getArgs();     
        for (int i = 0; i < args.length; i++) {
        	if(args[i] instanceof Model){
				 log.info("Model="+args[i]);
			}
		}
//        log.info("解码前：args[0]=" + args[0]);
//        args[0] = URLDecoder.decode(args[0].toString(), "utf-8");// 对data进行URL解码
//        log.info("解码后：args[0]=" + args[0]);
        Object result = pjp.proceed(args);
        log.info("response="+JSONUtil.toJsonStr(result));
        
        //包围方法后
        WebLogUtil.end(log, requestUri, interStart);
        return result;
    } 
    
    
//    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
    }
}