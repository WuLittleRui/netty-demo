package com.demo.netty;

import com.demo.netty.handler.ServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {
	private static int port = 8000;

	public static void main(String[] args) {
		// 将引导我们进行服务端的启动工作
		ServerBootstrap serverBootstrap = new ServerBootstrap();

		// 接受新连接线程，主要负责创建新连接
		NioEventLoopGroup boss = new NioEventLoopGroup();
		// 负责读取数据的线程，主要用于读取数据以及业务逻辑处理
		NioEventLoopGroup worker = new NioEventLoopGroup();
		// 给引导类配置两大线程组，这个引导类的线程模型也就定型了
		serverBootstrap.group(boss, worker)
//				.option(ChannelOption.TCP_NODELAY, true)
//				.option(ChannelOption.SO_KEEPALIVE, true)
				// 指定服务端的 IO 模型 NioServerSocketChannel
				.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {
					// 定义后续每条连接的数据读写，业务处理逻辑
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
//						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new ServerHandler());
					}
				}).bind(port);
		// 用于指定在服务端启动过程中的一些逻辑，通常情况下，用不着这个方法
		// serverBootstrap.handler(new
		// ChannelInitializer<NioServerSocketChannel>() {
		// protected void initChannel(NioServerSocketChannel ch) {
		// System.out.println("服务端启动中");
		// }
		// });
		// NioServerSocketChannel指定一些自定义属性，然后我们可以通过channel.attr()取出这个属性
		// serverBootstrap.attr(AttributeKey.newInstance("serverName"),
		// "nettyServer")
		// childAttr可以给每一条连接指定自定义属性，然后后续我们可以通过channel.attr()取出该属性。
		// serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"),
		// "clientValue")
	}
}
