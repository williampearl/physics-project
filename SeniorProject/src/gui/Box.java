package gui;
import processing.core.*;

public class Box {
	
	protected PVector position;
	protected PVector size;
	protected PApplet parent;
	
	public Box(PApplet par) {
		parent = par;
	}
	/**
	 * 
	 * @param par - canvas to draw on
	 * @param pos - position of the top left corner {x, y}
	 * @param size - size of the box {width, height}
	 */
	public Box(PApplet par, PVector pos, PVector size) {
		parent = par;
		position = pos;
		this.size= size;
	}
	/**
	 * Attempts to click the box
	 * @param mousePos
	 * @return if it clicked
	 */
	public boolean tryClick(PVector mousePos) {
		return clicked(mousePos);
	}
	/**
	 * Determines if the mouse clicked on the box
	 * @param mousePos - position of the mouse
	 * @return if the box was clicked
	 */
	public boolean clicked(PVector mousePos) {
		boolean x = mousePos.x > position.x && mousePos.x < position.x+size.x;
		boolean y = mousePos.y > position.y && mousePos.y < position.y+size.y;
		return x&&y;
	}
	public void draw() {
		draw(100);
	}
	/**
	 * Draws the box onto the PApplet
	 * @param color - the color to draw with
	 */
	public void draw(int color) {
		//System.out.println("clicked");

		parent.pushMatrix();
		parent.translate(position.x, position.y);
		parent.fill(color);
		parent.rect(0,0, size.x, size.y);
		parent.popMatrix();
	}
	
	/**
	 * Tests if two boxes are overlapping
	 * @param other - the other box to test against
	 * @return if the boxes are overlapping
	 */
	public boolean overlapping(Box other) {
		 return position.x < other.position.x + other.size.x && 
				 position.x + size.x> other.position.x && 
				 position.y < other.position.y + other.size.y && 
				 position.y + size.y > other.position.y;
	}
}
