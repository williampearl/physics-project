import processing.core.*;

public class Asteroid extends Particle{
	private PApplet parent;
	public Asteroid(PApplet parent) {
		super(new PVector((float)Math.random()*parent.width, (float)Math.random()*parent.height ), 
				new PVector(0,0),
				100,
				20);
		this.parent = parent;
	}
	public Asteroid(PApplet parent, PVector position) {
		super(position);
		this.parent = parent;
	}
	public Asteroid(PApplet parent, PVector position, float mass, float radius) {
		super(position, new PVector(0,0), mass, radius);
		this.parent = parent;
	}
	public void draw() {
		parent.pushMatrix();
		PVector position = getPosition();
		parent.translate(position.x, position.y);
		
		parent.ellipseMode(PApplet.RADIUS);
		parent.fill(parent.color(0, 100, 100));
		parent.ellipse(0, 0, getRadius(), getRadius());
		parent.popMatrix();
	}
}
