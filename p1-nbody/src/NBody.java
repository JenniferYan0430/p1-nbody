	

/**
 * @author Jenifer Yan
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));
		s.nextInt(); //read first value
		double radius = s.nextDouble();	// read second value and store as radius
		s.close();

		return radius;	//return radius
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static CelestialBody[] readBodies(String fname) throws FileNotFoundException {
		
			Scanner s = new Scanner(new File(fname));
			
			int nb = s.nextInt(); // read & store # bodies
			CelestialBody [] bodies = new CelestialBody[nb]; //create array to store values
			s.next(); // ignore radius
			
			for(int k=0; k < nb; k++) { //go through bodies array and add new CelestialBodies, reading input info from file
				bodies[k] = new CelestialBody(s.nextDouble(), s.nextDouble(), s.nextDouble(), s.nextDouble(), s.nextDouble(), s.next());
			}
			s.close(); //stop reading			
			
			return bodies; // return array of body objects read
	}
	/**
	 * implements methods for the NBody Class to run the simulation
	 * @param takes a argument of type string array 
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = Math.pow(10, 9);
		double dt = 1000000;
		
		String fname= "./data/planets.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		CelestialBody[] bodies = readBodies(fname); //read file and create celestial bodies
		double radius = readRadius(fname);//read the radius
		
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
	
		for(double t = 0.0; t < totalTime; t += dt) {
			
			
			double [] xforces = new double[bodies.length];// double arrays xforces and yforces holds the forces on each body
			double [] yforces = new double[bodies.length];
			// loop over all bodies, calculate net forces and store in xforces and yforces
			for(int j = 0; j<bodies.length;j++) {
				xforces[j] = bodies[j].calcNetForceExertedByX(bodies);
				yforces[j] = bodies[j].calcNetForceExertedByY(bodies);
			}
			
			
			for(int i =0; i<bodies.length; i++) {// loop over all bodies
				bodies[i].update(dt, xforces[i], yforces[i]);// call update w/dt and corresponding xforces, yforces values
			}
			
			StdDraw.picture(0,0,"images/starfield.jpg");
			
			// loop over all bodies and call draw on each one
			for(CelestialBody c: bodies) {
				c.draw();
			}
			StdDraw.show(10);
		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
