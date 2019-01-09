package gui;
import processing.core.*;

public class Button extends Box{
	private int normalColor = 0xFFB22222;
	private int pressedColor = 0xFF800000;
	private String text;
	private boolean pressed;
	/**
	 * Method to run when clicked
	 */
	private ButtonClick clickMethod;
	/**
	 * 
	 * @param parent - canvas to draw on
	 * @param pos - top left corner of button
	 * @param size - size of button
	 * @param text - text to display
	 */
	public Button(PApplet parent, PVector pos, PVector size, String text) {
		super(parent, pos, size);
		this.text = text;
		pressed = false;
	}
	public boolean tryClick(PVector mousePos) {
		if(clicked(mousePos)) {
			//System.out.println("clicked");
			click();
			return true;
		} 
		if(!pressed)
			unClick();
		
		return false;
	}
	public void click() {
		if(pressed == true) 
			return;
		pressed = true;
		//System.out.println("Clicked");
	}
	public void unClick() {
		if(pressed == false)
			return;
		//System.out.println("clicked to not");
		pressed = false;
		clickMethod.click(1);
	}
	public void setClickMethod(ButtonClick bc) {
		clickMethod = bc;
	}
	/**
	 * Draws the button onto the canvas
	 */
	public void draw() {
		if(pressed)
			super.draw(pressedColor);
		else
			super.draw(normalColor);
		parent.pushMatrix();
		parent.pushStyle();		
		parent.translate(position.x, position.y+size.y);
		parent.textSize(12f);
		float minSizeW = 12/parent.textWidth(text)*size.x;
		  // calculate minimum size to fit height
		  float minSizeH = 12/(parent.textDescent()+parent.textAscent()) *size.y;
		 
		 parent.textSize(PApplet.min(minSizeW, minSizeH));
		parent.fill(255);
		parent.text(text, 0,0);
		parent.popStyle();
		parent.popMatrix();
		
	}
	
	
}
