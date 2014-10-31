package world.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Main;
import net.funkitech.util.Location;

public class Player extends WorldObject {

	private static final Location[] bounds;
	private static final int radius = 20;

	static {
		int vertices = 10;
		bounds = new Location[vertices];

		for (int i = 1; i <= vertices; i++) {
			Location loc = new Location(0, radius / 2).rotate((double) i / vertices * 360.0);
			bounds[i - 1] = loc;
		}
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
		
	}

	@Override
	public void onUpdateFromServer() {
		
	}


}
