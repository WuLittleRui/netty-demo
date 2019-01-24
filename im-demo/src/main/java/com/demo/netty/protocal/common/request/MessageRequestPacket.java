package com.demo.netty.protocal.common.request;

import static com.demo.netty.protocal.common.Command.MESSAGE_REQUEST;

public class MessageRequestPacket extends Packet {
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Byte getCommand() {
		return MESSAGE_REQUEST;
	}

}
