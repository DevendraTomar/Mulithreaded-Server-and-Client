package com.in.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import co.in.db.DbManager;
import co.in.db.StudentDao;
import co.in.exception.ServerException;

public class ServerUtils {

	public static List<StudentDao> manipulateList(List<StudentDao> studentList, StudentDao studentDao) {

		for (int i = 0; i < studentList.size(); i++) {
			if (studentDao.getName().equals(studentList.get(i).getName())) {
				studentList.remove(i);
			}
		}

		return studentList;
	}

	public static StudentDao validateUser(DbManager dbManager, BufferedReader bufferedReader,
			PrintWriter serverOutStream) throws IOException {

		while (true) {
			String dataFromClient = bufferedReader.readLine();

			try {
				int rollNumber = Integer.parseInt(dataFromClient);
				StudentDao studentDao = dbManager.getStudenDetailsFromRollNo(rollNumber);

				if (studentDao == null) {
					serverOutStream.println("Student with this roll number does not exist !");
					serverOutStream.flush();
					continue;
				}
				return studentDao;

			} catch (NumberFormatException ex) {
				serverOutStream.println("Please enter a valid roll no.!");
				serverOutStream.flush();

			}
		}
	}

	public static class Client {

		private static Queue<StudentStatus> map = new ConcurrentLinkedQueue<>();

		public static void addClient(StudentStatus stats) {
			map.add(stats);
		}

		public static String getClientName(Socket socket) {

			Iterator<StudentStatus> iterator = map.iterator();
			StudentStatus studentDetails = null;
			StudentStatus tempStudentDetails = null;

			while (iterator.hasNext()) {
				studentDetails = iterator.next();

				while (studentDetails.getSocket().equals(socket)) {
					tempStudentDetails = studentDetails;
					break;
				}

			}

			if (tempStudentDetails == null) {
				return null; // Check
			} else {
				return tempStudentDetails.getName();
			}

		}

		public static int getUsers() {

			return map.size();
		}

		public static void refreshList(StudentStatus studentStatus) {

			Iterator<StudentStatus> iterator = map.iterator();

			while (iterator.hasNext()) {

				while (iterator.next().equals(studentStatus)) {
					iterator.remove();
					break;
				}

			}

		}

		public static void broadCastMsg(StudentStatus socket, String data) {

			Iterator<StudentStatus> iterator = map.iterator();

			while (iterator.hasNext()) {

				StudentStatus studentStatus = iterator.next();

				while (!studentStatus.equals(socket)) {

					try {
						PrintWriter printWriter = new PrintWriter(studentStatus.getSocket().getOutputStream());
						printWriter.println(socket.getName() + ":" + data);
						printWriter.flush();
						printWriter = null;
						break;
					} catch (IOException e) {
						throw new ServerException(e.getMessage());
					}

				}

			}

		}

		public static void broadCastSystemMsg(StudentStatus socket, String data) {

			Iterator<StudentStatus> iterator = map.iterator();

			while (iterator.hasNext()) {

				StudentStatus studentStatus = iterator.next();

				while (!studentStatus.equals(socket)) {

					try {
						PrintWriter printWriter = new PrintWriter(studentStatus.getSocket().getOutputStream());
						printWriter.println("System" + ":" + data);
						printWriter.flush();
						printWriter = null;
						break;
					} catch (IOException e) {
						throw new ServerException(e.getMessage());
					}

				}

			}
		}

	}

}
