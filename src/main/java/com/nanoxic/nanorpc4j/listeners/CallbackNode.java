package com.nanoxic.nanorpc4j.listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanoxic.nanorpc4j.Callback;

public class CallbackNode extends Thread {

	private int callbackPort;

	private ArrayList<AbstractCallbackListener> callbackListeners = new ArrayList<AbstractCallbackListener>();
	private ObjectMapper mapper = new ObjectMapper();
	private boolean running = false;

	public CallbackNode(int callbackPort) {
		this.callbackPort = callbackPort;
	}

	public void addCallbackListener(AbstractCallbackListener callbackListener) {
		callbackListeners.add(callbackListener);
		if (!running) {
			this.start();
			running = true;
		}
	}

	public void removeCallbackListener(AbstractCallbackListener callbackListener) {
		callbackListeners.remove(callbackListener);
		if (callbackListeners.size() == 0)
			running = false;
	}

	public void run() {
		try {
			ServerSocket socket = new ServerSocket(callbackPort);
			while (running) {
				String newBlockJSON = returnNextCallback(socket);

				System.out.print("CALLBACK: ");
				System.out.println(newBlockJSON);
				final Callback callback = mapper.readValue(newBlockJSON, Callback.class);
				callbackListeners.forEach((jos) -> {
					jos.newCallback(callback);
				});

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String returnNextCallback(ServerSocket socket) throws IOException {
		Socket clientSocket = socket.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String line = null;
		int length = 0;
		while ((line = in.readLine()) != null) {
			if (line.contains("Content-Length"))
				length = Integer.parseInt(line.split(" ")[1]);
			if (length != 0 && !line.contains(":"))
				break;
		}
		char[] body = new char[length];
		in.read(body);

		String newBlockJSON = new String(body);
		newBlockJSON = newBlockJSON.replace("\"{", "{");
		newBlockJSON = newBlockJSON.replace("\\n\"", "\\n");
		newBlockJSON = newBlockJSON.replace("\\n", "");
		newBlockJSON = newBlockJSON.replace("\\", "");
		return newBlockJSON;
	}

}
