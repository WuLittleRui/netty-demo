package com.demo.netty.protocal.serialize;

import com.demo.netty.protocal.serialize.impl.JSONSerializer;

public interface Serializer {
	byte JSON_SERIALIZER = 1;
	
	/**
	 * 序列化算法
	 * 获取具体的序列化算法标识
	 */
	byte getSerializerAlgorithm();

	/**
	 * java 对象转换成二进制
	 */
	byte[] serialize(Object object);

	/**
	 * 二进制转换成 java 对象
	 * 阿里巴巴的 fastjson 作为序列化框架
	 */
	<T> T deserialize(Class<T> clazz, byte[] bytes);

    Serializer DEFAULT = new JSONSerializer();
}
