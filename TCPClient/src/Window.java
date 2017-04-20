import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.text.DefaultCaret;

/*
 * Window class to set up input area
 */
public class Window implements ActionListener {
	private JFrame frame;
	private JTextArea InputArea = new JTextArea("");
	private JTextArea OutputArea = new JTextArea("");
	private JScrollPane scrollPane = new JScrollPane(OutputArea);
	private TCPProtocol tcpProtocol;
	private static Window window = new Window();
	private String command;

	/*
	 * Returns the window
	 */
	public static Window getWindow() {
		return window;
	}

	public void setTcpService(TCPProtocol tcpProtocol) {
		this.tcpProtocol = tcpProtocol;
	}

	private Window() {
		frame = new JFrame("260Client");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		scrollPane.setBounds(10, 10, 500, 350);
		
		OutputArea.setEditable(false);
		OutputArea.setLineWrap(true);
		OutputArea.setWrapStyleWord(true);
		OutputArea.addComponentListener(null);
		DefaultCaret caret = (DefaultCaret) OutputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		InputArea.setLineWrap(true);
		InputArea.setWrapStyleWord(true);
		InputArea.setBounds(10, 400, 500, 50);
		InputArea.addComponentListener(null);
		InputArea.setColumns(1);

		frame.add(InputArea);
		frame.add(scrollPane);
		frame.setSize(525, 500);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		InputArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent keyPressed) {
				if (keyPressed.getKeyCode() == KeyEvent.VK_ENTER) {
					command = InputArea.getText();
					InputArea.setText("");
					tcpProtocol.sendCommand(command.trim());
				}
			}
		});

	}

	public void readServerResponse() {
		OutputArea.setText(InputArea.getText() + tcpProtocol.serverResponse);
		OutputArea.append(tcpProtocol.serverResponse + "\n");
	}

	public void setServerResponse(String message) {
		OutputArea.append(message + "\n");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
