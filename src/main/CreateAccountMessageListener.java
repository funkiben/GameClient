package main;

import net.funkitech.util.server.messaging.MessageHandler;
import net.funkitech.util.server.messaging.MessageListener;


public class CreateAccountMessageListener implements MessageListener {

	public volatile boolean waiting = false;
	public volatile boolean accountValid = false;
	public volatile String problem = new String();
	
	
	
	@MessageHandler(names = "accountInvalid")
	public void accountInvalid(String reason) {
		waiting = false;
		problem = reason;
	}
	
	@MessageHandler(names = "accountValid")
	public void accountValid() {
		waiting = false;
		accountValid = true;
	}

}
