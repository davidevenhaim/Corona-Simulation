/**
 * Location class file, in Location package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Location;

import static java.lang.Math.*;

/**
 * Location class, has a Point to represent the top left corner, and a Size.
 */
public class Location {
	private Point position;
	private Size size;

	/**
	 * Constructor
	 * @param x: the x value of the Point
	 * @param y: the y value of the Point
	 * @param h: the height of the Size
	 * @param w: the width of the Size
	 */
	public Location(int x, int y, int h, int w) {
		this.position = new Point(x, y);
		this.size = new Size(h,w);
	}

	/**
	 * generate a random Point inside the Location
	 * @return a random Point
	 */
	public Point randomPointInLocation() {
		return new Point(
						(int)(position.getX() + size.getWidth() * random()),
						(int)(position.getY() - size.getHeight() * random())
					);
	}

	public int[] getDetails() {
		int[] arr = { this.position.getX(), this.position.getY(), this.size.getWidth(), this.size.getHeight()};
		return arr;
	}
	/**
	 * creates a String representation of the Location
	 * @return a String representation of the object
	 */
	@Override
	public String toString() {
		return this.position.toString() + " " + this.size.toString();
	}

	public Point getCenter() {
		int centerX = position.getX() + size.getWidth() / 2;
		int centerY = position.getY() + size.getHeight() / 2;
		return new Point(centerX, centerY);
	}

	public boolean isOnLocation(Point point) {
		int rangeX = this.position.getX() + this.size.getWidth();
		int rangeY = this.position.getY() + this.size.getHeight();

		if( point.getX() >= this.position.getX() && point.getX() <= rangeX &&
				point.getY() >= this.position.getY() && point.getY() <= rangeY) {
			return true;
		}
		return false;
	}

}
