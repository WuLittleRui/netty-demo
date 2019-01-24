package com.demo.netty.protocal.common.response;

import static com.demo.netty.protocal.common.Command.*;

import com.demo.netty.protocal.common.request.Packet;

public class LoginResponsePacket extends Packet {
	private boolean success;
	private String reason;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public Byte getCommand() {
		return LOGIN_RESPONSE;
	}

}
