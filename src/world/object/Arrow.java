package world.object;

import java.awt.Color;
import java.awt.Graphics;

import net.funkitech.util.Location;

public class Arrow extends WorldObject {
	
	private final static int width  = 7;
	private final static int height = 40;
	
	private static Location[] newBounds() {
		return new Location[] {
				new Location(width/2, height/2),
				new Location(-width/2, height/2),
				new Location(-width/2, -height/2),
				new Location(width/2, -height/2)
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
		g.setColor(Color.GREEN);
		g.fillRect(x - (width / 2), y - (height / 2), width, height);
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
