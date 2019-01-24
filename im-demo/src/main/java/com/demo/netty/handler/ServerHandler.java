package com.demo.netty.handler;

import java.util.Date;

import com.demo.netty.protocal.common.PacketCodec;
import com.demo.netty.protocal.common.request.LoginRequestPacket;
import com.demo.netty.protocal.common.request.MessageRequestPacket;
import com.demo.netty.protocal.common.request.Packet;
import com.demo.netty.protocal.common.response.LoginResponsePacket;
import com.demo.netty.protocal.common.response.MessageResponsePacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(new Date() + "接收" + ctx.channel().remoteAddress() + "数据成功。");
		ByteBuf requestBuffer = (ByteBuf) msg;

		// 解码
		Packet packet = PacketCodec.getInstance().decode(requestBuffer);
		requestBuffer.release();

		// 判断是否是登录请求数据包
		if(packet instanceof LoginRequestPacket) {
			LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
			LoginResponsePacket responsePacket = new LoginResponsePacket();

			if (valid(loginRequestPacket)) {
				responsePacket.setSuccess(true);
			} else {
				responsePacket.setSuccess(false);
				responsePacket.setReason("账号密码校验失败");
			}
			ByteBuf responseBuffer = PacketCodec.getInstance().encode(ctx.alloc(), responsePacket);

			ctx.channel().writeAndFlush(responseBuffer);
		} else if(packet instanceof MessageRequestPacket) {
			MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
			System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

			MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
			messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

			ByteBuf buffer = PacketCodec.getInstance().encode(ctx.alloc(), messageResponsePacket);
			ctx.writeAndFlush(buffer);
		}
		System.out.println(new Date() + "发送" + ctx.channel().remoteAddress() + "数据成功。");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + ":" + cause.getMessage());
		ctx.close();
	}

	private boolean valid(LoginRequestPacket packet) {
		return true;
	}

}
