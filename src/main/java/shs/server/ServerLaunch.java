package shs.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Level;

import shs.common.Tool;

public class ServerLaunch {

	public static void main(String args[]) throws IOException {
		
		Tool.logger.getRootLogger().setLevel(Level.OFF);
		System.out.println("SHS Server : launch");
		
		// DataSource
		DataConfig.getInstanceConfig();
		final ServerSocket serverSocket = new ServerSocket(DataConfig.getPORT());
		final JDBCConnectionPool connectionPool = new JDBCConnectionPool();
		
		new Thread(new MemoryCache()).start();
		
		// Define the closing process of the server application (by closing the port and avoiding any blocked port issue at the next launch)
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					Tool.logger.info("ServerLaunch : closing " + DataConfig.getPORT());
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
