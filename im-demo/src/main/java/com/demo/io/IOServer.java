package com.demo.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {
	public static void main(String[] args) throws Exception {
		//绑定 8000 端口
		ServerSocket serverSocket = new ServerSocket(8000);
		
		new Thread(() -> {
			//阻塞方法获取新的连接
			try{
				Socket socket = serverSocket.accept();
				
				new Thread(() -> {
					try{
						int len;
						byte[] data = new byte[1024];
						InputStream inputStream = socket.getInputStream();
						
						while((len = inputStream.read(data)) != -1) {
							System.out.println(new String(data, 0 ,len));
						}
						
					}catch (IOException e) {
						System.out.println(e);
					}
				}).start();
			}catch (IOException e) {
				System.out.println(e);
			}
		}).start();
	}
}
