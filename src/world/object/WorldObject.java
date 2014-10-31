package world.object;

import java.util.Arrays;

import main.Drawable;
import net.funkitech.util.Location;

public abstract class WorldObject implements Drawable {
	
	private final int id;
	private final Location location;
	private final Location[] bounds;
	private final int lowestX;
	private final int lowestY;
	private final int highestX;
	private final int highestY;
	private Object[] customData;
	
	public WorldObject(int id, Location location, Location[] bounds, Object[] customData) {
		this.id = id;
		this.location = location;
		this.bounds = bounds;

		int[] xpoints = new int[bounds.length];
		int[] ypoints = new int[bounds.length];

		for (int i = 0; i < xpoints.length; i++) {
			xpoints[i] = (int) bounds[i].getX();
		}

		for (int i = 0; i < ypoints.length; i++) {
			ypoints[i] = (int) bounds[i].getY();
		}
		
		Arrays.sort(xpoints);
		Arrays.sort(ypoints);
		
		lowestX = xpoints[0];
		lowestY = ypoints[0];
		highestX = xpoints[xpoints.length - 1];
		highestY = ypoints[ypoints.length - 1];
		
		this.customData = customData;

	}
	
	public int getId() {
		return id;
	}

	public Location getLocation() {
		return location.clone();
	}

	public void setLocation(Location loc) {
		location.set(loc);
	}

	public void move(Location delta) {
		location.set(location.add(delta));
	}

	public double getX() {
		return location.getX();
	}

	public double getY() {
		return location.getY();
	}

	public Location[] getBounds() {
		return bounds;
	}

	public int getHighestXPoint() {
		return highestX;
	}

	public int getHighestYPoint() {
		return highestY;
	}

	public int getLowestXPoint() {
		return lowestX;
	}

	public int getLowestYPoint() {
		return lowestY;
	}
	
	public Object[] getCustomData() {
		return customData;
	}
	
	public void setCustomData(Object[] data) {
		customData = data;
	}
	
	public boolean contains(Location test) {
		test = test.subtract(location);
		
		int i;
		int j;
		boolean result = false;
		
		for (i = 0, j = bounds.length - 1; i < bounds.length; j = i++) {
			if ((bounds[i].getY() > test.getY()) != (bounds[j].getY() > test.getY()) && (test.getX() < (bounds[j].getX() - bounds[i].getX()) * (test.getY() - bounds[i].getY()) / (bounds[j].getY() - bounds[i].getY()) + bounds[i].getX())) {
				result = !result;
			}
		}
		
		return result;
	}
	
	public boolean contains(WorldObject obj) {
		for (Location loc : obj.getBounds()) {
			if (contains(loc.add(obj.getLocation()))) {
				return true;
			}
		}
		
		return false;
	}

	public abstract void update(int frames);
	public abstract void onUpdateFromServer();

}
