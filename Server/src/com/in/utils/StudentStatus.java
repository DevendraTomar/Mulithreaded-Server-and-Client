package com.in.utils;

import java.net.Socket;

public class StudentStatus {

	Socket socket;
	String name;
	boolean status;

	public StudentStatus(Socket socket, String name, boolean status) {
		this.socket = socket;
		this.name = name;
		this.status = status;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object object) {

		return ((StudentStatus) object).getName().equals(this.name);

	}

}
