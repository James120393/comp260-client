import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class Window implements ActionListener {
	private static JTextArea serverInArea;
	private static JTextArea serverOutArea;
	private TCPProtocol tcpProtocol;
	private static Window window = new Window();
	private String command;

	public static Window getWindow() {
		return window;
	}

	public void setTcpService(TCPProtocol tcpProtocol) {
		this.tcpProtocol = tcpProtocol;
	}

	private Window() {
		JFrame f = new JFrame();
		serverOutArea = new JTextArea();
		serverOutArea.setBounds(10, 50, 500, 200);
		serverOutArea.setEditable(false);

		serverInArea = new JTextArea();
		serverInArea.setBounds(10, 300, 500, 30);

		f.add(serverInArea);
		f.add(serverOutArea);
		f.setSize(550, 500);
		f.setLayout(null);
		f.setVisible(true);

		serverInArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent keyPressed) {
				if (keyPressed.getKeyCode() == KeyEvent.VK_ENTER) {
					command = serverInArea.getText();
					serverInArea.setText("");
					tcpProtocol.sendCommand(command.trim());
				}
			}
		});

	}

	public void readServerResponse() {
		serverOutArea.setText(serverInArea.getText() + tcpProtocol.serverResponse);
		serverOutArea.append(tcpProtocol.serverResponse + "\n");
	}

	public void setServerResponse(String message) {
		serverOutArea.append(message + "\n");
		}

}