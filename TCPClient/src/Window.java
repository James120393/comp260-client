import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFrame;

/*
 * Window class to set up input area
 */
public class Window implements ActionListener {
	/*
	 * Set up the variables required for the window And instance the needed
	 * classes
	 */
	private JFrame frame;
	private JTextArea InputArea = new JTextArea("");
	private JTextArea OutputArea = new JTextArea("");
	/*
	 * Set up the scrolling area and parent it to the OutputArea.
	 */
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

	
	/*
	 * Set the TCPProtocol
	 */
	public void setTcpProtocol(TCPProtocol tcpProtocol) {
		this.tcpProtocol = tcpProtocol;
	}

	
	/*
	 * Main function of window
	 */
	private Window() {
		// Create the frame
		frame = new JFrame("260Client");
		// Set the exit procedure, when the window is closed it will exit the
		// program
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set the bounds for the scrolling plane, which is parented to the
		// output text area.
		scrollPane.setBounds(10, 10, 500, 350);

		// Set up the output area to not be editable
		OutputArea.setEditable(false);
		// Wrap the text within the bounds of the text area
		OutputArea.setLineWrap(true);
		OutputArea.setWrapStyleWord(true);

		// Wrap the text within the bounds of the text area
		InputArea.setLineWrap(true);
		InputArea.setWrapStyleWord(true);
		// Set the bounds for the input area
		InputArea.setBounds(10, 400, 500, 50);
		InputArea.setColumns(1);

		// Add the input and scrollpane to the frame
		frame.add(InputArea);
		frame.add(scrollPane);
		// Set the size of the frame
		frame.setSize(525, 500);
		// Set the layout to custom
		frame.setLayout(null);
		// Make the frame visible
		frame.setVisible(true);
		// Disable the user from being able to resize the window
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		
		/*
		 * Adding a key listener for user input
		 */
		InputArea.addKeyListener(new KeyAdapter() {
			// Check if a key has been pressed
			public void keyPressed(KeyEvent keyPressed) {
				// Check if that key was the ENTER key
				if (keyPressed.getKeyCode() == KeyEvent.VK_ENTER) {
					// Gets the test that was input into the window
					command = InputArea.getText();
					// Resets the text area to blank
					InputArea.setText("");
					// Sends the command to the server with white space removed
					tcpProtocol.sendCommand(command.trim());
				}
			}
		});

	}

	
	/*
	 * Set the text in the OutputArea to the response from the server
	 */
	public void setServerResponse(String message) {
		OutputArea.append(message + "\n");
	}

	
	/*
	 * A listener for any actions performed in the window needed for class to
	 * Operate
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
}
