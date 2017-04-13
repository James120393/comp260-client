import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class Window implements ActionListener {
	static JTextArea responseArea;
	JLabel l2;
	static JTextArea area;
	JButton b;

	Window() {
		JFrame f = new JFrame();
		responseArea = new JTextArea();
		responseArea.setBounds(10, 50, 500, 200);
		responseArea.setEditable(false);
		l2 = new JLabel();
		l2.setBounds(160, 25, 100, 30);
		area = new JTextArea();
		area.setBounds(10, 300, 500, 30);
		b = new JButton("Submit");
		b.setBounds(150, 350, 120, 30);
		b.addActionListener(this);
		f.add(responseArea);
		f.add(l2);
		f.add(area);
		f.add(b);
		f.setSize(550, 500);
		f.setLayout(null);
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String text = area.getText();
		String words[] = text.split("\\s");
	}
	
	public void run()
	{
		inputLine = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public String getTextStream()
	{
		return area.getText();
		
	}
	
	public void setTextStream()
	{
		area.setText(area.getText());
	}
	
	public void readServerResponse()
	{
		responseArea.setText(responseArea.getText() + TCPClient.serverResponse);
		responseArea.append(TCPClient.serverResponse + "\n");
	}
	
	public void setServerResponse()
	{
		String text = area.getText();
		TCPClient.
	}
	
}