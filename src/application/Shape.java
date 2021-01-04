
package application;

// import packages
import javafx.scene.paint.Color;

public class Shape {

	// Declare class variables
	private double width, height, tempWidth, tempHeight, sizeMultiplier, shifterX, shifterY;
	private Color color, blueShade, yellowShade;
	private int shape;
	public final int CIRCLE = 0;
	public final int DIAMOND = 1;

	// Set default constructor
	public Shape() {
		// Initialize class variable
		width = 0;
		height = 0;
		tempWidth = 0;
		tempHeight = 0;
		sizeMultiplier = 0;
		shifterX = 0;
		shifterY= 0;
		blueShade = Color.BLUE;
		yellowShade = Color.YELLOW;
		color = blueShade;
		shape = CIRCLE;
	}

	// Alternate constructor to set colour, sizeMultiplier, and shifters
	public Shape(double sm, double sx, double sy, Color bs, Color ys) {
		// Initialize class variable
		width = 0;
		height = 0;
		tempWidth = 0;
		tempHeight = 0;
		sizeMultiplier = sm;
		shifterX = sx;
		shifterY= sy;
		blueShade = bs;
		yellowShade = ys;
		color = bs;
		shape = CIRCLE;
	}

	// ACCESSOR METHODS

	/**
	 * Returns the width of the shape
	 *
	 * @return	width - the shape's width
	 */
	public double getWidth(){
		return width;
	}

	/**
	 * Returns the height of the shape
	 *
	 * @return	height - the shape's height
	 */
	public double getHeight(){
		return height;
	}

	/**
	 * Returns the shape's previous width, used when clearing a previous version with a different size to draw a new one
	 *
	 * @return	tempWidth - shape's previous width
	 */
	public double getTempWidth() {
		return tempWidth;
	}

	/**
	 * Returns the shape's previous height, used when clearing a previous version with a different size to draw a new one
	 *
	 * @return	tempHeight - shape's previous height
	 */
	public double getTempHeight() {
		return tempHeight;
	}

	/**
	 * Returns the value used to obtain the maximum size of the shape when expanded
	 *
	 * @return	sizeMultiplier - value that determines max size
	 */
	public double getSizeMultiplier() {
		return sizeMultiplier;
	}

	/**
	 * Returns value that shifts the shape horizontally
	 *
	 *  @return	shifterX - horizontal shift
	 */
	public double getShifterX() {
		return shifterX;
	}

	/**
	 * Returns value that shifts the shape vertically
	 *
	 * @return	shifterY - vertical shift
	 */
	public double getShifterY() {
		return shifterY;
	}

	/**
	 * Returns the shape of the Shape object
	 *
	 * @return	shape - shape of the object, either circle (0) or diamond(1)
	 */
	public int getShape() {
		return shape;
	}

	/**
	 * Returns the colour of the Shape object
	 *
	 * @return	color - colour of the object
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns the blueShade of the Shape object
	 *
	 * @return	blueShade - blue Shade of the object
	 */
	public Color getBlueShade() {
		return blueShade;
	}

	/**
	 * Returns the yellowShade of the Shape object
	 *
	 * @return	yellowShade - yellow Shade of the object
	 */
	public Color getYellowShade() {
		return yellowShade;
	}




	// MUTATOR METHODS
	/**
	 * Sets the shape's width
	 *
	 * @param 	w - shape width
	 */
	public void setWidth(double w) {
		width = w;
	}

	/**
	 * Sets the shape's height
	 *
	 * @param 	h - shape height
	 */
	public void setHeight(double h) {
		height = h;
	}

	/**
	 * Sets the width of the shape's previous size
	 *
	 * @param 	tw - shape's previous width
	 */
	public void setTempWidth(double tw) {
		tempWidth = tw;
	}

	/**
	 * Sets the height of the shape's previous size
	 *
	 * @param 	th - shape's previous height
	 */
	public void setTempHeight(double th) {
		tempHeight = th;
	}

	/**
	 * Sets the value used to determine the shape's max size after expanding
	 *
	 * @param 	sm - value used to determine max size
	 */
	public void setSizeMultiplier(double sm) {
		sizeMultiplier = sm;
	}

	/**
	 * Sets horizontal shift in the shape's position, in relation to the center of the stage
	 *
	 * @param 	sx - horizontal shift
	 */
	public void setShifterX(double sx) {
		shifterX = sx;
	}

	/**
	 * Sets vertical shift in the shape's position, in relation to the center of the stage
	 *
	 * @param 	sy - vertical shift
	 */
	public void setShifterY(double sy) {
		shifterY = sy;
	}

	/**
	 * Sets shape of the Shape object, either circle or diamond
	 *
	 * @param 	shape - desired shape of the object
	 */
	public void setShape(int shape) {
		this.shape = shape;
	}

	/**
	 * Sets colour of the Shape object
	 *
	 * @param 	c - desired colour of the object
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Sets blue shade of the Shape object
	 *
	 * @param 	bs - desired blue shade of the object
	 */
	public void setBlueShade(Color bs) {
		blueShade = bs;
	}

	/**
	 * Sets yellow shade of the Shape object
	 *
	 * @param 	ys - desired yellow shade of the object
	 */
	public void setYellowShade(Color ys) {
		yellowShade = ys;
	}


}