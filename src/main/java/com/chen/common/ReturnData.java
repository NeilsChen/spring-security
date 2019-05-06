package com.chen.common;

public class ReturnData {

	/**
	 * 200
	 */
	public final static int SUCCESS_CODE = 200;
	
	/**
	 * 200 操作成功
	 */
	public final static String SUCCESS_MSG = "操作成功";

	/**
	 * 400
	 */
	public final static int BAD_REQUEST_CODE = 400;
	
	/**
	 * 400 请求错误
	 */
	public final static String BAD_REQUEST_MSG = "请求错误";
	
	/**
	 * 401
	 */
	public final static int UNAUTHORIZED_CODE = 401;
	
	/**
	 * 401 请求未认证
	 */
	public final static String UNAUTHORIZED_MSG = "请求未认证";
	
	
	/**
	 * 402 未登录
	 */
	public final static int NOT_LOGIN_CODE = 402;
	
	/**
	 *  未登录
	 */
	public final static String NOT_LOGIN_MSG = "未登录";
	
	/**
	 * 403
	 */
	public final static int FORBIDDEN_CODE = 403;
	
	/**
	 * 403 请求拒绝
	 */
	public final static String FORBIDDEN_MSG = "请求拒绝";
	
	/**
	 * 404
	 */
	public final static int NOT_FOUND_CODE = 404;
	
	/**
	 * 404 未找到请求资源
	 */
	public final static String NOT_FOUND_MSG = "未找到请求资源";
	
	/**
	 * 500
	 */
	public final static int SERVER_ERROR_CODE = 500;
	
	/**
	 * 500 服务器错误
	 */
	public final static String SERVER_ERROR_MSG = "服务器错误";
}
