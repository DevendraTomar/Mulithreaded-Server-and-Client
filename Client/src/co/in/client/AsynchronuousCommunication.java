package co.in.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AsynchronuousCommunication implements Runnable {

	InputStream inputStreamReader;

	public AsynchronuousCommunication(InputStream inputStream) {
		this.inputStreamReader = inputStream;
	}

	@Override
	public void run() {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamReader));
		String data = null;
		try {
			while (true) {

				data = bufferedReader.readLine();
				System.out.println(data);

			}
		} catch (IOException e) {
			System.out.println("Server got disconnected !");
			System.exit(-1);
		
		} finally {
			try {
				inputStreamReader.close();
			} catch (IOException e) {

			}
		}
	}

}
