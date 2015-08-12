package world.object.tile;

import java.awt.image.BufferedImage;

import net.funkitech.util.Location;

public class CobbleTile extends Tile {
	
	private final static BufferedImage[] imgs = new BufferedImage[110];
	
	static {
		try {
			for(int i = 0; i < 110; i++) {
				imgs[i] = loadImage("pack2/stone/Stone (" + (i + 1) + ").png");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public CobbleTile(int id, Location location, Object[] customData) {
		super(id, location, customData);
		
		setImage(imgs[(Integer) customData[2]]);
		
	}

	@Override
	public void update(int frames) {
		
	}

	@Override
	public void onUpdateFromServer() {
		
	}

}
