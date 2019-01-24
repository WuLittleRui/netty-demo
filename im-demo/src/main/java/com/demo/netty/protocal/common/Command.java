package com.demo.netty.protocal.common;

/**
 * 指令集
 * 
 * @author wurui
 *
 */
public class Command {
	/**
	 * 登录请求指令
	 */
	public final static byte LOGIN_REQUEST = 1;
	/**
	 * 登录服务器请求回复
	 */
	public final static byte LOGIN_RESPONSE = 2;
	/**
	 * 消息请求
	 */
	public final static byte MESSAGE_REQUEST = 3;
	/**
	 * 消息服务器回复
	 */
	public final static byte MESSAGE_RESPONSE = 4;

}
