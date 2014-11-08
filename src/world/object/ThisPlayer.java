package world.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import main.FoV;
import main.Main;
import net.funkitech.util.Location;
import net.funkitech.util.server.messaging.Message;

public class ThisPlayer extends Player implements KeyListener, MouseListener, MouseMotionListener {
	
	public static final float speed = 25f;
	
	private final FoV fov;
	private Direction direction = null;
	private double directionAngle = 0;
	
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
		
		g.setColor(Color.BLACK);
		
		Location loc1 = new Location(0, -40).rotate(directionAngle).add(x, y);
		Location loc2 = new Location(-10, -30).rotate(directionAngle).add(x, y);
		Location loc3 = new Location(10, -30).rotate(directionAngle).add(x, y);
		
		g.drawLine((int) loc1.getX(), (int) loc1.getY(), (int) loc2.getX(), (int) loc2.getY());
		g.drawLine((int) loc1.getX(), (int) loc1.getY(), (int) loc3.getX(), (int) loc3.getY());
		
	}
	
	public Location getDelta() {
		if (direction == null) {
			return new Location(0, 0);
		}
		
		return direction.getDelta().rotate(directionAngle);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (Direction d : Direction.values()) {
			if (d.getChar() == e.getKeyChar()) {
				direction = d;
				break;
				
			}
		}
		
		try {
			Main.socket.sendMessage(new Message("keyPressed", e.getKeyChar()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		for (Direction d : Direction.values()) {
			if (d.getChar() == e.getKeyChar() && d == direction) {
				direction = null;
			}
		}
		
		try {
			Main.socket.sendMessage(new Message("keyReleased", e.getKeyChar()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		directionAngle = new Location(0, 0).angleBetween(getLocationOnScreen().subtract(e.getX(), e.getY())) + 90;
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
	public void mousePressed(MouseEvent e) {
		try {
			Main.socket.sendMessage(new Message("mouseButtonPressed", e.getButton(), Main.gameWindow.toWorldLocation(e.getX(), e.getY())));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			Main.socket.sendMessage(new Message("mouseButtonReleased", e.getButton(), Main.gameWindow.toWorldLocation(e.getX(), e.getY())));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mouseDragged(MouseEvent e) { }

}
