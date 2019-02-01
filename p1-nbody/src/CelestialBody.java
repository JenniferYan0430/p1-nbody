

/**
 * Celestial Body class for NBody
 * @author Jennifer Yan
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName; 

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}

	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b){
		// TODO: complete constructor
		myXPos = b.getX();
		myYPos = b.getY();
		myXVel = b.getXVel();
		myYVel = b.getYVel();
		myMass = b.getMass();
		myFileName = b.getName();
	}
	/**
	 * Return x-position of this Body (for use outside of class)
	 * @return myXPos (value of x-position)
	 */
	public double getX() {
		
		return myXPos;
	}
	/**
	 * Return y-position of this Body (for use outside of class)
	 * @return myYPos (value of y-position)
	 */
	public double getY() {
		
		return myYPos;
	}
	/**
	 * Return x-velocity of this Body (for use outside of class)
	 * @return myXVel (value of x-velocity).
	 */
	public double getXVel() {
		
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		
		return myYVel;
	}
	/**
	 * Return mass of this Body.
	 * @return value of mass.
	 */
	public double getMass() {
		
		return myMass;
	}
	/**
	 * Return name of this Body.
	 * @return its name as a string.
	 */
	public String getName() {
		
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double dx = b.myXPos-this.myXPos; 
		double dy = b.myYPos-this.myYPos;
		double distance = Math.sqrt(Math.pow(dx, 2)+ Math.pow(dy, 2));
		
		return distance;
	}
	/**
	 * Return the force exerted on this body by p
	 * @param p the other body which is exerting the force being calculated
	 * @return forced exerted by p onto this body
	 */
	public double calcForceExertedBy(CelestialBody p) {
		double G = 6.67*1e-11;
		double force = (G*this.getMass()*p.getMass())/Math.pow(this.calcDistance(p),2);
		return force;
	}
	/**
	 * Return the force exerted on this body by p in the x direction
	 * @param p the other body which is exerting the force being calculated
	 * @return forced exerted by p onto this body in the x direction 
	 */
	public double calcForceExertedByX(CelestialBody p) {
		double dx = p.myXPos-this.myXPos;
		double forceX = this.calcForceExertedBy(p)*dx/this.calcDistance(p);
		return forceX;
	}
	/**
	 * Return the force exerted on this body by p in the y direction
	 * @param p the other body which is exerting the force being calculated
	 * @return forced exerted by p onto this body in the y direction 
	 */
	public double calcForceExertedByY(CelestialBody p) {
		double dy = p.myYPos-this.myYPos;
		double forceY = this.calcForceExertedBy(p)*dy/this.calcDistance(p);
		return forceY;
	}
	/**
	 * Return the net force exerted on this body in the x direction by all the bodies in the array
	 * @param bodies, the set of celestial bodies, which are exerting the forces being calculated
	 * @return net force exerted on this body in the x direction by bodies
	 */
	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double netForce = 0.0;
		for(CelestialBody b: bodies) {
			if (!b.equals(this)) {
				netForce += this.calcForceExertedByX(b);
			}
		}
		return netForce;
	}
	/**
	 * Return the net force exerted on this body in the y direction by all the bodies in the array
	 * @param bodies, the set of celestial bodies, which are exerting the forces being calculated
	 * @return net force exerted on this body in the y direction by bodies
	 */
	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double netForce = 0.0;
		for(CelestialBody b: bodies) {
			if (!b.equals(this)) {
				netForce += this.calcForceExertedByY(b);
			}
		}
		return netForce;
	}
	/**
	 * Mutator, updates the values of position and velocity for the body after a change in time
	 * @param detaT the change in time
	 * @param xforce net force in the x direction exerted on this body by all other bodies
	 * @param yforcenet force in the x direction exerted on this body by all other bodies
	 */
	public void update(double deltaT, 
			           double xforce, double yforce) {
		double accelX = xforce/this.getMass();
		double accelY = yforce/this.getMass();
		
		//calculates new position & velocity values based on detaT and the calculated accleration
		double nvx = this.myXVel + deltaT*accelX;
		double nvy = this.myYVel + deltaT*accelY; 
		double nx = this.myXPos + deltaT*nvx; 
		double ny = this.myYPos + deltaT*nvy;
		
		myXPos = nx; 
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
	}
	/**
	 * uses the x-position and y-position of the object 
	 * to draw it's current position for the simulation
	 */
	public void draw() {
		StdDraw.picture(myXPos, myYPos, "images/"+myFileName);
	}
}
