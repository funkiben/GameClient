package world.object;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.funkitech.util.Location;

public class Tree extends ImageObject {
	
	private static final BufferedImage imgs[] = new BufferedImage[2];
	
	static {
		imgs[0] = loadImage("pack1/items/Tree (1).png");
		imgs[1] = loadImage("pack1/items/Tree (2).png");
	}
	
	public Tree(int id, Location location, Object[] customData) {
		super(id, location, getSquareBounds((Integer) customData[0], (Integer) customData[1]), customData);
		

		setImage(imgs[(Integer) customData[2]]);
		
		setImageWidth((Integer) customData[0]);
		setImageHeight((Integer) customData[1]);
		
		// TODO - position tree better
		
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		drawImage(g, x, y);
	}

	@Override
	public void update(int frames) {
		
	}

	@Override
	public void onUpdateFromServer() {
		
	}

}
