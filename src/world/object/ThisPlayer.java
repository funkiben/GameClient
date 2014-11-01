package world.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import main.FoV;
import main.Main;
import net.funkitech.util.Location;
import net.funkitech.util.server.messaging.Message;


public class ThisPlayer extends Player implements KeyListener {
	
	public static final float speed = 50f;
	
	private final FoV fov;
	private Direction direction = null;
	
	public ThisPlayer(int id, Location location, Object[] customData) {
		super(id, location, customData);
		
		fov = new FoV(location);
		
	}
	
	public FoV getFoV() {
		return fov;
	}
	
	@Override
	public void move(Location delta) {
		super.move(delta);
		
		try {
			Main.socket.sendMessage(new Message("move", delta));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(int frames) {
		if (!getDelta().isZero()) {
			move(getDelta());
		}
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		draw(g, x, y, Color.BLUE);
		
	}
	
	public Location getDelta() {
		if (direction == null) {
			return new Location(0, 0);
		}
		
		return direction.getDelta();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (Direction d : Direction.values()) {
			if (d.getChar() == e.getKeyChar()) {
				direction = d;
				break;
				
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		for (Direction d : Direction.values()) {
			if (d.getChar() == e.getKeyChar() && d == direction) {
				direction = null;
			}
		}
		
	}
	
	public enum Direction {
		
		UP    ('w', 0, -1),
		DOWN  ('s', 0, 1),
		LEFT  ('a', -1, 0),
		RIGHT ('d', 1, 0);
		
		private final char chr;
		private final int x;
		private final int y;
		
		Direction(char chr, int x, int y) {
			this.chr = chr;
			this.x = x;
			this.y = y;
		}
		
		public char getChar() {
			return chr;
		}
		
		public Location getDelta() {
			return new Location(x * ThisPlayer.speed, y * ThisPlayer.speed);
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }

}
