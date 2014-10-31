package main;

import javax.swing.JFrame;

public class DrawThread extends Thread {
	
	private final JFrame frame;
	private final int updateInterval;
	
	public DrawThread(JFrame frame, int updateInterval) {
		this.frame = frame;
		this.updateInterval = updateInterval;
		
		setDaemon(true);
		
		start();
	}
	
	@Override
	public void run() {
		while (true) {
			frame.repaint();
			Main.sleep(1000 / updateInterval);
		}
	}


}
