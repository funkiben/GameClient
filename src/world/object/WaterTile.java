package world.object;

import java.awt.image.BufferedImage;

import net.funkitech.util.Location;

public class WaterTile extends Tile {
	
	private static final BufferedImage img;
	
	static {
		img = loadImage("pack1/water/Water (1).png");
	}

	public WaterTile(int id, Location location, Object[] customData) {
		super(id, location, customData);
		
		setImage(img);
		setSolid(true);
		
	}

	@Override
	public void update(int frames) {
		
	}

	@Override
	public void onUpdateFromServer() {
		
	}

}
