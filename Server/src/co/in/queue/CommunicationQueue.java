package co.in.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import co.in.main.ClientCommunicationHandler;

public class CommunicationQueue {
	private BlockingQueue<ClientCommunicationHandler> workQueue = new LinkedBlockingQueue<>();

	public void add(ClientCommunicationHandler clientCommunicationHandler) {
		workQueue.add(clientCommunicationHandler);
	}

	public int getStatus() {
		return workQueue.size();
	}

	public boolean remove(ClientCommunicationHandler clientCommunicationHandler) {
		return workQueue.remove(clientCommunicationHandler);
	}

}
