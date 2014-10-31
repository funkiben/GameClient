package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.funkitech.util.Location;
import net.funkitech.util.server.messaging.Message;
import net.funkitech.util.server.messaging.MessageListener;


public class LoginWindow extends JFrame implements MessageListener {

	
	private static final long serialVersionUID = 6744432815630020429L;

	private final JTextField usernameTF;
	private final JPasswordField passwordTF;
	private final JButton loginBtn;
	private final JButton createAccountBtn;
	private final JLabel outputLbl;
	
	private final LoginMessageListener messageListener = new LoginMessageListener();
	
	public LoginWindow() {
		super("Login");
		
		usernameTF = new JTextField(16);
		
		passwordTF = new JPasswordField(16);
		
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ButtonActionListener());
		outputLbl = new JLabel();
		createAccountBtn = new JButton("Create Account");
		createAccountBtn.addActionListener(new ButtonActionListener2());
		
		setLayout(new FlowLayout());
		
		add(new JLabel("Username"));
		add(usernameTF);
		add(new JLabel("Password"));
		add(passwordTF);
		add(loginBtn);
		add(outputLbl);
		add(Box.createVerticalStrut(50));
		add(new JLabel("Don't have an account? Make one now!"));
		add(createAccountBtn);
		
		
		setSize(300, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	
		
	}
	
	public MessageListener getMessageListener() {
		return messageListener;
	}
	
	private String encrypt(char[] chars) {
		try {
			return new String(MessageDigest.getInstance("MD5").digest(new String(passwordTF.getPassword()).getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private class ButtonActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String password = encrypt(passwordTF.getPassword());
			String username = usernameTF.getText();
			
			try {
				Main.socket.sendMessage(new Message("login", username, password));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			messageListener.waiting = true;
			messageListener.loginValid = false;
			
			outputLbl.setText("Validating...");
			
			while (messageListener.waiting) {
				Main.sleep(100);
			}
			
			
			if (messageListener.loginValid) {
				outputLbl.setBorder(new LineBorder(Color.BLACK));
				Location location = messageListener.location;
				outputLbl.setText("Login successful. Login location: " + location);
				
			} else {
				outputLbl.setBorder(new LineBorder(Color.RED));
				outputLbl.setText(messageListener.problem);
				
			}
			
		}
		
	}
	
	private class ButtonActionListener2 implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			Main.createAccountWindow.setVisible(true);
		}
	}

}
