package com.chen.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求限流
 */
@Target({ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Inherited
@Documented    
public @interface ServiceLimit {

	/**
     * 资源的key
     *
     * @return String
     */
    String key() default "";

    /**
     * 给定的时间段
     * 单位秒
     *
     * @return int
     */
    int period() default 1;

    /**
     * 最多的访问限制次数
     *
     * @return int
     */
    int count() default 10;

	/**
	* 类型
	*
	* @return LimitType
	*/
	LimitType limitType() default LimitType.CUSTOMER;
	
	/**
	 * 限流类型    根据 key或者ip进行限流
	 */
	public enum LimitType {
		/**
		 * 自定义key
		 */
		CUSTOMER,
		/**
		 * 请求ip
		 */
		IP
	}

}
