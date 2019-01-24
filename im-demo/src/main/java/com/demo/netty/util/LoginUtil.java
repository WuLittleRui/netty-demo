package com.demo.netty.util;

import com.demo.netty.protocal.common.Attributes;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {
	/**
	 * 标记登录成功
	 * @param channel
	 */
	public static void markAsLogin(Channel channel) {
		channel.attr(Attributes.LOGIN).set(true);
	}
	
	public static boolean hasLogin(Channel channel) {
		Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
		
		return loginAttr.get() != null;
	}
}
