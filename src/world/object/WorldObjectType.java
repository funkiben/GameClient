package world.object;

import world.object.tile.*;
import net.funkitech.util.Location;

public enum WorldObjectType {
	
	PLAYER  			(0x001, Player.class),
	CONTROLLED_PLAYER 	(0x002, ThisPlayer.class),
	ARROW				(0x003, Arrow.class),
	
	TILE_COBBLE			(0x010, CobbleTile.class),
	TILE_WATER			(0x011, WaterTile.class),
	TILE_GRASS			(0x012, GrassTile.class),
	
	TREE				(0x020, Tree.class),
	
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
	
	public static WorldObjectType getTypeOf(WorldObject obj) {
		for (WorldObjectType t : WorldObjectType.values()) {
			if (t.getWorldObjectClass() == obj.getClass()) {
				return t;
			}
		}
		return null;
	}
	

}
