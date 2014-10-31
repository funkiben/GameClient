package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.funkitech.util.server.messaging.Message;
import net.funkitech.util.server.messaging.MessageListener;


public class CreateAccountWindow extends JFrame {

	private static final long serialVersionUID = -2080418318567475166L;
	
	private final CreateAccountMessageListener messageListener = new CreateAccountMessageListener();
	
	private final JTextField usernameTF;
	private final JPasswordField passwordTF;
	private final JPasswordField confirmPasswordTF;
	private final JButton createBtn;
	private final JLabel outputLbl;
	
	public CreateAccountWindow() {
		super("Create Account");
		
		usernameTF = new JTextField(16);
		passwordTF = new JPasswordField(16);
		confirmPasswordTF = new JPasswordField(16);
		outputLbl = new JLabel();
		
		createBtn = new JButton("Create Account");
		createBtn.addActionListener(new ButtonActionListener());
		
		setLayout(new FlowLayout());
		

		add(new JLabel("Username"));
		add(usernameTF);
		add(new JLabel("Password"));
		add(passwordTF);
		add(new JLabel("Confirm"));
		add(confirmPasswordTF);
		add(createBtn);
		add(outputLbl);
		
		setResizable(false);
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
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
			outputLbl.setBorder(new LineBorder(Color.RED));
			
			String password = new String(passwordTF.getPassword());
			String confirm = new String(confirmPasswordTF.getPassword());
			
			if (!password.equals(confirm)) {
				outputLbl.setText("Password does not match confirm");
				return;
			}
			
			password = encrypt(passwordTF.getPassword());
			
			String username = usernameTF.getText();
			
			
			try {
				Main.socket.sendMessage(new Message("createAccount", username, password));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			messageListener.waiting = true;
			messageListener.accountValid = false;
			
			outputLbl.setText("Validating...");
			
			while (messageListener.waiting) {
				Main.sleep(100);
			}
			
			
			if (messageListener.accountValid) {
				outputLbl.setBorder(new LineBorder(Color.BLACK));
				outputLbl.setText("<html>Account successfully created!<br>You will now be able to log in.</html>");
				
			} else {
				outputLbl.setText(messageListener.problem);
				
			}
			
			
		}
		
	}

}
