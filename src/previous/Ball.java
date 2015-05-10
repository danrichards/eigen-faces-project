package previous;
import csc450Lib.calc.base.Function1D;
import csc450Lib.calc.base.PolyFunction1D;

/**
 * Simulate a Ball flying through the air with gravity.
 * 
 * The Ball Function1D function is Velocity Vector V(t)
 * 
 * Using PolyFunction1D in case we need a derivative
 * 
 * @author Dan Richards
 *
 */
public class Ball extends Function1D {
	
	/* Gravity */
	public float g = -9.81f;
	
	/* Time */
	public float t = 0;
	
	/* Position & Velocities */
	public float x0, z0, Vx, Vz;
	
	/* Final position & velocities after collision with ground. */
	public float xf, zf, Vxf, Vzf;
	
	/* Ground level is a function, let start with flat surface. */
	public PolyFunction1D ground;
	
	/**
	 * Simulate a Ball affected by gravity or a bounce
	 * 
	 * @param 	x0 	float
	 * @param 	z0 	float
	 * @param 	Vx 	float
	 * @param	Vz 	float 
	 */
	public Ball(PolyFunction1D ground, float x0, float z0, float Vx, float Vz) {
		
		/* Set the ground level to a flat line at y = -10 */
		this.ground = ground;
		
		/* Set our initial state, this could be the starting position & velocity or 
		 * reflected (bounce) start */
		this.x0 = x0; this.z0 = z0; this.Vx = Vx; this.Vz = Vz;
	}

	/**
	 * Given t, calculate the next time the ball will make contact with the ground
	 */
	@Override
	public float func(float xk) {		
		// Given the data in the constructor
		
		// float t = (xk - this.x0) / this.Vx;
		
		// float result = Math.pow(-0.5f * g * t, 2) + this.Vz ( t + this.z0) - this.ground.func(xk);
		
		// if the result is reasonably close to zero.
			
			// set our final class variables xf, zf, Vxf, Vzf
		
		// return result;
		
		/* Until this thing is working*/
		return 0f;
	}
	
	/**
	 * Rise over Run for what? VyRt(t) / VxRt()
	 */
//	public float dfunc(x) {
//		
//	}
	
	/**
	 * Return a new ball that is a reflection of this one.
	 * 
	 * @return Ball
	 */
//	public Ball reflect() {
//		Ball reflection = new Ball();
//		return reflection;
//	}
	
	/**
	 * Velocity in x direction with respect to time is the average velocity * time 
	 * 
	 * @return
	 */
	public float VxRt(float t) {
		return this.Vx * t;
//		return new PolyFunction1D (new float [] {0f, this.Vx});
	}
	
	/**
	 * Velocity in the z direction with respect to time is the Vyi
	 * @return
	 */
	public float VzRt(float t) {
		return this.g * t;
//		return new PolyFunction1D (new float [] {0f, this.Vz});
	}

	/**
	 * Define x with respect to t
	 */
	public PolyFunction1D xRt(float t) {
		PolyFunction1D xRt = new PolyFunction1D (new float[] {this.x0, this.Vx});
		this.xf = xRt.func(t);
		return xRt;
	}
	
	
	/**
	 * Define z with respect to t, taking gravity into account
	 * 
	 * @param t
	 * @return
	 */
	public PolyFunction1D zRt(float t) {
		PolyFunction1D zRt = new PolyFunction1D (new float[] {this.z0, this.Vz, 0.5f * this.g});
		this.zf = zRt.func(t);
		return zRt;
	}

	/**
	 * Yep.
	 */
	@Override
	public boolean isExactDerivativeDefined() {
		return true;
	}

}
