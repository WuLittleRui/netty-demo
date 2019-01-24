package com.demo.netty.protocal.common.request;

public abstract class Packet {
	/**
	 * 协议版本号
	 */
	private Byte version = 1;
	
	public Byte getVersion() {
		return version;
	}

	public void setVersion(Byte version) {
		this.version = version;
	}
	public abstract Byte getCommand();
}
