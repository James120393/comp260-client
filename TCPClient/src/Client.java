
public class Client {
	
	private static TCPProtocol tcpProtocol;
	private static Window window;

	public static void main(String[] args) {
		window = Window.getWindow();
		tcpProtocol = new TCPProtocol(window);
		window.setTcpService(tcpProtocol);
		TCPProtocol.main(args);
	}

}
