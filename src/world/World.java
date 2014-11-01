package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import world.object.Player;
import world.object.WorldObject;

public class World {
	
	private final Map<Integer,WorldObject> objectMap = new HashMap<Integer,WorldObject>();
	
	@SuppressWarnings("unchecked")
	public <E extends WorldObject> List<E> getObjects(Class<? extends WorldObject> E) {
		List<E> list = new ArrayList<E>();
		
		for (WorldObject obj : objectMap.values()) {
			if (E.isAssignableFrom(obj.getClass())) {
				list.add((E) obj);
			}
		}
		
		return list;
	}
	
	public List<Player> getPlayers() {
		return getObjects(Player.class);
	}
	
	public List<WorldObject> getObjects() {
		return new ArrayList<WorldObject>(objectMap.values());
	}
	
	public void addObject(WorldObject o) {
		objectMap.put(o.getId(), o);
	}
	
	public boolean removeObject(WorldObject obj) {
		return removeObject(obj.getId());
	}
	
	public boolean removeObject(int id) {
		return objectMap.remove(id) != null;
	}
	
	public WorldObject getObject(int id) {
		return objectMap.get(id);
	}
	

	
	

}
