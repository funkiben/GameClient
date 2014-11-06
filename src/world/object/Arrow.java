package world.object;

import java.awt.Color;
import java.awt.Graphics;

import net.funkitech.util.Location;

public class Arrow extends WorldObject {
	
	private final static int width  = 6;
	private final static int height = 40;
	
	private static Location[] newBounds() {
		return new Location[] {
				new Location(width/2, 0),
				new Location(-width/2, 0),
				new Location(-width/2, height),
				new Location(width/2, height)
		};
	}
	
	
	private double angle;
	private Location velocity;
	
	public Arrow(int id, Location location, Object[] customData) {
		super(id, location, newBounds(), customData);
		
		velocity = (Location) getCustomData()[0];
		
		angle = new Location(0, 0).angleBetween(velocity) + 90;
		rotate(angle);
		
	}
	
	public Location getVelocity() {
		return velocity;
	}
	
	public double getAngle() {
		return angle;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.drawLine(x, y, x, y + height);
		g.drawLine(x, y + height, x - width / 2 , y + height - 10);
		g.drawLine(x, y + height, x + width / 2, y + height - 10);
	}

	@Override
	public void update(int frames) {
		
	}

	@Override
	public void onUpdateFromServer() {
		Location velocity = (Location) getCustomData()[0];
		
		if (!velocity.equals(this.velocity)) {
			this.velocity = velocity;
			
			angle = new Location(0, 0).angleBetween(velocity);
			
			setRotation(angle);
		}
	}

}
