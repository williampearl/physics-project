import java.util.*;
import processing.core.*;

public class GameSystem {
	
	PApplet display;
	private ArrayList<Particle> particles;
	private int tickSpeed;
	private float asteroidMaxRadius=10f;
	private float asteroidMaxMass=20f;
	private float G = (float) (6.67 * Math.pow(10f, -3f));
	
	public GameSystem(PApplet parent) {
		this(parent, 0);
	}
	public GameSystem(PApplet parent, int numberOfParticles) {
		display = parent;
		particles = new ArrayList<Particle>();
		tickSpeed = 1;
		for(int i=0; i<numberOfParticles; i++)
			addRandomAsteroid();
	}
	
	public void addRandomAsteroid() {
		Asteroid a = new Asteroid(display);
		boolean works = false;
		while(!works) {
			works = true;
			a = generateRandomAsteroid();
			for(Particle part : particles) {
				if(a.isColliding(part)) {
					works = false;
					break;
				}
			}
		}
		particles.add(a);
	}
	public Asteroid generateRandomAsteroid() {
		PVector randomPosition = new PVector(display.width*(float)Math.random(), display.height*(float)Math.random());
		float pVal = (float)Math.random();
		float mass = pVal*asteroidMaxMass;
		float radius = pVal*asteroidMaxRadius;
		return new Asteroid(display, randomPosition, mass, radius);
	}
	
	public void tick() {
		for(int i=0; i<tickSpeed; i++) {
			applyGravity();
			checkCollisions();
			updateParticles();
		}
		draw();
	}
		
	public void checkCollisions() {
		for(int a1=0; a1<particles.size(); a1++) {
			Particle p1 = particles.get(a1);
			for(int a2=a1+1; a2<particles.size(); a2++) {
				Particle p2 = particles.get(a2);
				if(p1.isColliding(p2))
					p1.collide(p2);
			}
		}
	}
	public void applyGravity() {
		for(int i1=0; i1<particles.size(); i1++) {
			Particle a1 = particles.get(i1);
			for(int i2=i1+1; i2<particles.size(); i2++) {
				Particle a2 = particles.get(i2);
				PVector grav = gravity(a1, a2);
				a1.addForce(grav);
				a2.addForce(gravity(a2, a1));
			}
		}
	}
	public PVector gravity(Particle p1, Particle p2) {
		PVector f = PVector.sub(p1.getPosition(), p2.getPosition());
		float dis = f.mag();
		if(dis<1)
			dis = 1f;
		float stren = (G*p1.getMass()*p2.getMass())/(dis*dis);
		f.mult(stren*-0.01f);
		//f.limit(30);
		
		return f;
	}
	public void updateParticles() {
		for(Particle p : particles)
			p.update();
	}
	public void draw() {
		for(Particle par : particles)
			par.draw();
	}
	public void restart(int n) {
		particles = new ArrayList<Particle>();
		for(int i=0; i<n; i++)
			addRandomAsteroid();
	}
	public ArrayList<Particle> getParticles() {
		return particles;
	}
	public void setParticles(ArrayList<Particle> particles) {
		this.particles = particles;
	}
	public int getTickSpeed() {
		return tickSpeed;
	}
	public void setTickSpeed(int tickSpeed) {
		if(tickSpeed < 0)
			this.tickSpeed = 0;
		else 
			this.tickSpeed = tickSpeed;
	}
}
