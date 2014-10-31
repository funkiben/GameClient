package world.object;

import java.awt.Color;
import java.awt.Graphics;

import net.funkitech.util.Location;


public class Block extends WorldObject {
	
	public static Location[] getBounds(int width, int height) {
		return new Location[] {
			new Location(width, height),
			new Location(-width, height),
			new Location(-width, -height),
			new Location(width, -height)
		};
	}
	
	private final static int width  = 50;
	private final static int height = 50;
	
	private int red;
	private final int random = (int) (Math.random() * 1000);
	
	public Block(int id, Location location, Object[] customData) {
		super(id, location, getBounds(width, height), customData);
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(new Color(red, 100, 100));
		g.fillRect(x - (width/2), y - (height/2), width, height);
	}
	
	@Override
	public void update(int frames) {
		frames += random;
		red = (int) ((Math.sin(frames / 10.0) * 128.0) + 128.0);

	}

	@Override
	public void onUpdateFromServer() {
		
	}

}
