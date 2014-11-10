package world.object;

import java.awt.Graphics;

import net.funkitech.util.Location;

public abstract class Tile extends ImageObject {

	public Tile(int id, Location location, Object[] customData) {
		super(id, location, getSquareBounds((Integer) customData[0], (Integer) customData[1]), customData);
		setZLevel(-10);
		
		setImageWidth((Integer) customData[0]);
		setImageHeight((Integer) customData[1]);
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		drawImage(g, x, y);
	}
	

}
