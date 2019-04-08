package shs.server;

import java.io.IOException;
import java.net.ServerSocket;

import shs.common.Tool;

public class ServerLaunch {
	
	// TODO : put this on the properties file (right now, it's just the information about jdbc)
	protected static final int PORT = 2001;

	public static void main(String args[]) throws IOException {
		
		System.out.println("SHS Server : launch");
		
		// DataSource
		DataConfig.getInstanceConfig();
		final ServerSocket serverSocket = new ServerSocket(PORT);
		final JDBCConnectionPool connectionPool = new JDBCConnectionPool();
		
		// Define the closing process of the server application (by closing the port and avoiding any blocked port issue at the next launch)
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					Tool.logger.info("ServerLaunch : closing " + PORT);
					serverSocket.close();
				} catch (IOException e) {
					Tool.logger.info("#Error ServerLaunch : An error occurs during the closing port process");
				}
			}
			}));
		
		ServerAcceptor serverAcceptor = new ServerAcceptor(serverSocket, connectionPool);
		serverAcceptor.acceptConnection();
	}
	
}
