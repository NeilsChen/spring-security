package com.chen.common;

public enum UserStatusEnum {

	ENABLE(0,"正常"),
	DISABLE(1,"停用"),
	LOCKED(2,"锁定")	;
	
	private Integer value;
	private String name;
	
	private UserStatusEnum(Integer value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static UserStatusEnum getUserStatus(Integer value) {
		try {
			for (UserStatusEnum item : UserStatusEnum.values()) { 
	            if (item.getValue() == value) {  
	                return item;  
	            }  
	        }  	     
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
