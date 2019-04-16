package shs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import shs.common.Tool;

public class ServerAcceptor {

	protected ServerSocket serverSocket;
	protected JDBCConnectionPool connectionPool;

	public ServerAcceptor(ServerSocket serverSocket, JDBCConnectionPool connectionPool) {
		this.serverSocket = serverSocket;
		this.connectionPool = connectionPool;
	}

	// Main part of the program : waiting for input
	public void acceptConnection() {
		System.out.println("SHS Server : Waiting for connection to serve");
		Socket socket = null;
		while (true) {
			try {
				// We wait for a connection
				socket = serverSocket.accept();
				System.out.println("SHS Server : a new connection was initialized!");

				if(!connectionPool.isEmpty()) {
					// We open a new Thread to serve the request
					Controller controller = new Controller(connectionPool);
					RequestHandler service = new RequestHandler(socket, controller);
					Thread thread = new Thread(service);
					thread.start();
				}
				else {
					// No connection are available, we can't serve the request
					System.out.println("No connections are available");
					socket.close();
				}
			} catch (IOException e) {
				Tool.logger.info("#Erreur : ServerAcceptor > I/O error: " + e);
			}
		}
	}


}
