
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

public class TCPProtocol implements Runnable {

	// The client socket
	private static Socket clientSocket = null;
	// The output stream
	private static PrintStream os = null;
	// The input stream
	private static DataInputStream is = null;
	// Reader for text input
	private static BufferedReader inputLine = null;
	// Boolean for closing the socket
	private static boolean closed = false;
	// Setting up the server response for the client
	public String serverResponse = null;
	// Instancing the window
	private static Window window;

	public TCPProtocol(Window window) {
		TCPProtocol.window = window;
	}

	// Function for sending the command to the server
	public void sendCommand(String command) {
		os.println(command);
	}

	public static void main(String[] args) {

		// The default port.
		int portNumber = 25565;
		// The default host.
		String host = "138.68.191.170";

		window = Window.getWindow();

		if (args.length < 2) {
			window.setServerResponse("Usage: java MultiThreadChatClient <host> <portNumber>\n" + "Now using host=" + host
					+ ", portNumber=" + portNumber);
		} else {
			host = args[0];
			portNumber = Integer.valueOf(args[1]).intValue();
		}

		/*
		 * Open a socket on a given host and port. Open input and output
		 * streams.
		 */
		try {
			clientSocket = new Socket(host, portNumber);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to the host " + host);
		}

		/*
		 * If everything has been initialized then we want to write some data to
		 * the socket we have opened a connection to on the port portNumber.
		 */
		if (clientSocket != null && os != null && is != null) {
			try {

				/* Create a thread to read from the server. */
				Thread thread = new Thread(new TCPProtocol(window));
				thread.start();
				
				/*
				 * Close the output stream, close the input stream, close the
				 * socket.
				 */
				while (!closed) {
					if (thread == null || window == null) {
						closed = true;
					}
					// os.println(inputLine.readLine().trim());
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
	 * Create a thread to read from the server. (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */

	@SuppressWarnings("deprecation")
	public void run() {
		/*
		 * Keep on reading from the socket till we receive "Bye" from the
		 * server. Once we received that then we want to break.
		 */
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
