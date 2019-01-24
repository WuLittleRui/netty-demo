package com.demo.netty.protocal.serialize.impl;

import com.alibaba.fastjson.JSONObject;
import com.demo.netty.protocal.serialize.Serializer;

public class JSONSerializer implements Serializer {

	@Override
	public byte getSerializerAlgorithm() {
		return JSON_SERIALIZER;
	}

	@Override
	public byte[] serialize(Object object) {
		return JSONObject.toJSONBytes(object);
	}

	@Override
	public <T> T deserialize(Class<T> clazz, byte[] bytes) {
		return JSONObject.parseObject(bytes, clazz);
	}

}
