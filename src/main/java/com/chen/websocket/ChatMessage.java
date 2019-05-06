package com.chen.websocket;

/**
 * 消息载体
 */
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class ChatMessage {

    private String content;
    private String from;
    private String to;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
    
}