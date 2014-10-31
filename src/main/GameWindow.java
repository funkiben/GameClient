package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import world.World;
import world.object.WorldObject;


public class GameWindow extends JFrame {
	
	private static final long serialVersionUID = -2048254827556094505L;
	
	
	private static final int DRAW_INTERVALS = 30;
	
	private final World world;
	private final FoV fov;
	private int frames = 0;
	
	private int fps = 0;
	private int fpsCounter = 0;
	private int prevSec;
	
	public GameWindow(FoV fov, World world) {
		super("Game");
		
		this.fov = fov;
		this.world = world;
		
		add(new DrawCanvas());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		setResizable(false);
		setVisible(true);
		
		new DrawThread(this, DRAW_INTERVALS);
	}
	
	public FoV getFoV() {
		return fov;
	}
	
	public void drawObjects(Graphics g) {
		
		fpsCounter++;
		int curSec = Calendar.getInstance().get(Calendar.SECOND);
		if (curSec != prevSec) {
			prevSec = curSec;
			fps = fpsCounter;
			fpsCounter = 0;
		}
		
		int draws = 0;
		int objects = 0;
		
		world.doObjectAdding();
		world.doObjectRemoving();
		
		Iterator<WorldObject> iter = world.getObjects().iterator();
		
		while (iter.hasNext()) {
			
			objects++;
			
			WorldObject o = iter.next();
			
			o.update(frames);
			
			double x = (o.getX() - (fov.getX() - fov.getSize() / 2.0)) / fov.getSize() * getWidth();
			double y = (o.getY() - (fov.getY() - fov.getSize() / 2.0)) / fov.getSize() * getHeight();
			
			if (x + o.getLowestXPoint() < getWidth() && x + o.getHighestXPoint() > 0 && y + o.getLowestYPoint() < getHeight() && y + o.getHighestYPoint() > 0) {
				
				o.draw(g, (int) x, (int) y);
				draws++;
				
			}
			
		}
		
		frames++;
		
		g.setColor(Color.black);
		g.drawString("Draws: " + draws, 10, 15);
		g.drawString("Objects: " + objects, 10, 30);
		g.drawString("FPS: " + fps, 10, 45);
		g.drawString("Loc: " + Main.player.getLocation(), 10, 60);
		int cx = (int) (Main.player.getLocation().getX() / 800);
		int cy = (int) (Main.player.getLocation().getY() / 800);
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
