package com.demo.netty;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.demo.netty.handler.ClientHandler;
import com.demo.netty.protocal.common.PacketCodec;
import com.demo.netty.protocal.common.request.MessageRequestPacket;
import com.demo.netty.util.LoginUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	private static String host = "127.0.0.1";
	private static int port = 8000;
	private final static int MAX_RETRY = 10; // 最大重连次数

	public static void main(String[] args) throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();

		NioEventLoopGroup group = new NioEventLoopGroup();
		bootstrap
				// 1.指定线程模型
				.group(group)
				// 2.指定 IO 类型为 NIO
				.channel(NioSocketChannel.class)
				// .option(ChannelOption.TCP_NODELAY, true)
				// .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				// .option(ChannelOption.SO_KEEPALIVE, true)
				// 3.IO 处理逻辑
				.handler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						// ch.pipeline() 返回的是和这条连接相关的逻辑处理链，采用了责任链模式
						// ch.pipeline().addLast(new StringEncoder());
						// 调用 addLast() 方法
						// 添加一个逻辑处理器，这个逻辑处理器为的就是在客户端建立连接成功之后，向服务端写数据
						ch.pipeline().addLast(new ClientHandler());
					}

				});

		// 连接
		connect(bootstrap, MAX_RETRY);
	}

	private static void connect(Bootstrap bootstrap, int retry) {
		bootstrap.connect(host, port).addListener(future -> {
			if (future.isSuccess()) {
				Channel channel = ((ChannelFuture) future).channel();
				startConsoleThread(channel);
			} else if (retry == 0) {
				System.out.println("重试次数已用完，放弃连接！");
			} else {
				int order = (MAX_RETRY - retry) + 1;
				int delay = 1 << order; // 0001 左移1位的话 0010=>2

				System.out.println(new java.util.Date() + ": 连接失败，第" + order + "次重连……");
				bootstrap.config().group().schedule(() -> connect(bootstrap, retry - 1), delay, TimeUnit.SECONDS);
			}
		});
	}

	private static void startConsoleThread(Channel channel) {
		new Thread(() -> {
			while (!Thread.interrupted()) {
				if (LoginUtil.hasLogin(channel)) {
					System.out.println("输入消息发送至服务端: ");
					Scanner scanner = new Scanner(System.in);
					String line = scanner.nextLine();
					if (line.equalsIgnoreCase("exit")) {
						break;
					}

					MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
					messageRequestPacket.setMessage(line);
					ByteBuf buffer = PacketCodec.getInstance().encode(channel.alloc(), messageRequestPacket);

					channel.writeAndFlush(buffer);
				}
			}
		}).start();
	}
}
