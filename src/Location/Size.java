/**
 * Size class file, in Location package
 * authors:
 * Asaf Navon, ID: 207809997
 * David Abenhaim, ID: 208537019
 */
package Location;

/**
 * Size class, has width and height values
 */
public class Size {
	private int width;
	private int height;

	/**
	 * Constructor
	 * @param h: an integer that represent the height
	 * @param w: an integer that represent the width
	 */
	public Size(int h, int w) {
		this.width = w;
		this.height = h;
	}

	/**
	 * set a new value for the height
	 * @param h: a new integer value for the height
	 */
	public void setHeight(int h) {
		this.height = h;
	}

	/**
	 * set a new value for the width
	 * @param w: a new integer value for the width
	 */
	public void setWidth(int w) {
		this.width = w;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * creates a String representation of the Size object
	 * @return a String representation of the object
	 */
	@Override
	public String toString() {
		return "Width: " + this.width + " Height: " + this.height;
	}

}
