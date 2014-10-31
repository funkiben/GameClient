package main;

import net.funkitech.util.Location;


public class FoV {
	private final static int size = 3000;

	private final Location location;
	
	public FoV(Location location) {
		this.location = location;
		
	}
	
	public double getX() {
		return location.getX();
	}
	
	public double getY() {
		return location.getY();
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean contains(Location loc) {
		return contains(loc.getX(), loc.getY());
	}
	
	public boolean contains(double x, double y) {
        double x0 = getX();
        double y0 = getY();
        return (x >= x0 &&
                y >= y0 &&
                x < x0 + size &&
                y < y0 + size);
    }
}
