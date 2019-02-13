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
	/**
	 * 群聊请求
	 */
	public final static byte GROUP_REQUEST = 5;
	/**
	 * 群聊回复
	 */
	public final static byte GROUP_RESPONSE = 6;
	/**
	 * 加入群聊请求
	 */
	public final static byte JOIN_GROUP_REQUEST = 7;
	/**
	 * 加入群聊回复
	 */
	public final static byte JOIN_GROUP_RESPONSE = 8;
	/**
	 * 退出群聊请求
	 */
	public final static byte QUIT_GROUP_REQUEST = 9;
	/**
	 * 退出群聊回复
	 */
	public final static byte QUIT_GROUP_RESPONSE = 10;
}
