package world.object;

import net.funkitech.util.Location;

public enum WorldObjectType {
	
	PLAYER  			(0x001, Player.class),
	BLOCK  				(0x002, Block.class),
	CONTROLLED_PLAYER 	(0x003, ThisPlayer.class),
	;
	
	
	private final int id;
	private final Class<? extends WorldObject> clazz;
	
	WorldObjectType(int id, Class<? extends WorldObject> clazz) {
		this.id = id;
		this.clazz = clazz;
	}
	
	public int getId() {
		return id;
	}
	
	public Class<? extends WorldObject> getWorldObjectClass() {
		return clazz;
	}
	
	public WorldObject newInstance(int id, Location location, Object[] customData) {
		try {
			return clazz.getConstructor(Integer.TYPE, Location.class, Object[].class).newInstance(id, location, customData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static WorldObject newInstance(int id, int typeId, Location location, Object[] customData) {
		for (WorldObjectType t : WorldObjectType.values()) {
			if (t.getId() == typeId) {
				return t.newInstance(id, location, customData);
			}
		}
		
		return null;
	}
	

}
