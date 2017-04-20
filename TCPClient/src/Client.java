
public class Client {
	
	/*
	 * Set up variables for class to access
	 */
	private static TCPProtocol tcpProtocol;
	private static Window window;

	/*
	 * Main function to run at start
	 */
	public static void main(String[] args) {
		/*
		 * Create The window
		 */
		window = Window.getWindow();
		/*
		 * Create the window for the TCP Protocol Class
		 */
		tcpProtocol = new TCPProtocol(window);
		/*
		 * Link the window class to the TCPProtocol Class
		 */
		window.setTcpProtocol(tcpProtocol);
		/*
		 * Run the TCPProtocol class
		 */
		TCPProtocol.main(args);
	}
}
