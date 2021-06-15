/**
 * Point class file, in Location package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Location;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

/**
 * Point class, has x and y values.
 */
public class Point {
	private int x;
	private int y;

	/**
	 * Constructor
	 * @param x: an integer that represent the x value of the point
	 * @param y: an integer that represent the y value of the point
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * set a new value for x
	 * @param x: a new integer value for x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * set a new value for y
	 * @param y: a new integer value for y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the x value
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return the y value
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * calculates the distance between a Point to another
	 * @param other: another Point object
	 * @return the distance between this Point and the other Point
	 */
	public double distance(Point other) {
		return sqrt(
				pow(x - other.getX(), 2)
				+
				pow(y - other.getY(), 2)
				);
	}

	/**
	 * creates a String representation of the Point
	 * @return a String representation of the object
	 */
	@Override
	public String toString() {
		return "X: " + this.x + " Y: " + this.y;
	}

}
