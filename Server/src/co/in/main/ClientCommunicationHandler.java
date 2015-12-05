package co.in.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.in.utils.ServerUtils;
import com.in.utils.StudentStatus;

import co.in.db.DbManager;
import co.in.exception.ServerException;
import co.in.server.AsynchronuousCommunication;

public class ClientCommunicationHandler implements Runnable {

	DbManager dbManager;
	Socket socket;

	public ClientCommunicationHandler(DbManager dbManager, Socket socket) {

		this.dbManager = dbManager;
		this.socket = socket;

	}

	@Override
	public void run() {

		BufferedReader bufferedReader = null;
		StudentStatus student = null;
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println("Hello ! , Please enter your name:");
			printWriter.flush();

			try {
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				throw new ServerException(e.getMessage());
			}

			String name = bufferedReader.readLine();

			student = new StudentStatus(socket, name, false);

			ServerUtils.Client.addClient(student);
			printWriter.println("Hi " + name + " !");
			printWriter.println("Total no. active users : " + ServerUtils.Client.getUsers());

			if (ServerUtils.Client.getUsers() == 1) {
				printWriter.println(
						"You are the first one to enter this chat box, chat box is empty, so please wait for someone to arrive.");
			} else {

				ServerUtils.Client.broadCastSystemMsg(student, "User " + student.getName() + " added.");
				printWriter.println("You are now connected, please start the conversation.");
			}
			printWriter.flush();

			AsynchronuousCommunication asyncCom = new AsynchronuousCommunication(student);
			new Thread(asyncCom).start();

			// StudentDao studentDao = ServerUtils.validateUser(dbManager,
			// bufferedReader, printWriter);
			// printWriter.println("Hi " + studentDao.getName() + " !");
			// List<StudentDao> studentList = dbManager.getAllUsers();
			// List<StudentDao> updatedList =
			// ServerUtils.manipulateList(studentList, studentDao);
			// printWriter.println("Students in your circle :");

			/*
			 * for (int i = 0; i < updatedList.size(); i++) {
			 * printWriter.println(updatedList.get(i).getName()); }
			 */
			// printWriter.println("Please enter the name of the student , you
			// wish to chat with :");
			// printWriter.flush();

			// checkForAvailableStudents(bufferedReader,studentDao);

		} catch (IOException e) {
			System.out.println(student.getName() + " got disconnected");
		}

	}

}
