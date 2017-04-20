import java.io.DataInputStream;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import java.awt.event.*;

/*
 * Original Source From 
 * http://makemobiapps.blogspot.co.uk/p/multiple-client-server-chat-programming.html
 * Heavily modified
 */

public class TCPProtocol implements Runnable {
	/*
	 * The client socket
	 */
	private static Socket clientSocket = null;

	/*
	 * The output stream
	 */
	private static PrintStream os = null;

	/*
	 * The input stream
	 */
	private static DataInputStream is = null;

	/*
	 * Boolean for closing the socket
	 */
	private static boolean closed = false;

	/*
	 * Setting up the server response for the client
	 */
	public String serverResponse = null;

	/*
	 * Instancing the window
	 */
	private static Window window;

	/*
	 * Setting the instance of the window for thread use
	 */
	public TCPProtocol(Window window) {
		TCPProtocol.window = window;
	}

	/*
	 * Function for sending the command to the server
	 */
	public void sendCommand(String command) {
		os.println(command);
	}

	/*
	 * Main function for the networking between the client and sever
	 */
	public static void main(String[] args) {
		// The default port.
		int portNumber = 25565;

		// The default host.
		String host = "138.68.191.170";

		// Getting the instance of the window
		window = Window.getWindow();

		// Tell the user what port and IP address is being used
		if (args.length < 2) {
			window.setServerResponse("Now using host=" + host + ", portNumber=" + portNumber);
		} else {
			host = args[0];
			portNumber = Integer.valueOf(args[1]).intValue();
		}

		// Open a socket on a given host and port. Open input and output streams
		try {
			clientSocket = new Socket(host, portNumber);
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to the host " + host);
		}

		// If everything has been initialized then write some data to the socket
		// we have opened a connection too on the port portNumber.
		if (clientSocket != null && os != null && is != null) {
			try {
				// Create a thread to read from the server.
				Thread thread = new Thread(new TCPProtocol(window));
				thread.start();

				// Close the output stream, close the input stream, close the
				// socket.
				while (!closed) {
					if (thread == null || window == null) {
						closed = true;
					}
				}
				os.close();
				is.close();
				clientSocket.close();
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}
		}
	}

	/*
	 * Create a thread to read from the server Ignore deprecation warning
	 */
	@SuppressWarnings("deprecation")
	public void run() {
		// Keep on reading from the socket till we receive "Ending Session!"
		// from the server. Once we received that then we want to break.
		String responseLine;
		try {
			while ((responseLine = is.readLine()) != null && closed == false) {
				window.setServerResponse(responseLine);
				if (responseLine.indexOf("Ending Session!") != -1)
					break;
			}
			closed = true;
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}

	}
}
