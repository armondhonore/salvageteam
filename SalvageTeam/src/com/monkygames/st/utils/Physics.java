/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.utils;

/**
 * Handles any additional physics calculations not handled by jbullet.
 * @version 1.0
 */
public class Physics{

// ============= Class variables ============== //
public float max_speed;  //The absolute max speed allowed
public float vI; //Initial Velocity in meters per second
public float acc; //The acceleration
// ============= Constructors ============== //
/**
 * Creates a new physics with default values of 1.78 for max speed and 0.43 for accelaration.
 **/
public Physics(){
    this(1.0f,0.01f);
}

/**
 * Creates a new physics class with the specified params.
 * @param max_speed the max speed of the ship.
 * @param acc the acceleration of the ship.
 **/
public Physics(float max_speed, float acc){
    this.max_speed = max_speed;
    this.acc = acc;
    vI = 0f;
}
// ============= Public Methods ============== //
/**
 * Calculates the thrust force.<br>
 * Equation: Vf = aT + Vi<br>
 * Where T = 20ms.
 * @param isThrusting true if thrusting and false otherwise.
 * @param time the amount of time between the last velocity calc and this one.
 * @return the thrust force taking into account acceleration.
 **/
public float calcThrustForce(boolean isThrusting, float time){
    float T = time;
    if(isThrusting){
	float vF = 0f;
//System.out.println("max_speed,acc,vI,vF "+max_speed+","+acc+","+vI+","+vF);
	if(max_speed > vI){
	    vF = acc*T + vI;
	    if(max_speed < vF){
		vF = max_speed;
	    }
	} else if (max_speed == vI){
	    vF = max_speed;
	}
	vI = vF;
    } else {
	vI = 0f;
    }
    return vI;
}
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
