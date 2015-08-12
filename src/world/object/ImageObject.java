package world.object;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.funkitech.util.Location;

public abstract class ImageObject extends WorldObject {
	
	protected static BufferedImage deepCopyImg(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	protected static BufferedImage loadImage(String path) {
		path = "/images/" + path;
		try {
			return ImageIO.read(ImageObject.class.getResourceAsStream(path));
		} catch (IOException e) {
			return null;
		}
	}
	

	private BufferedImage img = null;
	private int imgWidth;
	private int imgHeight;
	
	public ImageObject(int id, Location location, Location[] bounds, Object[] customData) {
		super(id, location, bounds, customData);
	}
	
	public Image getImage() {
		return img;
	}
	
	public int getImageWidth() {
		return imgWidth;
	}
	
	public int getImageHeight() {
		return imgHeight;
	}
	
	public void setImageHeight(int h) {
		imgHeight = h;
	}
	
	public void setImageWidth(int w) {
		imgWidth = w;
	}
	
	public void setImage(BufferedImage img) {
		this.img = img;
	}
	
	protected void drawImage(Graphics g, int x, int y) {
		if (img != null)
			g.drawImage(img, x, y, imgWidth, imgHeight, null);
		
	}

}
