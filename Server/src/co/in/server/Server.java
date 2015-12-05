package co.in.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import co.in.db.DbManager;
import co.in.exception.ServerException;
import co.in.main.ClientCommunicationHandler;
import co.in.queue.CommunicationQueue;

public class Server {

	public static void main(String[] args) throws IOException {

		String s = new File(".").getCanonicalPath() + "\\test.db";

		DbManager dbManager = new DbManager(s);
		CommunicationQueue communicationQueue = new CommunicationQueue();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8080);

			System.out.println("Waiting for clients to connect !");

			while (true) {
				Socket socket = serverSocket.accept();
				ClientCommunicationHandler handler = new ClientCommunicationHandler(dbManager, socket);
				communicationQueue.add(handler);
				Thread thread = new Thread(handler);
				thread.start();
			}

		} catch (IOException e) {
			throw new ServerException(e.getMessage());
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					System.exit(-1);
				}
			}
		}
	}

}
