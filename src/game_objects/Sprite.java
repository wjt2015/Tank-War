package game_objects;

import java.awt.*;

public class Sprite {
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean visible;
	protected Image image;

	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}

	protected void getImageDimensions() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	protected void loadImage(String imageName) {
		image = Toolkit.getDefaultToolkit().createImage(imageName);
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	protected void move(int dx, int dy) {
		x += dx;
		y += dy;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
