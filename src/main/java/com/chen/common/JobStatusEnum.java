package com.chen.common;

public enum JobStatusEnum {

	WATTING(0,"未运行"),
	RUNNING(1,"运行中"),
	SUCCESS(2,"运行结束"),
	FAILED(3,"运行失败");
	
	private Integer value;
	private String name;
	
	private JobStatusEnum(Integer value, String name) {
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
	
	public static JobStatusEnum getJobStatus(Integer value) {
		try {
			for (JobStatusEnum item : JobStatusEnum.values()) { 
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
