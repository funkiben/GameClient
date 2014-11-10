package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.funkitech.util.Location;
import world.World;
import world.object.ThisPlayer;
import world.object.WorldObject;


public class GameWindow extends JFrame {
	
	public static final int chunkSize = 800;
	
	public static int toChunkX(double x) {
		if (x <= 0) {
			x++;
		}
		
		int cx = (int) (x / chunkSize);
		
		if (x <= 0) {
			cx--;
		}
		
		return cx;
	}
	
	public static int toChunkY(double y) {
		if (y <= 0) {
			y++;
		}
		
		int cy = (int) (y / chunkSize);
		
		if (y <= 0) {
			cy--;
		}
		
		return cy;
	}
	
	public static Location getChunkLocation(int x, int y) {
		return new Location(x * chunkSize, y * chunkSize);
	}
	
	
	private static final long serialVersionUID = -2048254827556094505L;
	
	
	private static final int DRAW_INTERVALS = 30;
	
	private final World world;
	private final FoV fov;
	private int frames = 0;
	
	private int fps = 0;
	private int fpsCounter = 0;
	private int prevSec;
	
	private final List<WorldObject> inZOrder = new ArrayList<WorldObject>();
	
	public static volatile boolean isDrawing = false;
	
	public GameWindow(FoV fov, World world) {
		super("Game");
		
		this.fov = fov;
		this.world = world;
		
		add(new DrawCanvas());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(fov.getSize(), fov.getSize());
		setResizable(false);
		setVisible(true);
		
		new DrawThread(this, DRAW_INTERVALS);
		
	}
	
	public FoV getFoV() {
		return fov;
	}
	
	public Location toWorldLocation(int lx, int ly) {
		double x = ((lx + (fov.getX() - fov.getSize() / 2.0)) * fov.getSize() / getWidth());
		double y = ((ly + (fov.getY() - fov.getSize() / 2.0)) * fov.getSize() / getHeight());
		
		return new Location(x, y);
	}
	
	public void drawObjects(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		fpsCounter++;
		int curSec = Calendar.getInstance().get(Calendar.SECOND);
		if (curSec != prevSec) {
			prevSec = curSec;
			fps = fpsCounter;
			fpsCounter = 0;
		}
		
		int objects = 0;
		int draws = 0;
		
		Main.player.update(frames);
		
		isDrawing = true;
		
		inZOrder.clear();
		
		inZOrder.addAll(world.getObjects());
		
		Collections.sort(inZOrder, new Comparator<WorldObject>() {

			@Override
			public int compare(WorldObject o1, WorldObject o2) {
				return o1.getZLevel() - o2.getZLevel();
			}
			
		});
		
		for (WorldObject o : inZOrder) {
			
			objects++;
			
			if (!(o instanceof ThisPlayer)) {
				o.doSmoothMoving();
				o.update(frames);
			}
			
			double x = (o.getX() - (fov.getX() - fov.getSize() / 2.0)) / fov.getSize() * getWidth();
			double y = (o.getY() - (fov.getY() - fov.getSize() / 2.0)) / fov.getSize() * getHeight();
			
			if (x + o.getLowestXPoint() < getWidth() && x + o.getHighestXPoint() > 0 && y + o.getLowestYPoint() < getHeight() && y + o.getHighestYPoint() > 0) {
				
				g2d.setTransform(o.getTransform());
				g2d.translate(x, y);
				g2d.rotate(Math.toRadians(o.getRotation()));
				
				o.draw(g, 0, 0);
				draws++;
				
				
			}
			
		}
		isDrawing = false;
		
		frames++;
		

		g2d.setTransform(new AffineTransform());
		
		g.setColor(Color.black);
		g.drawString("Draws: " + draws, 10, 15);
		g.drawString("Objects: " + objects, 10, 30);
		g.drawString("FPS: " + fps, 10, 45);
		g.drawString("Loc: " + Main.player.getLocation(), 10, 60);
		int cx = (int) (toChunkX(Main.player.getLocation().getX()));
		int cy = (int) (toChunkY(Main.player.getLocation().getY()));
		g.drawString("Chunk: " + cx + "," + cy, 10, 75);
		
		
		
	}

	
	private class DrawCanvas extends JPanel {
		
		private static final long serialVersionUID = 6333028362943156947L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.WHITE);
			drawObjects(g);
		}
		
	}
	

}
