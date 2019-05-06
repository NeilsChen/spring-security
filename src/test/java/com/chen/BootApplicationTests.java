package com.chen;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.chen.util.RedisUtil;

@RunWith(SpringRunner.class)
// 项目中添加websocket功能后 junit测试时 增加以下设置，不然websocket报错
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BootApplicationTests {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Test
	public void contextLoads() {
		
//		RedisUtil.set("aaa", "zxcasadvfsd");
		Set<String> allKeys = RedisUtil.allKeys("token:*");
		for (String key : allKeys) {
			Map<Object, Object> hmget = RedisUtil.hmget(key);
			System.out.println(hmget);
		}
//		Map<Object, Object> hmget = RedisUtil.hmget("token:chen");
//		System.out.println(hmget);
	}

	
	@Test
	public void testPwdEncode() {
		System.out.println(passwordEncoder.encode("123456"));
	}
}
