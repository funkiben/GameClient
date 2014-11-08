package main;

import world.object.ThisPlayer;
import world.object.WorldObject;
import world.object.WorldObjectType;
import net.funkitech.util.Location;
import net.funkitech.util.server.messaging.MessageHandler;
import net.funkitech.util.server.messaging.MessageListener;

public class ServerMessageListener implements MessageListener {
	
	@MessageHandler(names = "worldobjectRemoved")
	public void worldobjectRemoved(Integer id) {
		while (GameWindow.isDrawing) {
			Main.sleep(10);
		}
		
		Main.world.removeObject(id);
		
	}

	@MessageHandler(names = "worldobject")
	public void worldobject(Integer id, Integer typeId, Location location, Object[] customData) {
		WorldObject object = Main.world.getObject(id);

		if (object != null) {
			object.smoothMoveTo(location);
			object.setCustomData(customData);
			object.onUpdateFromServer();
			
		} else {
			object = WorldObjectType.newInstance(id, typeId, location, customData);
			
			while (GameWindow.isDrawing) {
				Main.sleep(10);
			}
			
			Main.world.addObject(object);
			
			if (object instanceof ThisPlayer) {
				Main.player = (ThisPlayer) object;
			}
		}
	}

	@MessageHandler(names = "disconnect")
	public void disconnect(String reason) {
		Main.warningMsg("Disconnected from server", reason);
		Main.close();
	}
	
	@MessageHandler(names = "chat")
	public void chat(String msg) {
		Main.gameWindow.chatWindow.addEntry(msg);
	}

}
