package world.object.tile;

import java.awt.image.BufferedImage;

import net.funkitech.util.Location;

public class GrassTile extends Tile {
	
	private static final BufferedImage img = loadImage("pack1/grass/Grass (1).png");
	
	public GrassTile(int id, Location location, Object[] customData) {
		super(id, location, customData);
		setImage(img);
	}

	@Override
	public void update(int frames) {
		
	}

	@Override
	public void onUpdateFromServer() {
		
	}

}
