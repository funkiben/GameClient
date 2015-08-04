package main;

import java.io.IOException;

import javax.swing.JOptionPane;

import world.World;
import world.object.ThisPlayer;
import net.funkitech.util.server.messaging.MessageListeningManager;
import net.funkitech.util.server.messaging.MessagingSocket;

public class Main {
	
	public static final int serverPort = 12345; //12345
<<<<<<< HEAD
	public static final String serverAddress = "localhost"; //71.174.248.94
=======
	public static final String serverAddress = "71.174.248.94"; //71.174.248.94
>>>>>>> FETCH_HEAD
	
	public static MessagingSocket socket;
	public static final MessageListeningManager msgListeningMg = new MessageListeningManager();
	
	public static World world = new World();
	
	public static GameWindow gameWindow;
	public static LoginWindow loginWindow;
	public static final CreateAccountWindow createAccountWindow = new CreateAccountWindow();
	
	public static volatile ThisPlayer player;
	
	public static void main(String[] args) {
		
		msgListeningMg.registerListeners(new ServerMessageListener());
		
		try {
			socket = new MessagingSocket(msgListeningMg, serverAddress, serverPort);
		} catch (IOException e) {
			errorMsg("Error", "Could not connect to the server: " + e.getMessage() + "\n\nPossible Problems:\n\n\t1. You do not have internet\n\t2. The server is currently down for maintanence.");
			return;
		}
		
		loginWindow = new LoginWindow();
		msgListeningMg.registerListeners(loginWindow.getMessageListener());
		msgListeningMg.registerListeners(createAccountWindow.getMessageListener());
		
		while (player == null) {
			sleep(100);
		}
		
		loginWindow.setVisible(false);
		loginWindow.dispose();
		
		gameWindow = new GameWindow(player.getFoV(), world);
		gameWindow.addKeyListener(player);
		gameWindow.addMouseMotionListener(player);
		gameWindow.addMouseListener(player);
		
	}
	
	public static void close() {
		
		if (gameWindow != null) {
			gameWindow.setVisible(false);
			gameWindow.dispose();
		}
		
		if (loginWindow != null) {
			loginWindow.setVisible(false);
			loginWindow.dispose();
		}
		
		socket.exit();
	}
	
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void infoMsg(String title, String msg) {
		JOptionPane.showMessageDialog(null,
		    msg,
		    title,
		    JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void warningMsg(String title, String msg) {
		JOptionPane.showMessageDialog(null,
		    msg,
		    title,
		    JOptionPane.WARNING_MESSAGE);
	}
	
	public static void errorMsg(String title, String msg) {
		JOptionPane.showMessageDialog(null,
		    msg,
		    title,
		    JOptionPane.ERROR_MESSAGE);
	}

}
