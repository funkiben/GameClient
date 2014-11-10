package world.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Main;
import net.funkitech.util.Location;

public class Player extends WorldObject {

	private static final Location[] bounds;
	protected static final int radius = 10;

	static {
		bounds = getCircularBounds(radius, 10);
	}
	
	
	

	private final String name;

	public Player(int id, Location location, Object[] customData) {
		super(id, location, bounds, customData);
		
		this.name = (String) customData[0];
		
		
	}

	public String getName() {
		return name;
	}
	
	public boolean touchingOther() {
		for (Player player : Main.world.getPlayers()) {
			if (player != this) {
				if (contains(player)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	protected void draw(Graphics g, int x, int y, Color color) {
		g.setColor(touchingOther() ? Color.CYAN : Color.BLACK);
		g.fillOval(x - radius, y - radius, (int) (radius * 2), (int) (radius * 2));
		
		g.setColor(color);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		
		int strSize = g.getFontMetrics().stringWidth(getName());
		g.drawString(getName(), x - (strSize / 2), y - radius - 6);
		
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		draw(g, x, y, Color.RED);
	}

	@Override
	public void update(int frames) {
		setZLevel((int) getLocationOnScreen().getY());
	}

	@Override
	public void onUpdateFromServer() {
		
	}


}
