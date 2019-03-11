package shs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import shs.common.Tool;

public class ServerLaunch {
	
	protected static final int PORT = 2001;

	public static void main(String args[]) throws IOException {
		
		System.out.println("SHS Server : launch");
		
		// TODO What about writing here the DataSource initialization phase?
		DataConfig.getInstanceConfig();
		Socket socket = null;
		final ServerSocket serverSocket = new ServerSocket(PORT);
		Controller controller = new Controller(new JDBCConnectionPool());
		
		// Define the closing process of the server application (by closing the port and avoiding any blocked port issue at the next launch)
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					Tool.logger.info("ServerLaunch : closing " + PORT);
					serverSocket.close();
				} catch (IOException e) {
					Tool.logger.info("#Erreur ServerLaunch : An error occurs during the closing port process");
				}
			}
			}));
		
		System.out.println("SHS Server : Waiting for connection to serve");
		// Main part of the program : waiting for input
		while (true) {
			try {
				// We wait for a connection
				socket = serverSocket.accept();
				System.out.println("SHS Server : a new connection was initialized!");
				// We open a new Thread to serve the request
				new ServerService(socket, controller).start();
			} catch (IOException e) {
				Tool.logger.info("#Erreur : ServerLaunch > I/O error: " + e);
			}
		}
	}
	
}
