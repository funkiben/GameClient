package world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import world.object.WorldObject;
import world.object.WorldObjectType;

public class World {
	
	private final Map<Integer, WorldObject> objectMap = new HashMap<Integer, WorldObject>();
	private final Map<Class<? extends WorldObject>, Map<Integer, WorldObject>> objectMapByType = new HashMap<Class<? extends WorldObject>, Map<Integer, WorldObject>>();
	
	public World() {
		for (WorldObjectType t : WorldObjectType.values()) {
			objectMapByType.put(t.getWorldObjectClass(), new HashMap<Integer, WorldObject>());
		}
	}
	
	@SuppressWarnings("unchecked")
	public <E extends WorldObject> List<E> getObjects(Class<? extends WorldObject> clazz) {
		return new ArrayList<E>((Collection<? extends E>) objectMapByType.get(clazz).values());
	}
	
	public List<WorldObject> getObjects() {
		return new ArrayList<WorldObject>(objectMap.values());
	}
	
	public void addObject(WorldObject o) {
		objectMapByType.get(o.getClass()).put(o.getId(), o);
		objectMap.put(o.getId(), o);
		o.ZOrderChange();
	}
	
	public boolean removeObject(WorldObject obj) {
		return removeObject(obj.getId());
	}
	
	public boolean removeObject(int id) {
		WorldObject obj = getObject(id);
		
		if (obj == null) {
			return false;
		}
		
		objectMapByType.get(obj.getClass()).remove(id);
		
		objectMap.remove(id);
		
		return true;
	}
	
	public WorldObject getObject(int id) {
		return objectMap.get(id);
	}
	
	

}
