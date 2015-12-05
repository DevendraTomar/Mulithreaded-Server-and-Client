package co.in.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.in.utils.ServerUtils;
import com.in.utils.StudentStatus;

import co.in.exception.ServerException;

public class AsynchronuousCommunication implements Runnable {

	StudentStatus socket;

	public AsynchronuousCommunication(StudentStatus socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			String data = null;
			while (true) {
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(socket.getSocket().getInputStream()));
				data = bufferedReader.readLine();
				broadCastChat(data);
			}
		} catch (IOException e) {
			System.out.println(socket.getName() + " diconnected");
			ServerUtils.Client.refreshList(socket);
		}

		finally {
			if (socket.getSocket() != null) {
				try {
					socket.getSocket().close();
				} catch (IOException e) {
					throw new ServerException(e.getMessage());
				}
			}
		}
	}

	private void broadCastChat(String data) {

		if (ServerUtils.Client.getUsers() == 1) {
			System.out.println("Dear " + socket.getName() + ", no one is available to chat !");
		} else {
			ServerUtils.Client.broadCastMsg(this.socket, data);
		}

	}

}
