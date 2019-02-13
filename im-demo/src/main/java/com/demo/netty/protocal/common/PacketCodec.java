package com.demo.netty.protocal.common;

import static com.demo.netty.protocal.common.Command.*;

import java.util.HashMap;
import java.util.Map;

import com.demo.netty.protocal.common.packet.Packet;
import com.demo.netty.protocal.common.packet.request.CreateGroupRequestPacket;
import com.demo.netty.protocal.common.packet.request.JoinGroupRequestPacket;
import com.demo.netty.protocal.common.packet.request.LoginRequestPacket;
import com.demo.netty.protocal.common.packet.request.MessageRequestPacket;
import com.demo.netty.protocal.common.packet.response.CreateGroupResponsePacket;
import com.demo.netty.protocal.common.packet.response.JoinGroupResponsePacket;
import com.demo.netty.protocal.common.packet.response.LoginResponsePacket;
import com.demo.netty.protocal.common.packet.response.MessageResponsePacket;
import com.demo.netty.protocal.serialize.Serializer;
import com.demo.netty.protocal.serialize.impl.JSONSerializer;

import io.netty.buffer.ByteBuf;

public class PacketCodec {
	public final static int MAGIC_NUMBER = 0x12345678;
	private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
	private static final Map<Byte, Serializer> serializerMap;

	public static final PacketCodec INSTANCE = new PacketCodec();

	// 静态代码块、修饰变量、修饰方法、静态类、静态导包（指导入目的包的静态资源）
	static {
		packetTypeMap = new HashMap<>();
		packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
		packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
		packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
		packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
		packetTypeMap.put(GROUP_REQUEST, CreateGroupRequestPacket.class);
		packetTypeMap.put(GROUP_RESPONSE, CreateGroupResponsePacket.class);
		packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
		packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);

		serializerMap = new HashMap<>();
		Serializer serializer = new JSONSerializer();
		serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
	}

	public ByteBuf encode(ByteBuf buffer, Packet packet) {
		byte[] bytes = Serializer.DEFAULT.serialize(packet);

		// 1.魔数 检查是否是本系统的自定义协议
		buffer.writeInt(MAGIC_NUMBER);
		// 2.版本号
		buffer.writeByte(packet.getVersion());
		// 3.序列化算法
		buffer.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
		// 4.指令
		buffer.writeByte(packet.getCommand());
		// 5.数据长度
		buffer.writeInt(bytes.length);
		// 6.数据
		buffer.writeBytes(bytes);

		return buffer;
	}

	public Packet decode(ByteBuf byteBuf) {
		// 1.跳过魔数
		byteBuf.skipBytes(4);
		// 2.跳过版本号
		byteBuf.skipBytes(1);
		// 3.获取序列化算法
		byte serializeAlgorithm = byteBuf.readByte();
		// 4.指令
		byte command = byteBuf.readByte();
		// 5.数据长度
		int length = byteBuf.readInt();
		// 6.数据
		byte[] bytes = new byte[length];
		byteBuf.readBytes(bytes);

		Class<? extends Packet> requestType = getRequestType(command);
		Serializer serializer = getSerializer(serializeAlgorithm);

		if (requestType != null && serializer != null) {
			return serializer.deserialize(requestType, bytes);
		}

		return null;
	}

	private Serializer getSerializer(byte serializeAlgorithm) {

		return serializerMap.get(serializeAlgorithm);
	}

	private Class<? extends Packet> getRequestType(byte command) {

		return packetTypeMap.get(command);
	}
}
