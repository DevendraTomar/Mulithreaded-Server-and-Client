package co.in.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws InterruptedException {

		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 8080);

			if (socket.isConnected()) {

				AsynchronuousCommunication asyncCom = new AsynchronuousCommunication(socket.getInputStream());
				new Thread(asyncCom).start();
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

				while (!socket.isClosed()) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
					String data = bufferedReader.readLine();
					printWriter.println(data);
					printWriter.flush();

				}

			}

		} catch (IOException exception) {
			System.out.println("Server got disconnected !");

		} finally {
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					throw new ClientException(e.getMessage());
				}
			}
		}
	}

}
