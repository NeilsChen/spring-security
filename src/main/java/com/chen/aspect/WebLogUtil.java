package com.chen.aspect;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.ui.Model;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;

/**
 * 日志接口
 * @author gx
 * @version 2016-07-13
 */
public class WebLogUtil {
	
	/**
	 * 打印接口调用起始信息，返回起始时间戳
	 * @param logger 日志对象
	 * @param interfaceName 接口名称
	 * @return 起始时间戳
	 */
	public static long start(Logger logger, String interfaceName) {
		logger.info("*********************************************************************************************************************");
		logger.info(interfaceName + " start");
		return DateUtil.current(false);
	}
	
	/**
	 * 打印接口调用完成信息，计算接口调用耗时
	 * @param logger 日志对象
	 * @param interfaceName 接口名称
	 * @param interfaceStart 起始时间戳
	 * @param contextMap 数据上下文
	 */
	public static void end(Logger logger, String interfaceName, long interfaceStart, HashMap<String, Object> contextMap) {
		long interfaceEnd = DateUtil.current(false);
		logger.info("dataMap=" + JSONUtil.toJsonStr(contextMap));
		logger.info("调用接口耗时：" + (interfaceEnd - interfaceStart) + "ms");
		logger.info(interfaceName + " end");
		logger.info("*********************************************************************************************************************\n");
	}

	/**
	 * 打印接口调用完成信息，计算接口调用耗时
	 * @param logger 日志对象
	 * @param interfaceName 接口名称
	 * @param interfaceStart 起始时间戳
	 * @param model 数据上下文
	 */
	public static void end(Logger logger, String interfaceName, long interfaceStart, Model model) {
		long interfaceEnd = DateUtil.current(false);
		logger.info("model=" + JSONUtil.toJsonStr(model));
		logger.info("调用接口耗时：" + (interfaceEnd - interfaceStart) + "ms");
		logger.info(interfaceName + " end");
		logger.info("*********************************************************************************************************************\n");
	}

	/**
	 * 打印接口调用完成信息，计算接口调用耗时
	 * @param logger 日志对象
	 * @param interfaceName 接口名称
	 * @param interfaceStart 起始时间戳
	 */
	public static void end(Logger logger, String interfaceName, long interfaceStart) {
		long interfaceEnd = DateUtil.current(false);
		logger.info("调用接口耗时：" + (interfaceEnd - interfaceStart) + "ms");
		logger.info(interfaceName + " end");
		logger.info("*********************************************************************************************************************\n");
	}
	
	/**
	 * 打印接口调用完成信息，计算接口调用耗时
	 * @param logger 日志对象
	 * @param request Http请求
	 */
	public static void printParams(Logger logger, HttpServletRequest request) {
		logger.info("params in request:"); 
		Enumeration<String> keys = request.getParameterNames(); 
		while(keys.hasMoreElements()) { 
		    String name = keys.nextElement(); 
		    logger.info(name + " = " + request.getParameter(name) ); 
		} 		
		logger.info("***********************************************");
	}
	
	/**
	 * 打印接口调用完成信息，计算接口调用耗时
	 * @param logger 日志对象
	 * @param request Http请求
	 */
	public static void printHeaderParams(Logger logger, HttpServletRequest request) {
		logger.info("params in header:"); 
		Enumeration<String> keys = request.getHeaderNames();
		while(keys.hasMoreElements()) { 
			String name = keys.nextElement(); 
			logger.info(name + " = " + request.getHeader(name) ); 
//			if ("user_id".equals(name)) {
//				
//			}
//			switch (name) {
//			case "user_id":
//				logger.info(name + " = " + request.getHeader(name) ); 
//				break;
//				
//			case "token":
//				logger.info(name + " = " + request.getHeader(name) ); 
//				break;
//
//			default:
//				break;
//			}
		} 	
		logger.info("***********************************************");
	}
}
