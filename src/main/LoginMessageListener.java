package main;

import net.funkitech.util.Location;
import net.funkitech.util.server.messaging.MessageHandler;
import net.funkitech.util.server.messaging.MessageListener;


public class LoginMessageListener implements MessageListener {
	
	public volatile boolean waiting = false;
	public volatile boolean loginValid = false;
	public volatile String problem = new String();
	public volatile Location location;
	
	@MessageHandler(names = "loginSuccess")
	public void loginSuccess(Location location) {
		loginValid = true;
		waiting = false;
		this.location = location;
	}
	
	@MessageHandler(names = "loginFailed")
	public void loginFailed(String reason) {
		this.problem = reason;
		waiting = false;
	}

}
