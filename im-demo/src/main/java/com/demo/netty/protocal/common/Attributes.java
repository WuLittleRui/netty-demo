package com.demo.netty.protocal.common;

import com.demo.netty.session.Session;

import io.netty.util.AttributeKey;

public interface Attributes {
	AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
	
	AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
