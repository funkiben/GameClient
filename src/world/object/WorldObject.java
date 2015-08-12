package world.object;

import java.awt.geom.AffineTransform;
import java.util.Arrays;

import main.Drawable;
import main.FoV;
import main.GameWindow;
import main.Main;
import net.funkitech.util.Location;

public abstract class WorldObject implements Drawable {
	
	public static Location[] getSquareBounds(int width, int height) {
		return new Location[] {
				new Location(0, 0),
				new Location(width, 0),
				new Location(width, height),
				new Location(0, height)
		};
	}
	
	public static Location[] getCenteredSquareBounds(int width, int height) {
		return new Location[] {
				new Location(width/2, height/2),
				new Location(-width/2, height/2),
				new Location(-width/2, -height/2),
				new Location(width/2, -height/2)
		};
	}
	
	public static Location[] getCircularBounds(int radius, int points) {
		Location[] bounds = new Location[points];

		for (int i = 1; i <= points; i++) {
			Location loc = new Location(0, radius).rotate((double) i / points * 360.0);
			bounds[i - 1] = loc;
		}
		
		return bounds;
	}
	
	
	private static final float smoothMovementDeltaDivisor = 2.60f;
	
	
	private final int id;
	private final Location location;
	private final Location[] bounds;
	private final int lowestX;
	private final int lowestY;
	private final int highestX;
	private final int highestY;
	private Object[] customData;
	private double rotation = 0;
	private Location smTarget = new Location(0, 0);
	private Location smDelta = new Location(0, 0);
	private final AffineTransform transform = new AffineTransform();
	private int zLevel = 0;
	private boolean solid = false;
	private boolean autoSetZLevel = true;
	
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
	
	public void smoothMove(Location delta) {
		smTarget = location.add(delta);
		smDelta = delta;
	}
	
	public void smoothMoveTo(Location target) {
		smTarget = target;
		smDelta = target.subtract(location);
	}
	
	public void doSmoothMoving() {
		if (!smDelta.isZero()) {
			if (smTarget.distanceSqrt(location) < smDelta.lengthSqrt()) {
				setLocation(smTarget);
				smDelta.set(0, 0);
			} else {
				move(smDelta.divide(smoothMovementDeltaDivisor));
			}
		}
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
	
	public AffineTransform getTransform() {
		return transform;
	}
	
	public int getZLevel() {
		return zLevel;
	}
	
	public void setZLevel(int z) {
		zLevel = z;
		ZOrderChange();
	}
	
	public void ZOrderChange() {
		if (Main.gameWindow != null) {
			Main.gameWindow.ZOrderChange();
		}
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public void setAutoSetZLevel(boolean b) {
		autoSetZLevel = b;
	}
	
	public boolean canAutoSetZLevel() {
		return autoSetZLevel;
	}
	
	public void rotateBounds(double deg) {
		for (int i = 0; i < bounds.length; i++) {
			bounds[i] = bounds[i].rotate(deg);
		}
	}
	
	public void rotate(double deg) {
		rotation += deg;
		rotateBounds(deg);
	}
	
	public void setRotation(double deg) {
		rotateBounds(-rotation);
		rotation = deg;
		rotateBounds(rotation);
	}
	
	public double getRotation() {
		return rotation;
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
	
	public boolean contains(WorldObject obj, Location offset) {
		for (Location loc : obj.getBounds()) {
			if (contains(loc.add(obj.getLocation().add(offset)))) {
				return true;
			}
		}
		
		return false;
	}
	
	public Location getLocationOnScreen() {
		FoV fov = Main.player.getFoV();
		GameWindow frame = Main.gameWindow;
		
		double x = (getX() - (fov.getX() - fov.getSize() / 2.0)) / fov.getSize() * frame.getWidth();
		double y = (getY() - (fov.getY() - fov.getSize() / 2.0)) / fov.getSize() * frame.getHeight();
		
		return new Location(x, y);
	}
	
	public boolean isInView() {
		GameWindow frame = Main.gameWindow;
		Location loc = getLocationOnScreen();
		
		return loc.getX() + getLowestXPoint() < frame.getWidth() && loc.getX() + getHighestXPoint() > 0 && loc.getY() + getLowestYPoint() < frame.getHeight() && loc.getY() + getHighestYPoint() > 0;
	}

	public abstract void update(int frames);
	public abstract void onUpdateFromServer();

}
