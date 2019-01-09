import processing.core.*;

public abstract class Particle {
	private PVector position;
	private PVector velocity;
	private PVector acceleration;
	/**
	 * angular displacement
	 */
	private float theta;
	/**
	 * angular velocity
	 */
	private float rVelocity;
	/**
	 * angular acceleration
	 */
	private float rAcceleration;
	private float mass;	
	private float radius;
	/**
	 * position will not change
	 */
	private boolean isStatic;
	boolean canCollide;
	public Particle(PVector initialPos) {
		this(initialPos, new PVector(0, 0), 10f, 10f);
	}
	public Particle(PVector initialPos, PVector initialVelocity, float mass, float radius) {
		position = initialPos;
		velocity = initialVelocity;
		acceleration = new PVector(0,0);
		theta = 0f;
		rVelocity = 0f;
		rAcceleration = 0f;
		this.mass = mass;
		this.radius = radius;
		isStatic = false;
		canCollide = true;
	}
	
	public void update() {
		if(isStatic)
			return;
		velocity.add(acceleration);
		position.add(velocity);
		acceleration.set(0, 0);
		rVelocity += rAcceleration;
		theta += rVelocity;
		rAcceleration = 0;
	}
	
	/**
	 * Applies a force to the particle
	 * @param force
	 */
	public void addForce(PVector force) {
		acceleration.add(force.div(mass));
	}
	public void setAngularAcceleration(float newAcc) {
		rAcceleration = newAcc;
	}
	
	public boolean isColliding(Particle part) {
		return canCollide && part.canCollide && position.dist(part.position) < radius+part.radius;
	}
	public void collide(Particle part) {
		
		float mass1 = mass;
		float mass2 = part.getMass();
		//fix overlap based on mass
		PVector x1 = getPosition().copy();
		PVector x2 = part.getPosition().copy();
		PVector v1 = getVelocity().copy();
		PVector v2 = part.getVelocity().copy();
		
		PVector normal = PVector.sub(x2, x1).normalize();
		PVector tangent = new PVector(normal.y*-1f, normal.x).normalize();
		fixOverlap(part);
		//System.out.println("After "+isColliding(part));
		//normal component
		float v1n = PVector.dot(v1, normal);
		float v2n = PVector.dot(v2, normal);
		//tangent component
		float v1t = PVector.dot(v1, tangent);
		float v2t = PVector.dot(v2, tangent);
		
		//finding the new normals
		float v1nn = (v1n*(mass1-mass2)+2*mass2*v2n)/(mass1+mass2);
		//System.out.println(v1nn);
		float v2nn = (v2n*(mass2-mass1)+2*mass1*v1n)/(mass1+mass2);
		//System.out.println(v2nn);

		PVector v1nf = PVector.mult(normal, v1nn);
		//System.out.println(v1nf);
		PVector v2nf = PVector.mult(normal, v2nn);
		//System.out.println(v2nf);

		PVector v1tf = PVector.mult(tangent, v1t);
		PVector v2tf = PVector.mult(tangent, v2t);
		
		PVector v1f = v1nf.add(v1tf);
		//System.out.println(v1f);
		PVector v2f = v2nf.add(v2tf);
		//System.out.println(v2f);

//		canCollide = false;
//		part.canCollide = false;
		float energyLoss = 0.99f;
		setVelocity(v1f.mult(energyLoss));
		part.setVelocity(v2f.mult(energyLoss));
	}
	
	private void fixOverlap(Particle part) {
		PVector sub = position.copy().sub(part.getPosition().copy());
		
		//sub.mult(-1f);
		sub.setMag(radius+part.getRadius()-sub.mag());
		if(mass<part.getMass())
			position.add(sub);
		else
			part.getPosition().add(sub.mult(-1f));
	}
	
	public abstract void draw();
	
	public PVector getPosition() {
		return position;
	}
	public void setPosition(PVector position) {
		this.position = position;
	}
	public PVector getVelocity() {
		return velocity;
	}
	public void setVelocity(PVector velocity) {
		this.velocity = velocity;
	}
	public PVector getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(PVector acceleration) {
		this.acceleration = acceleration;
	}
	public float getTheta() {
		return theta;
	}
	public void setTheta(float theta) {
		this.theta = theta;
	}
	public float getrVelocity() {
		return rVelocity;
	}
	public void setrVelocity(float rVelocity) {
		this.rVelocity = rVelocity;
	}
	public float getrAcceleration() {
		return rAcceleration;
	}
	public void setrAcceleration(float rAcceleration) {
		this.rAcceleration = rAcceleration;
	}
	public float getMass() {
		return mass;
	}
	public void setMass(float mass) {
		this.mass = mass;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public boolean isStatic() {
		return isStatic;
	}
	public void toggleStatic() {
		isStatic = !isStatic;
	}
}
