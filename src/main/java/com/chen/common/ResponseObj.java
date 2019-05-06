package com.chen.common;

import javax.servlet.http.HttpServletResponse;

public class ResponseObj<T> {

	public int code;

	private String message;

	private T data;

	public ResponseObj() {
	}

	public ResponseObj(int code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
	}

	public ResponseObj(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static ResponseObj<Object> success(){
		return new ResponseObj<Object>(HttpServletResponse.SC_OK,"操作成功！");
	}
	
	public static ResponseObj<Object> success(Object data){
		return new ResponseObj<Object>(HttpServletResponse.SC_OK,"操作成功！",data);
	}
	
	public static ResponseObj<Object> badRequest(){
		return new ResponseObj<Object>(HttpServletResponse.SC_BAD_REQUEST,"操作失败！");
	}
	
	public static ResponseObj<Object> badRequest(String msg){
		return new ResponseObj<Object>(HttpServletResponse.SC_BAD_REQUEST,msg);
	}
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseObj [code=" + code + ", message=" + message + ", data=" + data + "]";
	}

}
