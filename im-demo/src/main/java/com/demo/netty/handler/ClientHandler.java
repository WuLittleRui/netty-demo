package com.demo.netty.handler;

import java.util.Date;
import java.util.UUID;

import com.demo.netty.protocal.common.PacketCodec;
import com.demo.netty.protocal.common.request.LoginRequestPacket;
import com.demo.netty.protocal.common.request.Packet;
import com.demo.netty.protocal.common.response.LoginResponsePacket;
import com.demo.netty.protocal.common.response.MessageResponsePacket;
import com.demo.netty.util.LoginUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(new Date() + ": 客户端登录");

		// 创建登录对象
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
		loginRequestPacket.setUserId(UUID.randomUUID().toString());
		loginRequestPacket.setPassword("pwd");
		loginRequestPacket.setUsername("flash");

		// 编码
		ByteBuf buffer = PacketCodec.getInstance().encode(ctx.alloc(), loginRequestPacket);

		ctx.channel().writeAndFlush(buffer);

		System.out.println(new Date() + "客户端发送成功");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf requestBuffer = (ByteBuf) msg;

		Packet packet = PacketCodec.getInstance().decode(requestBuffer);
		if (packet instanceof LoginResponsePacket) {
			LoginResponsePacket responsePacket = (LoginResponsePacket) packet;

			if (responsePacket.isSuccess()) {
				LoginUtil.markAsLogin(ctx.channel());
				System.out.println(new Date() + ": 客户端登录成功");
			} else {
				System.out.println(new Date() + ": 客户端登录失败，原因：" + responsePacket.getReason());
			}
		} else if (packet instanceof MessageResponsePacket) {
			MessageResponsePacket messageRequestPacket = (MessageResponsePacket) packet;
			System.out.println(new Date() + ": 收到服务端的消息: " + messageRequestPacket.getMessage());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + ":" + cause.getMessage());
		ctx.close();
	}
}
