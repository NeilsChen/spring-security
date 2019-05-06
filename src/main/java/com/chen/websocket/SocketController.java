package com.chen.websocket;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

	/**
	 * MessageMapping  接收消息路径  客户端访问路径为 前缀加 /sendPublicMessage 即 /app/sendPublicMessage <br/>
	 * SendTo 推送 公共消息路径  客户端设置监听路径 /topic/public
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/sendPublicMessage")
	@SendTo("/topic/public")
	public ChatMessage sendPublicMessage(ChatMessage message) throws Exception {
		// 模拟延时，以便测试客户端是否在异步工作
		Thread.sleep(1000);
		return message;
	}

	/**
	 * MessageMapping  接收消息路径  客户端访问路径为 前缀加 /sendPublicMessage 即 /app/sendPrivateMessage <br/>
	 * SendToUser 推送 用户消息路径  客户端设置监听路径加上前缀 默认user /user/topic/public
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/sendPrivateMessage")
	@SendToUser("/topic/chat")
	public ChatMessage sendPrivateMessage(ChatMessage message,Principal principal) throws Exception {
		// 模拟延时，以便测试客户端是否在异步工作
		System.out.println("principal.getName():"+principal.getName());
		Thread.sleep(1000);
		return message;
	}
	
}
