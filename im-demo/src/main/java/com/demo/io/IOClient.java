package com.demo.io;

import java.io.IOException;
import java.net.Socket;

public class IOClient {
	public static void main(String[] args) {
		new Thread(() -> {
			try {
				Socket socket = new Socket("127.0.0.1", 8000);
				while (true) {
					try {
						socket.getOutputStream().write((new java.util.Date() + ": hello world").getBytes());
						Thread.sleep(2000);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}).start();
	}
}
