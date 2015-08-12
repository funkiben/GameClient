package world.object.tile;

import java.awt.image.BufferedImage;

import net.funkitech.util.Location;

public class WaterTile extends Tile {
	
	private final static BufferedImage[] imgs = new BufferedImage[56];
	
	static {
		try {
			for(int i = 0; i < 56; i++) {
				imgs[i] = loadImage("pack1/water/Water (" + (i + 1) + ").png");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WaterTile(int id, Location location, Object[] customData) {
		super(id, location, customData);
		
		setImage(imgs[(Integer) customData[2]]);
		setSolid(true);
		
	}

	@Override
	public void update(int frames) {
		
	}

	@Override
	public void onUpdateFromServer() {
		
	}

}
