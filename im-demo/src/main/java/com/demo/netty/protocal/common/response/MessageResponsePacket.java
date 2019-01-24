package com.demo.netty.protocal.common.response;

import com.demo.netty.protocal.common.request.Packet;
import static com.demo.netty.protocal.common.Command.MESSAGE_RESPONSE;

public class MessageResponsePacket extends Packet {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Byte getCommand() {
		return MESSAGE_RESPONSE;
	}

}
