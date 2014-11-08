package main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.funkitech.util.server.messaging.Message;

public class ChatWindow extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = -3689341656241302306L;
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[kk:mm:ss] ");
	private static final int MAX_ENTRIES = 20;
	
	private final List<String> entries = new ArrayList<String>(MAX_ENTRIES);
	private final JLabel output = new JLabel();
	private final JTextField input = new JTextField(35);
	
	public ChatWindow() {
		super("Chat");
		
		for (int i = 0; i <= MAX_ENTRIES; i++) {
			addRawEntry("\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010\u0010");
		}
		
		
		setOutput();
		
		setSize(500, 400);
		add(output);
		add(input);
		
		input.addActionListener(this);
		
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		setLayout(layout);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setVisible(true);
		
		
	}
	
	public void setOutput() {
		String str = new String();
		
		for (String e : entries) {
			str = e + "<br>" + str;
		}
		
		str = "<html>" + str;
		
		str += "</html>";
		
		output.setText(str);
	}
	
	private String format(String msg) {
		return DATE_FORMAT.format(new Date()) + msg;
	}
	
	public synchronized void addEntry(String msg) {
		if (entries.size() >= MAX_ENTRIES) {
			entries.remove(MAX_ENTRIES - 1);
		}
		
		entries.add(0, format(msg));
		
		setOutput();
	}
	
	public synchronized void addRawEntry(String msg) {
		if (entries.size() >= MAX_ENTRIES) {
			entries.remove(MAX_ENTRIES - 1);
		}
		
		entries.add(0, msg);
		
		setOutput();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Main.socket.sendMessage(new Message("chat", e.getActionCommand()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		input.setText(new String());
	}
	
}
